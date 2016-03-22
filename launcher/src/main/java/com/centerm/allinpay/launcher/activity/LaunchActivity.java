package com.centerm.allinpay.launcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridLayout;

import com.centerm.allinpay.launcher.base.BaseActivity;
import com.centerm.allinpay.launcher.cont.Constants;

import java.io.File;


/**
 * Created by linwanliang on 2016/2/29.
 * 启动界面，用于判断用户是否已经登录，如果已经登录，则显示应用列表界面，否则显示登录界面。
 */
public class LaunchActivity extends BaseActivity {


    private GridLayout gridContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        setContentView(R.layout.test_activity);
        gridContainer = (GridLayout) findViewById(R.id.grid_container);*/

        goNextActivity();
        finish();

/*
        ViewTreeObserver viewObserver = gridContainer.getViewTreeObserver();
        viewObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = gridContainer.getWidth();
                int height = gridContainer.getHeight();
                logger.info("test", "width==" + width + "  height==" + height);
            }
        });
*/


    }


    private void goNextActivity() {
        File loginedCache = new File(getCacheDir() + File.separator + Constants.Cache.LOGINED_INFO_FILE_NAME);
        Intent intent = null;
        if (loginedCache.exists()) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
    }


}
