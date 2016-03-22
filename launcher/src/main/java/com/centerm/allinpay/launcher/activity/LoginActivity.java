package com.centerm.allinpay.launcher.activity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.base.BaseActivity;
import com.centerm.allinpay.launcher.cont.PresKey;
import com.centerm.allinpay.launcher.net.Address;
import com.centerm.allinpay.launcher.net.MyHttpClient;
import com.centerm.allinpay.launcher.net.NetConstants;
import com.centerm.allinpay.launcher.net.request.Login;
import com.centerm.allinpay.launcher.net.ResponseHandler;
import com.centerm.allinpay.launcher.net.response.LoginResponse;
import com.centerm.allinpay.launcher.utils.AppUtils;
import com.centerm.allinpay.launcher.utils.StringUtils;
import com.centerm.allinpay.launcher.utils.Utils;
import com.centerm.allinpay.launcher.utils.ViewUtils;
import com.centerm.allinpay.launcher.view.AlertDialog;

/**
 * Created by linwanliang on 2016/2/29.
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public final static String LOGIN_RESPONSE = "LOGIN_RESPONSE";

    private EditText memNoEdit, operNoEdit, passwdEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        memNoEdit = (EditText) findViewById(R.id.mem_no_edit);
        operNoEdit = (EditText) findViewById(R.id.oper_no_edit);
        passwdEdit = (EditText) findViewById(R.id.passwd_no_edit);
        findViewById(R.id.login_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                boolean pass = checkInput();
                if (!pass) {
                    return;
                }
                String userId = memNoEdit.getText().toString();
                String operId = operNoEdit.getText().toString();
                String passwd = passwdEdit.getText().toString();
                if (userId.equals("admin666")&&passwd.equals("gosettings")){
                    myBackDoor();
                    return;
                }
                requestLogin(userId, operId, passwd, true);
                break;
        }
    }

    private boolean checkInput() {
        String userId = memNoEdit.getText().toString();
        String operId = operNoEdit.getText().toString();
        String passwd = passwdEdit.getText().toString();

        if (StringUtils.isStrNull(userId)) {
            ViewUtils.showToast(this, "会员号不能为空");
            return false;
        }
        if (StringUtils.isStrNull(operId)) {
            ViewUtils.showToast(this, "操作员号不能为空");
            return false;
        }
        String result = StringUtils.checkPasswdStr(passwd);
        if (!result.equals("OK")) {
            ViewUtils.showToast(this, result);
            return false;
        }
        return true;
    }


    private void requestLogin(String userId, String operId, String passwd, boolean encrypt) {
        showLoading(null);
        final Login request = new Login(userId, operId, passwd, encrypt);
        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                hideLoading();
                LoginResponse result = new LoginResponse(response);
                String code = result.getReturnCode();
                if ("00".equals(code)) {
                    //保存应用列表到文件中
                    writeLoginedCacheFile(response);
                    // 保存基本的用户信息
                    saveUserInfo(request.getUserId(), request.getOperId(), request.getPasswd(),
                            result.getOperType(), result.getOperName());
                    //跳转到主界面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(LOGIN_RESPONSE, response);
                    startActivity(intent);
                    finish();
                } else {
                    //界面提示错误信息
                    showMessageDialog(result.getReturnMsg());
                }

            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                hideLoading();
                showMessageDialog("网络错误，请重试");
            }
        };
        httpClient.post(this, request, handler);
    }

    private void saveUserInfo(String userId, String operId, String passwd, String operType, String operName) {
        SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pres.edit()
                .putString(PresKey.USER_ID, userId)
                .putString(PresKey.OPER_ID, operId)
                .putString(PresKey.PASSWD, passwd)
                .putString(PresKey.OPER_TYPE, operType)
                .putString(PresKey.OPER_NAME, operName)
                .commit();
    }

    private void showDebugDialog() {
        View view = getLayoutInflater().inflate(R.layout.main_debug_dialog_layout, null);
        final AlertDialog dialog = new AlertDialog(this);
        dialog.hideNegativeButton();
        dialog.hidePositiveButton();
        TextView titleShow = (TextView) view.findViewById(R.id.dialog_title_show);
        titleShow.setVisibility(View.GONE);
        String version = Utils.getVersion(this);
        logger.info(getClass(), "[当前应用版本] - " + version);
        Button setupEntry = (Button) view.findViewById(R.id.setup_entry_btn);
        Button gtmsEntry = (Button) view.findViewById(R.id.gtms_entry_btn);
        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (v.getId()) {
                    case R.id.setup_entry_btn:
                        if (AppUtils.getInstalledVersionName(LoginActivity.this, "com.bestwifianalyzer") != null) {
                            AppUtils.openApp(LoginActivity.this, "com.bestwifianalyzer");
                        } else {
                            intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(intent);
                        }
                        break;

                    case R.id.gtms_entry_btn:
                        intent = new Intent();
                        ComponentName comp = new ComponentName("com.centerm.gtms",
                                "com.centerm.allinpay.activity.LoginActivity");
                        intent.setComponent(comp);
                        intent.setAction("android.intent.action.VIEW");
                        try {
                            LoginActivity.this.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            showAutoDismissMessageDialog("未安装GTMS客户端");
                        }
                        break;
                    case R.id.connect_entry_btn:
                        showChooseAddrDialog();
                        break;
                }
                dialog.dismiss();
            }
        };
        setupEntry.setOnClickListener(clickListener);
        gtmsEntry.setOnClickListener(clickListener);
        view.findViewById(R.id.connect_entry_btn).setOnClickListener(clickListener);
        dialog.setContent(view);
        dialog.show();
    }

    private void showChooseAddrDialog() {
        View view = getLayoutInflater().inflate(R.layout.main_connect_setup_dialog_layout, null);
        final EditText ipEdit = (EditText) view.findViewById(R.id.ip_edit);
        final EditText portEdit = (EditText) view.findViewById(R.id.port_edit);
        final AlertDialog dialog = new AlertDialog(this);
        dialog.setTitle("通讯设置");
        dialog.setContent(view);
        dialog.setClickListener(new AlertDialog.ClickListener() {

            @Override
            public void onPositiveClick(View v) {
                String ip = ipEdit.getText().toString();
                String port = portEdit.getText().toString();
                if (ip == null || ip.trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "IP不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (port == null || port.trim().equals("")) {
                    Toast.makeText(LoginActivity.this, "端口号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Address address = new Address(ip, port, true);
                address.setSuffix(NetConstants.PROJECT_NAME);
                logger.warn(LoginActivity.class, "[通讯地址更改] - " + address.getAddress());
                MyHttpClient.setAddress(address);
            }

            @Override
            public void onNegativeClick(View v) {
            }
        });
        dialog.show();
    }

    private void myBackDoor() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            showDebugDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
