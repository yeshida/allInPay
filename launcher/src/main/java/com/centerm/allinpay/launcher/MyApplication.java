package com.centerm.allinpay.launcher;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;

import com.allinpay.usdk.dev.aidl.BasicInfo;
import com.allinpay.usdk.dev.aidl.DeviceProvider;
import com.centerm.allinpay.launcher.cont.PresKey;
import com.centerm.allinpay.launcher.net.Address;
import com.centerm.allinpay.launcher.net.NetConstants;
import com.centerm.allinpay.launcher.net.MyHttpClient;
import com.centerm.allinpay.launcher.utils.Log4d;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.IoUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by linwanliang on 2016/2/29.
 */
public class MyApplication extends Application {
    private final static String REMOTE_SERVICE_ACTION = "com.allinpay.usdk.dev.AIDL_SERVICE";
    private final static String TAG = MyApplication.class.getSimpleName();
    private final static Log4d logger = Log4d.getInstance();
    private DeviceProvider deviceProvider;

    private static String sn = "";
    private static String aipSn = "";

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            deviceProvider = DeviceProvider.Stub.asInterface(service);
            logger.info(TAG, "[远程服务连接已建立]");
            try {
                BasicInfo info = deviceProvider.getBasicProvider().getBasicInfo();
//                sn = info.getSn();
//                aipSn = info.getAipSn();
//                SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                pres.edit().putString(PresKey.SN, sn).putString(PresKey.AIPSN, aipSn).apply();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            logger.info(TAG, "已获取到终端信息，解绑服务");
            unbindRemoteService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            deviceProvider = null;
            logger.info(TAG, "[远程服务连接已断开]");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        //设置日志打印主标签
        Log4d.setGlobalTag("ALLINPAY_LAUNCHER");
        //连接远程服务
        bindRemoteService();
        //初始化异步图片加载组件
        initImageLoader(getApplicationContext());
        //获取终端部分信息，应用首次启动是没有这部分信息的
        //远程服务连接成功后，会更新这部分信息
        SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //D1G0280000003
        //AIP0000000000001
        //// TODO: 2016/3/11 测试用的终端号，正式发布要去掉 
//        sn = pres.getString(PresKey.SN, "null");
//        aipSn = pres.getString(PresKey.AIPSN, "null");
        sn = "D1G0280000003";
        aipSn = "AIP0000000000001";
        logger.info(TAG, "sn==" + sn + "  aipSn==" + aipSn);
        //设置默认的接口访问地址
        Address address = new Address(NetConstants.IP, NetConstants.TEST_PORT, true);
        address.setSuffix(NetConstants.PROJECT_NAME);
        MyHttpClient.setAddress(address);

//        MyHttpClient.getInstance().test2(getApplicationContext());


    }

    private void initImageLoader(Context context) {
        ImageLoaderConfiguration conf = ImageLoaderConfiguration.createDefault(context);
        ImageLoader.getInstance().init(conf);
    }

    public void bindRemoteService() {
        logger.info(TAG, "开始绑定远程服务");
        Intent intent = new Intent();
        intent.setAction(REMOTE_SERVICE_ACTION);

        int currentVersion = Build.VERSION.SDK_INT;
        logger.info(TAG, "当前系统版本：" + currentVersion);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setPackage(getPackageName());
        }

        boolean result = bindService(intent, connection, Context.BIND_AUTO_CREATE);
        logger.info(TAG, "[服务绑定结果]--" + result);
        if (!result) {
            SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sn = pres.getString(PresKey.SN, "null");
            aipSn = pres.getString(PresKey.AIPSN, "null");
        }


    }

    public void unbindRemoteService() {
        logger.info(TAG, "解绑服务");
        unbindService(connection);
    }

    public static String getSn() {
        return sn;
    }

    public static String getAipSn() {
        return aipSn;
    }
}
