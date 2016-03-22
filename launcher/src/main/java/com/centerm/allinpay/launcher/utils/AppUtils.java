package com.centerm.allinpay.launcher.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import com.centerm.allinpay.launcher.cont.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by linwanliang on 2016/3/7.
 */
public class AppUtils {

    private final static String TAG = AppUtils.class.getSimpleName();
    private final static Log4d logger = Log4d.getInstance();

    /**
     * 根据apk文件来获取对应的包名
     *
     * @param context
     * @param apkPath
     * @return apk文件对应的包名，如果apk文件有问题，则返回null；
     */
    public static String getPackageName(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info.applicationInfo.packageName;
        } else {
            return null;
        }
    }

    public static String getApkVersionName(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info.versionName;
        } else {
            return null;
        }
    }


    /**
     * 打开指定包名的应用
     *
     * @param context
     * @param packageName
     */
    public static boolean openApp(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return false;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(intent);
        return true;
    }

    /**
     * 安装指定APK
     *
     * @param context
     * @param apkPath
     */
    public static boolean installApp(Context context, String apkPath) {
        File file = new File(apkPath);
        if (!apkPath.endsWith(".apk") || !file.exists()) {
            logger.warn(TAG, "[安装失败] " + apkPath);
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        context.startActivity(intent);
        return true;
    }

    /**
     * 获取指定包名应用的当前版本，如果该应用未安装，返回null。
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getInstalledVersionName(Context context, String packageName) {
        if (packageName == null) {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> allPackages = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : allPackages) {
            if (packageInfo.packageName.equals(packageName)) {
                return packageInfo.versionName;
            }
        }
        return null;
    }

    /**
     * 获取非系统应用列表
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getNonSystemAppList(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> allPackages = packageManager.getInstalledPackages(0);
        List<PackageInfo> listUnSystem = new ArrayList<PackageInfo>();
        for (PackageInfo packageInfo : allPackages) {
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
                    && !packageInfo.packageName.equals(context.getPackageName())) {
                listUnSystem.add(packageInfo);// 如果非系统应用，则添加至appList
            }
        }
        return listUnSystem;
    }

    /**
     * 获取APK文件存储目录的绝对路径
     *
     * @return
     */
    public static String getApkFileDir() {
        return Environment.getExternalStorageDirectory()
                + Constants.Cache.ROOT_DIR + Constants.Cache.APK_DIR;
    }

    /**
     * 获取ZIP文件存储目录的绝对路径
     *
     * @return
     */
    public static String getZipFileDir() {
        return Environment.getExternalStorageDirectory()
                + Constants.Cache.ROOT_DIR + Constants.Cache.ZIP_DIR;
    }

    /**
     * 从下载地址中提取出文件名称
     *
     * @param packageUrl
     * @return
     */
    public static String extractFileNameFromUrl(String packageUrl) {
        if (packageUrl == null) {
            return null;
        }
        String[] array = packageUrl.split("/");
        if (array.length > 0) {
            return array[array.length - 1];
        }
        return null;
    }


    /**
     * 获取已解压的文件夹绝对路径
     *
     * @param packageUrl
     * @return
     */
    public static String getUnzipDir(String packageUrl) {
        return getZipFileDir() + File.separator + MD5Utils.getMD5Str(packageUrl);
    }

    /**
     * 根据下载地址，获取该文件对应的临时文件的存储路径
     *
     * @param packageUrl
     * @return
     */
    public static String getTempFilePath(String packageUrl) {
        if (packageUrl.endsWith(".apk")) {
            return getApkFileDir() + File.separator + MD5Utils.getMD5Str(packageUrl);
        } else {
            return getZipFileDir() + File.separator + MD5Utils.getMD5Str(packageUrl);
        }
    }

    public static void unzip(final File zipFile, String unzipPath, ZipResultCallback callback) {
        if (zipFile == null) {
            logger.warn(TAG, "[解压失败] " + zipFile.toString());
            if (callback != null) {
                callback.onUnZipFailed();
            }
            return;
        }

        InputStream in = null;
        OutputStream out = null;
        try {
            ZipFile zip = new ZipFile(zipFile);
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                in = zip.getInputStream(entry);
                String outPath = (unzipPath + File.separator + zipEntryName).replaceAll("\\*", "/");
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if (!file.exists()) {
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是,不需要解压
                if (new File(outPath).isDirectory()) {
                    continue;
                }
                out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
            }
        } catch (IOException e) {
            zipFile.delete();
            logger.warn(TAG, "[解压失败] " + zipFile.toString());
            e.printStackTrace();
            if (callback != null) {
                callback.onUnZipFailed();
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.warn(TAG, "[解压失败] " + zipFile.toString());
                e.printStackTrace();
                zipFile.delete();
                if (callback != null) {
                    callback.onUnZipFailed();
                }
            }
            if (callback != null) {
                logger.info(TAG, "[解压完成] " + zipFile.toString());
                callback.onUnZipFinished(unzipPath);
            }
        }
    }

    public static void testUnzip() {

    }


    public interface ZipResultCallback {
        void onUnZipFailed();

        void onUnZipFinished(String unzipPath);
    }


}
