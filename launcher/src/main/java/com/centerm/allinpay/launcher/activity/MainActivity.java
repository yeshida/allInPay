package com.centerm.allinpay.launcher.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centerm.allinpay.launcher.MyApplication;
import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.adapter.MainPagerAdapter;
import com.centerm.allinpay.launcher.base.BaseActivity;
import com.centerm.allinpay.launcher.cont.BroadcastAction;
import com.centerm.allinpay.launcher.cont.Constants;
import com.centerm.allinpay.launcher.cont.PresKey;
import com.centerm.allinpay.launcher.download.DownloadManager;
import com.centerm.allinpay.launcher.net.Key;
import com.centerm.allinpay.launcher.net.ResponseHandler;
import com.centerm.allinpay.launcher.net.request.Login;
import com.centerm.allinpay.launcher.net.response.LoginResponse;
import com.centerm.allinpay.launcher.utils.MD5Utils;
import com.centerm.allinpay.launcher.view.AlertDialog;
import com.centerm.allinpay.launcher.view.GridItemView;
import com.centerm.allinpay.launcher.view.MessageAlertDialog;
import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private final static int MAX_CHECK_PASSWD_TIMES = 5;
    private int checkPasswdTimes = 0;
    private ViewPager viewPager;
    private String loginResponse;
    private Map<String, Integer> idMapPosition;
    private MyReceiver receiver;
    private GridSpaceFillHelper helper;
    private LinearLayout dotContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showLoading(null);

        //初始化应用列表数据，如果Intent没有传递数据，则从缓存中读取
        loginResponse = getIntent().getStringExtra(LoginActivity.LOGIN_RESPONSE);
        if (loginResponse == null) {
            File cache = new File(getCacheDir() + File.separator + Constants.Cache.LOGINED_INFO_FILE_NAME);
            if (!cache.exists()) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                loginResponse = readCacheFile(cache);
                logger.info("test",loginResponse);
            }
        }
        //解析缓存数据
        LoginResponse responseEntity = new LoginResponse(loginResponse);
        idMapPosition = responseEntity.getIdMapPosition();
        final List<JSONObject> appList = responseEntity.getAppList();
        TextView userNameShow = (TextView) findViewById(R.id.user_name_show);
        String userName = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext()).getString(PresKey.OPER_NAME, "");
        userNameShow.setText("你好，" + userName);
        userNameShow.setOnClickListener(this);

        //设置页面滑动监听器
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotContainer = (LinearLayout) findViewById(R.id.dot_container);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeDotIndicater(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        //注册广播
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadcastAction.UNZIPPING_FINISHED);
        filter.addAction(BroadcastAction.UNZIPPING);
        filter.addAction(BroadcastAction.UNZIPPING_FAILED);
        filter.addAction(BroadcastAction.DOWNLOAD_CANCELED);
        filter.addAction(BroadcastAction.DOWNLOAD_COMPLETE);
        filter.addAction(BroadcastAction.DOWNLOAD_FAILED);
        filter.addAction(BroadcastAction.DOWNLOAD_PROGRESS);
        filter.addAction(BroadcastAction.DOWNLOAD_START);
        filter.addAction(BroadcastAction.MODIFY_USER_INFO_SUCCESS);
        registerReceiver(receiver, filter);

        //动态布局
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                helper = new GridSpaceFillHelper(MainActivity.this, appList
                        , viewPager.getWidth(), viewPager.getHeight());
                addIndicators(helper.getPageCounts(), 0);
                viewPager.setAdapter(new MainPagerAdapter(helper.generateViewList()));
                hideLoading();
            }
        }, 1500);
    }

    /**
     * 添加页面指示器
     *
     * @param counts
     * @param indicateItem
     */
    private void addIndicators(int counts, int indicateItem) {
        for (int i = 0; i < counts; i++) {
            ImageView dot = new ImageView(this);
            dot.setPadding(5, 5, 5, 5);
            if (i == indicateItem) {
                dot.setImageResource(R.drawable.dot_on);
            } else {
                dot.setImageResource(R.drawable.dot);
            }
            dotContainer.addView(dot);
        }
    }

    /**
     * 移除所有的指示器
     */
    private void removeIndicators() {
        dotContainer.removeAllViews();
    }


    /**
     * 改变页码指示器
     *
     * @param indicate
     */
    private void changeDotIndicater(int indicate) {
        LinearLayout container = (LinearLayout) findViewById(R.id.dot_container);
        int counts = container.getChildCount();
        for (int i = 0; i < counts; i++) {
            if (!(container.getChildAt(i) instanceof ImageView)) {
                continue;
            }
            ImageView dot = (ImageView) container.getChildAt(i);
            if (i == indicate) {
                dot.setImageResource(R.drawable.dot_on);
            } else {
                dot.setImageResource(R.drawable.dot);
            }
        }
    }


    /**
     * 读取缓存文件中的字符流
     *
     * @param cacheFile
     * @return
     */
    private String readCacheFile(File cacheFile) {
        if (cacheFile == null) {
            return null;
        }
        BufferedInputStream bis = null;
        StringBuilder sBuilder = new StringBuilder();
        try {
            bis = new BufferedInputStream(new FileInputStream(cacheFile));
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bis.read(buffer)) != -1) {
                sBuilder.append(new String(buffer, 0, len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sBuilder.toString();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_name_show:
                showVerifyPasswdDialog();
//                Intent intent = new Intent(this, UserCenterActivity.class);
//                startActivity(intent);
                break;

        }
    }

    /*   @Override
       public boolean dispatchKeyEvent(KeyEvent event) {
           logger.info("test", "===" + event.getKeyCode());
           return super.dispatchKeyEvent(event);
       }


       @Override
       public boolean onKeyDown(int keyCode, KeyEvent event) {
           logger.info("test", "===" + keyCode);
           return super.onKeyDown(keyCode, event);
       }
   */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            if (isLoading()) {
                return true;
            }
            if (viewPager != null) {
                viewPager.setCurrentItem(0, true);
            }
            //如果当前有下载任务，则不更新界面，否则会造成界面显示不准确
            Map downloadQueue = DownloadManager.getInstance().getIdMapStatus();
            if (downloadQueue.isEmpty()) {
                updateAppList();
            }
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    /**
     * 更新应用列表。注意更新成功后，要重新写入缓存文件。
     */
    private void updateAppList() {
        SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userId = pres.getString(PresKey.USER_ID, null);
        String operId = pres.getString(PresKey.OPER_ID, null);
        String passwd = pres.getString(PresKey.PASSWD, null);
        if (userId == null || operId == null || passwd == null) {
            //找不到必要信息，直接显示登录信息过期
            onLoginExpired();
            return;
        }
        showLoading(null);
        Login request = new Login(userId, operId, passwd, false);
        httpClient.post(this, request, new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hideLoading();
                LoginResponse result = new LoginResponse(response);
                String code = result.getReturnCode();
                if ("00".equals(code)) {
                    LoginResponse old = new LoginResponse(loginResponse);
                    if (result.getAppListString().equals(old.getAppListString())) {
                        logger.warn(MainActivity.class, "应用列表不变，无需更新界面");
                    } else {
                        //写入缓存文件
                        writeLoginedCacheFile(response);
                        loginResponse = response;
                        //更新界面
                        logger.warn(MainActivity.class, "应用列表有变化，正在更新界面");
                        idMapPosition = result.getIdMapPosition();
                        List<JSONObject> appList = result.getAppList();
                        //一定要移除指示器，否则界面布局会出现偏差
                        removeIndicators();
                        helper = new GridSpaceFillHelper(MainActivity.this, appList
                                , viewPager.getWidth(), viewPager.getHeight());
                        addIndicators(helper.getPageCounts(), 0);
                        viewPager.setAdapter(new MainPagerAdapter(helper.generateViewList()));
                    }
                } else {
                    onLoginExpired();
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                hideLoading();
                showMessageDialog("更新应用列表失败\n请稍候再试");
            }
        });

    }

    /**
     * 登录信息过期时的处理逻辑
     */
    private void onLoginExpired() {
        MessageAlertDialog dialog = showMessageDialog("登录信息已过期，请重新登录");
        dialog.setClickListener(new AlertDialog.ClickListener() {
            @Override
            public void onPositiveClick(View v) {
                logout();
            }

            @Override
            public void onNegativeClick(View v) {
            }
        });
    }

    /**
     * 显示注销登录确认对话框
     */
    private void showExitDialog() {
        MessageAlertDialog dialog = new MessageAlertDialog(this);
        dialog.setMessage("是否要退出登录");
        dialog.setClickListener(new AlertDialog.ClickListener() {

            @Override
            public void onPositiveClick(View v) {
                logout();
            }

            @Override
            public void onNegativeClick(View v) {
            }
        });
        dialog.show();
    }


    /**
     * 注销登录
     */
    private void logout() {
        logger.info(MainActivity.class, "[注销登录]");
        SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pres.edit().clear();
        File file = getLoginedCacheFile();
        if (file.exists()) {
            boolean result = file.delete();
            logger.info(MainActivity.class, "[清除缓存结果] - " + result);
        }
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 根据位置索引去查找相应的ItemView
     *
     * @param position
     * @return
     */
    private GridItemView findView(int position) {
        if (helper == null) {
            return null;
        }
        int countsInPage = helper.getColumns() * helper.getRows();
        int pageIndex = position / countsInPage;
        int index = position % countsInPage;
        if (viewPager == null) {
            return null;
        }
        try {
            GridView gridView = (GridView) helper.getViewList().get(pageIndex);
            GridItemView itemView = (GridItemView) gridView.getChildAt(index);
            return itemView;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void showVerifyPasswdDialog() {
        View view = getLayoutInflater().inflate(R.layout.main_input_passwd_dialog_layout, null);
        final EditText passwdEdit = (EditText) view.findViewById(R.id.passwd_edit);
        final AlertDialog dialog = new AlertDialog(
                this);
        dialog.setTitle("请验证密码");
        dialog.setContent(view);
        dialog.setClickListener(new AlertDialog.ClickListener() {

            @Override
            public void onPositiveClick(View v) {
                String passwd = passwdEdit.getText().toString();
                if (passwd == null || passwd.trim().isEmpty()) {
                    showAutoDismissMessageDialog("密码不能为空");
                    return;
                } else {
                    passwd = MD5Utils.getMD5Str(passwd);
                    String localPasswd = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                            .getString(PresKey.PASSWD, null);
                    if (passwd.equals(localPasswd)) {
                        Intent intent = new Intent(MainActivity.this, UserCenterActivity.class);
                        startActivity(intent);
                    } else {
                        ++checkPasswdTimes;
                        if (checkPasswdTimes >= 5) {
                            showAutoDismissMessageDialog("密码验证次数已达" + MAX_CHECK_PASSWD_TIMES + "次，请重新登录");
                            logout();
                        } else {
                            showAutoDismissMessageDialog("密码验证失败，请重新验证");
                        }
                    }
                }
            }

            @Override
            public void onNegativeClick(View v) {
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        httpClient.getInnerClient().cancelAllRequests(true);
        DownloadManager downloadManager = DownloadManager.getInstance();
        if (downloadManager.getIdMapStatus() != null) {
            downloadManager.getIdMapStatus().clear();
        }
        if (downloadManager.getInnerClient() != null) {
            downloadManager.getInnerClient().cancelAllRequests(true);
        }
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //若用户更改信息成功，主界面需要同步更新用户名信息
            if (BroadcastAction.MODIFY_USER_INFO_SUCCESS.equals(action)) {
                SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final String userName = pres.getString(PresKey.OPER_NAME, "");
                TextView userNameShow = (TextView) findViewById(R.id.user_name_show);
                userNameShow.setText("你好，" + userName);
                return;
            }

            String id = intent.getStringExtra(Key.id);
            Integer position = idMapPosition.get(id);
            if (position == null) {
                logger.warn(MainActivity.class, "[无法找到] " + position);
                return;
            }
            GridItemView view = findView(position);
            if (view == null) {
                logger.warn(MainActivity.class, "[无法找到对应视图] " + position);
                return;
            }
            if (action.equals(BroadcastAction.DOWNLOAD_PROGRESS) || action.equals(BroadcastAction.DOWNLOAD_START)) {
                int progress = intent.getIntExtra(DownloadManager.KEY_PROGRESS, 0);
                view.showStatus("正在下载", progress);
            } else if (BroadcastAction.DOWNLOAD_FAILED.equals(action) || BroadcastAction.UNZIPPING_FAILED.equals(action)) {
                view.showStatus("下载失败\n点击重试", -1);
            } else if (BroadcastAction.UNZIPPING.equals(action)) {
                view.showStatus("正在解压中", -1);
            } else if (BroadcastAction.DOWNLOAD_COMPLETE.equals(action) || BroadcastAction.UNZIPPING_FINISHED.equals(action)) {
                view.showStatus("下载完成", -1);
            }
        }
    }


}
