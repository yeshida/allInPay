package com.centerm.allinpay.launcher.cont;


import java.io.File;

/**
 * Created by linwanliang on 2016/3/3.
 */
public class Constants {




    /**
     * 程序缓存目录结构，
     * 缓存保存在SD卡上。
     */
    public static class Cache {
        public final static String ROOT_DIR = File.separator + "AllinpayLauncher";
        //public final static String IMG_DIR = File.separator + "image";
        public final static String APK_DIR = File.separator + "apk";
        public final static String ZIP_DIR = File.separator + "zip";

        /**
         * 用户登录之后的信息，所保存的文件名。
         * 注意：该文件与上述文件所保存的位置不同，应放置在系统为程序分配的缓存文件夹中，
         * 可以通过Context.getCacheDir()方法来访问该文件夹。
         */
        public final static String LOGINED_INFO_FILE_NAME = "logined.info";

    }
}
