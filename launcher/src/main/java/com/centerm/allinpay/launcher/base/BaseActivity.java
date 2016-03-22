package com.centerm.allinpay.launcher.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;

import com.centerm.allinpay.launcher.cont.Constants;
import com.centerm.allinpay.launcher.net.MyHttpClient;
import com.centerm.allinpay.launcher.utils.Log4d;
import com.centerm.allinpay.launcher.utils.ScreenUtils;
import com.centerm.allinpay.launcher.view.CustomToast;
import com.centerm.allinpay.launcher.view.HintDialog;
import com.centerm.allinpay.launcher.view.MessageAlertDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by linwanliang on 2016/2/26.
 */
public class BaseActivity extends Activity {

    protected static final Log4d logger = Log4d.getInstance();
    protected static MyHttpClient httpClient = MyHttpClient.getInstance();
    private ScreenUtils screenUtils;

    private HintDialog loading;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getScreenUtils().isTablet()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public ScreenUtils getScreenUtils() {
        if (screenUtils == null) {
            screenUtils = new ScreenUtils(this);
        }
        return screenUtils;
    }

    public void showLoading(String content) {
        if (loading == null) {
            loading = new HintDialog(this);
            loading.setCanceledOnTouchOutside(false);
            loading.setCancelable(false);
        }
        if (content == null || content.trim().equals("")) {
            content = "加载中，请稍后...";
        }
        loading.setHint(content);
        loading.show();
    }

    public void hideLoading() {
        if (loading != null) {
            loading.setHint("加载中，请稍后...");
            loading.dismiss();
        }
    }

    public boolean isLoading() {
        if (loading != null) {
            return loading.isShowing();
        }
        return false;
    }

    public MessageAlertDialog showMessageDialog(String message) {
        MessageAlertDialog dialog = new MessageAlertDialog(this);
        dialog.hideNegativeButton();
        dialog.setMessage(message);
        dialog.show();
        return dialog;
    }

    public File getLoginedCacheFile() {

        File file = new File(getCacheDir() + File.separator + Constants.Cache.LOGINED_INFO_FILE_NAME);
        return file;
    }

    public void writeLoginedCacheFile(String content) {
        File file = getLoginedCacheFile();
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file, false);
            fos.write(content.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 可自动消失的提示框
     *
     * @param message
     */
    public void showAutoDismissMessageDialog(String message) {
        CustomToast toast = new CustomToast(this);
        toast.setText(message);
        toast.show();
    }


}
