package com.centerm.allinpay.launcher.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.centerm.allinpay.launcher.adapter.GridViewAdapter;
import com.centerm.allinpay.launcher.download.DownloadManager;
import com.centerm.allinpay.launcher.net.Key;
import com.centerm.allinpay.launcher.utils.AppUtils;
import com.centerm.allinpay.launcher.utils.Log4d;
import com.centerm.allinpay.launcher.utils.ScreenUtils;
import com.centerm.allinpay.launcher.utils.ViewUtils;
import com.centerm.allinpay.launcher.view.GridItemView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/6.
 */
public class GridSpaceFillHelper {
    private final static String TAG = GridSpaceFillHelper.class.getSimpleName();
    private final static Log4d logger = Log4d.getInstance();
    //竖屏（小屏幕），分为2列
    private final static int PORTRAIT_COLUMNS = 2;
    //横屏（大屏幕），分为5列
    private final static int LANDSCAPE_COLUMNS = 5;
    //每页三行
    private final static int ROWS = 3;
    //每个Item之间的间隔
    private final static int ITEM_SPACING = 15;
    //计算每个item高度时的容错值，确保页面不会上下滑动
    private final static int FAULT_TOLERANCE_VALUE = 30;

    private Activity activity;
    private List<JSONObject> appList;
    private int spaceWidth, spaceHeight;
    private int columns = PORTRAIT_COLUMNS;
    private int rows = ROWS;
    private int pageCounts;
    private int itemHeight;
    private int itemSpacing;

    private List<View> viewList;

    private DownloadManager downloadManager;

    public GridSpaceFillHelper(Activity activity, List<JSONObject> appList, final int spaceWidth, final int spaceHeight) {
        if (activity == null || appList == null) {
            throw new IllegalArgumentException("any params cannot be null");
        }
        this.activity = activity;
        this.appList = appList;
        this.spaceWidth = spaceWidth;
        this.spaceHeight = spaceHeight;
        this.itemSpacing = ITEM_SPACING;

        downloadManager = DownloadManager.getInstance();

        logger.info(GridSpaceFillHelper.class, "[容器大小] width = " + spaceWidth + "  height = " + spaceHeight);
        //判断是否是平板的横屏屏幕
        ScreenUtils screenUtils = new ScreenUtils(activity);
        if (screenUtils.isTablet()) {
            columns = LANDSCAPE_COLUMNS;
        }
        //计算总页数
        int size = appList.size();
        pageCounts = size / (columns * rows);
        if (size % (columns * rows) != 0) {
            pageCounts++;
        }
        logger.info(GridSpaceFillHelper.class, "[总页数] = " + pageCounts);
        //计算item的高度
        int availableSpace = spaceHeight - ((rows - 1) * ITEM_SPACING * 2) - FAULT_TOLERANCE_VALUE;
        itemHeight = availableSpace / 3;

        logger.info(GridSpaceFillHelper.class, "[每项高度值] = " + itemHeight);

    }

    /**
     * @return
     */
    public List<View> generateViewList() {
        viewList = new ArrayList<View>();
        int total = appList.size();
        int start = 0;
        for (int i = 0; i < pageCounts; i++) {
            List<JSONObject> subList = null;
            int end = (i + 1) * columns * rows;
            if (end > total) {
                end = total;
            }
            subList = appList.subList(start, end);
            start = end;
            viewList.add(generateGridView(subList));
        }
        return viewList;
    }

    private GridView generateGridView(final List<JSONObject> appList) {
        final GridView gridView = new GridView(activity);
        gridView.setNumColumns(columns);
        gridView.setVerticalSpacing(itemSpacing);
        gridView.setHorizontalSpacing(itemSpacing);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setGravity(Gravity.CENTER);
        gridView.setAdapter(new GridViewAdapter(activity, appList, itemHeight));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    onGridItemClick(parent, view, position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return gridView;
    }

    public List<View> getViewList() {
        return viewList;
    }

    private void onGridItemClick(AdapterView<?> parent, final View item, int position) throws JSONException {
        if (item.getTag() == null || !(item.getTag() instanceof JSONObject)) {
            return;
        }
        JSONObject data = (JSONObject) item.getTag();
        String id = data.getString(Key.id);
        String packageUrl = data.getString(Key.packageUrl);
        String fileName = AppUtils.extractFileNameFromUrl(packageUrl);
        if (fileName == null) {
            return;
        }
        Map<String, DownloadManager.Status> idMapStatus = downloadManager.getIdMapStatus();
        if (idMapStatus.containsKey(id)) {
            // 目前不支持暂停下载，待支持断点续传后开启该功能
/*           DownloadManager.Status status = idMapStatus.get(id);
            if (status.equals(DownloadManager.Status.DOWNLOADING) || status.equals(DownloadManager.Status.WAITING)) {
                downloadManager.stopDownload(packageUrl);
                ((GridItemView) item).showStatus("暂停下载", -1);
            } else if (status.equals(DownloadManager.Status.PAUSED)) {
                doDownload(item, id, packageUrl);
            } else {
                ViewUtils.showToast(activity, "下载队列中，无法操作");
            }*/
            logger.warn(TAG, fileName + "  已存在下载队列中，暂时无法操作");
            return;
        }
        ((GridItemView) item).hideStatus();
        if (packageUrl.endsWith(".apk")) {
            String packageName = null;
            //从已存在的APK安装包中获取包名
            File file = new File(AppUtils.getApkFileDir() + File.separator + fileName);
            if (file.exists()) {
                packageName = AppUtils.getPackageName(activity, file.toString());
            }
            logger.info(TAG, "[解析出来的包名]" + packageName);
            logger.info(TAG, "[接口返回的包名]" + id);
            //如果上述途径无法获取到，就从接口返回的id中取值
            //注意：接口返回的id并不一定是真实的APK包名，此处设计有缺陷
            if (packageName == null) {
                packageName = id;
            }
            // 判断该应用是否已经安装
            // 版本名为null，说明指定包名还未安装在本机上
            String localVersion = AppUtils.getInstalledVersionName(activity, packageName);
            String currentVersion = data.getString(Key.versionNm);
            logger.info(TAG,"[本地版本名]" + localVersion);
            logger.info(TAG,"[接口返回的版本名]" + currentVersion);
            //版本比较
            //注意： 此处设计有缺陷，应该比较版本号
            if (localVersion == null) {
                //如果安装包存在，并且版本与接口返回的版本一致，就打开安装包进行安装
                if (file.exists()) {
                    String apkVersion = AppUtils.getApkVersionName(activity, file.getAbsolutePath());
                    if (currentVersion.equals(apkVersion)) {
                        logger.info(TAG, "安装文件存在，并且版本号与服务器一致，准备安装程序");
                        AppUtils.installApp(activity, file.getAbsolutePath());
                        return;
                    }
                }
            } else if (currentVersion.equals(localVersion)) {
                logger.info(TAG, "本地版本号与服务器一致，准备打开应用");
                //打开应用
                boolean openResult = AppUtils.openApp(activity, packageName);
                if (openResult) {
                    return;
                }
            }

            //开始下载对应的APK文件
            doDownload(item, id, packageUrl);
        } else if (packageUrl.endsWith(".zip")) {
            String unzipDir = AppUtils.getUnzipDir(packageUrl);
            File indexFile = new File(unzipDir + File.separator + id);
            if (indexFile.exists()) {
                // 载入相应的html文件
                Intent intent = new Intent(activity, WebViewActivity.class);
                intent.putExtra(WebViewActivity.KEY_INDEX_HTML, indexFile.toString());
                activity.startActivity(intent);
            } else {
                //查找相应的Zip包是否存在
                File zipFile = new File(AppUtils.getZipFileDir() + File.separator + fileName);
                if (zipFile.exists()) {
                    //Zip文件存在的话，开始解压
                    AppUtils.unzip(zipFile, unzipDir, null);
                } else {
                    //Zip不存在，开始下载
                    doDownload(item, id, packageUrl);
                }
            }
        }
    }

    private void doDownload(View itemView, String id, String packageUrl) {
        ((GridItemView) itemView).showStatus("准备下载", -1);
        downloadManager.startDownload(activity, id, packageUrl);
    }


    /**
     * 获取行数
     *
     * @return
     */
    public int getRows() {
        return rows;
    }

    /**
     * 获取列数
     *
     * @return
     */
    public int getColumns() {
        return columns;
    }

    /**
     * 获取每个色块的高度
     *
     * @return
     */
    public int getItemHeight() {
        return itemHeight;
    }

    /**
     * 获取分页总数
     *
     * @return
     */
    public int getPageCounts() {
        return pageCounts;
    }

}
