package com.centerm.allinpay.launcher.download;

import android.content.Context;
import android.content.Intent;

import com.centerm.allinpay.launcher.cont.BroadcastAction;
import com.centerm.allinpay.launcher.net.Key;
import com.centerm.allinpay.launcher.utils.AppUtils;
import com.centerm.allinpay.launcher.utils.Log4d;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/8.
 * 文件下载管理器
 */
public class DownloadManager {

    public final static String KEY_PROGRESS = "KEY_PROGRESS";

    private final static String TAG = DownloadManager.class.getSimpleName();
    private final static int DEFAULT_MAX_CONNECTIONS = 5;// 并发下载的文件个数
    private final static int DEFAULT_RESPONSE_TIMEOUT = 5 * 60 * 1000;// 超时时间10分钟
    private final static Log4d logger = Log4d.getInstance();
    private static DownloadManager instance;
    private AsyncHttpClient innerClient;
    private int maxConnections = DEFAULT_MAX_CONNECTIONS;
    private int timeOut = DEFAULT_RESPONSE_TIMEOUT;
    private Map<String, Status> idMapStatus;

    private static long lastSendProgressTime = 0;
    private final static long SEND_PROGRESS_INTERVAL = 1000;

    private DownloadManager() {
        innerClient = new AsyncHttpClient();
        innerClient.setMaxConnections(maxConnections);
        innerClient.setConnectTimeout(timeOut);
        innerClient.setResponseTimeout(timeOut);
        idMapStatus = new HashMap<String, Status>();
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            instance = new DownloadManager();
        }
        return instance;
    }

    public void startDownload(final Context context, final String id, final String packageUrl) {
        if (idMapStatus.containsKey(id)) {
            log(packageUrl, id + "已在下载队列中，无需重复开启下载");
            return;
        }
        FileType type = null;
        if (packageUrl.endsWith(".apk")) {
            type = FileType.APK;
        } else {
            type = FileType.ZIP;
        }
        final FileType fileType = type;
        //防止多线程下载同一个文件造成文件错误
        innerClient.cancelRequestsByTAG(packageUrl, true);
        idMapStatus.put(id, Status.WAITING);
        log(packageUrl, "加入下载队列");
        //获取临时文件的存储路径
        final File tempFile = new File(AppUtils.getTempFilePath(packageUrl));
        //开始下载起始字节
        long startByte = 0;
        if (tempFile.exists()) {
            //如果支持断点续传，需要先获取已有文件的长度
            //现在不支持断点续传，因此如果临时文件已存在，就直接删除，重新开始下载
            //startByte = tempFile.length();
            tempFile.delete();
        }
        final long beginDownloadedSize = startByte;
        FileAsyncHttpResponseHandler handler = new FileAsyncHttpResponseHandler(tempFile, true) {

            @Override
            public void onCancel() {
                log(packageUrl, "取消下载");
//                idMapStatus.put(id, Status.PAUSED);
                idMapStatus.remove(id);
                Intent intent = new Intent();
                intent.setAction(BroadcastAction.DOWNLOAD_CANCELED);
                intent.putExtra(Key.id, id);
                context.sendBroadcast(intent);
            }

            @Override
            public void onStart() {
                log(packageUrl, "开始下载");
                idMapStatus.put(id, Status.DOWNLOADING);
                Intent intent = new Intent();
                intent.setAction(BroadcastAction.DOWNLOAD_PROGRESS);
                intent.putExtra(KEY_PROGRESS, 0);
                intent.putExtra(Key.id, id);
                context.sendBroadcast(intent);
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                long now = System.currentTimeMillis();
                if (now - lastSendProgressTime > SEND_PROGRESS_INTERVAL) {
                    int percent = (int) ((beginDownloadedSize + bytesWritten) * 100
                            / (beginDownloadedSize + totalSize));
                    logger.debug(TAG, packageUrl);
                    logger.debug(TAG, "正在下载，已下载 " + percent + " %");
                    Intent intent = new Intent();
                    intent.setAction(BroadcastAction.DOWNLOAD_PROGRESS);
                    intent.putExtra(Key.id, id);
                    intent.putExtra(KEY_PROGRESS, percent);
                    context.sendBroadcast(intent);
                    lastSendProgressTime = now;
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                log(packageUrl, "下载失败，错误码 " + statusCode + " 异常信息 " + throwable.toString());
//                idMapStatus.put(id, Status.PAUSED);
                idMapStatus.remove(id);
                if (file.exists()) {
                    file.delete();
                }
                Intent intent = new Intent();
                intent.setAction(BroadcastAction.DOWNLOAD_FAILED);
                intent.putExtra(Key.id, id);
                context.sendBroadcast(intent);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                String name = AppUtils.extractFileNameFromUrl(packageUrl);
                final File newFile = new File(fileType.getFileDir() + File.separator + name);
                boolean result = file.renameTo(newFile);
                if (result) {
                    logger.info(TAG, "下载成功，文件位置 " + newFile.toString());
                    file.delete();
                }
                if (fileType.equals(FileType.ZIP)) {
                    Intent intent = new Intent();
                    intent.setAction(BroadcastAction.UNZIPPING);
                    intent.putExtra(Key.id, id);
                    context.sendBroadcast(intent);
                    idMapStatus.put(id, Status.UNZIPPING);
                    final AppUtils.ZipResultCallback callback = new AppUtils.ZipResultCallback() {
                        @Override
                        public void onUnZipFailed() {
                            Intent intent = new Intent();
                            intent.setAction(BroadcastAction.UNZIPPING_FAILED);
                            intent.putExtra(Key.id, id);
                            context.sendBroadcast(intent);
                            idMapStatus.remove(id);
                        }

                        @Override
                        public void onUnZipFinished(String unzipPath) {
                            Intent intent = new Intent();
                            intent.setAction(BroadcastAction.UNZIPPING_FINISHED);
                            intent.putExtra(Key.id, id);
                            context.sendBroadcast(intent);
                            idMapStatus.put(id, Status.UNZIPPING);
                            idMapStatus.remove(id);
                        }
                    };
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AppUtils.unzip(newFile, AppUtils.getUnzipDir(packageUrl), callback);
                        }
                    }).start();
                } else {
                    Intent intent = new Intent();
                    intent.setAction(BroadcastAction.DOWNLOAD_COMPLETE);
                    intent.putExtra(Key.id, id);
                    context.sendBroadcast(intent);
                    idMapStatus.remove(id);
                }
            }
        };
        // 断点续传的节点
        // innerClient.addHeader("Range", "bytes=" + startByte);
        innerClient.get(context, packageUrl, handler).setTag(packageUrl);
    }

    public void stopDownload(String packageUrl) {
        innerClient.cancelRequestsByTAG(packageUrl, true);
    }

    public Map<String, Status> getIdMapStatus() {
        return idMapStatus;
    }

    public AsyncHttpClient getInnerClient() {
        return innerClient;
    }

    private void log(String packageUrl, String content) {
        logger.warn(TAG, packageUrl);
        logger.info(TAG, content);
    }


    public enum Status {
        WAITING,
        DOWNLOADING,
        PAUSED,
        UNZIPPING
    }

    public enum FileType {
        APK(AppUtils.getApkFileDir()),
        ZIP(AppUtils.getZipFileDir());

        private String fileDir;

        FileType(String fileDir) {
            this.fileDir = fileDir;
        }

        public String getFileDir() {
            return fileDir;
        }
    }


}
