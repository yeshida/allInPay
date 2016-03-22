package com.centerm.allinpay.launcher.activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.base.BaseActivity;
import com.centerm.allinpay.launcher.cont.PresKey;
import com.centerm.allinpay.launcher.fragment.AddOperFragment;
import com.centerm.allinpay.launcher.fragment.ModifyPasswdFragment;
import com.centerm.allinpay.launcher.fragment.OperListFragment;
import com.centerm.allinpay.launcher.fragment.UserCenterFragment;
import com.centerm.allinpay.launcher.net.ResponseHandler;
import com.centerm.allinpay.launcher.net.request.GetOperList;
import com.centerm.allinpay.launcher.utils.ScreenUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/10.
 */
public class UserCenterActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = UserCenterActivity.class.getSimpleName();
    private RadioGroup radioGroup;
    private RadioButton userCenterBtn, modifyPwdBtn, operManagBtn;
    private boolean isAdjustUi;
    private TextView titleShow;
    private List<Map<String, String>> operList;

    private String userId, operId, operType, passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        //从本地存储中初始化用户信息
        initUserInfo();

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        userCenterBtn = (RadioButton) findViewById(R.id.user_center_btn);
        modifyPwdBtn = (RadioButton) findViewById(R.id.modify_pwd_btn);
        operManagBtn = (RadioButton) findViewById(R.id.oper_manag_btn);
        titleShow = (TextView) findViewById(R.id.title_show);
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.add_oper_btn).setOnClickListener(this);

        //如果是平板布局，需要做一些调整
        ScreenUtils screenUtils = new ScreenUtils(this);
        if (screenUtils.isTablet()) {
            //设置各个Tab的padding值
            final ViewTreeObserver observer = radioGroup.getViewTreeObserver();
            ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (!isAdjustUi) {
                        int totalHeight = radioGroup.getHeight();
                        int itemHeight = userCenterBtn.getHeight();
                        int space = totalHeight - itemHeight * 3;
                        int itemPadding = space / 6;
                        userCenterBtn.setPadding(0, itemPadding, 0, itemPadding);
                        modifyPwdBtn.setPadding(0, itemPadding, 0, itemPadding);
                        operManagBtn.setPadding(0, itemPadding, 0, itemPadding);
                        isAdjustUi = true;
                    }
                }
            };
            observer.addOnGlobalLayoutListener(layoutListener);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.user_center_btn:
                        onShowUserCenterFragment();
                        break;

                    case R.id.modify_pwd_btn:
                        onShowModifyPwdFragment();
                        break;

                    case R.id.oper_manag_btn:
                        onShowOperListFragment();
                        break;
                }
            }
        });
        userCenterBtn.setChecked(true);
    }

    private void initUserInfo() {
        SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = pres.getString(PresKey.USER_ID, "");
        operId = pres.getString(PresKey.OPER_ID, "");
        operType = pres.getString(PresKey.OPER_TYPE, "02");
        passwd = pres.getString(PresKey.PASSWD, "");
    }


    public void requestOperList(ResponseHandler handler) {
        GetOperList request = new GetOperList(userId, operId, passwd);
        httpClient.post(this, request, handler);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;

            case R.id.add_oper_btn:
                if (!"01".equals(operType)) {
                    showMessageDialog("普通操作员无此权限");
                    return;
                }
                titleShow.setText("新增操作员");
                getFragmentManager().beginTransaction().replace(R.id.frag_container,
                        new AddOperFragment(userId, operId, passwd)).commit();
                v.setVisibility(View.GONE);
                break;
        }
    }

    public List<Map<String, String>> getOperList() {
        return operList;
    }

    public void setOperList(List<Map<String, String>> operList) {
        this.operList = operList;
    }

    public void onShowOperListFragment() {
        findViewById(R.id.add_oper_btn).setVisibility(View.VISIBLE);
        titleShow.setText("操作员管理");
        getFragmentManager().beginTransaction().replace(R.id.frag_container, new OperListFragment()).commit();
    }

    private void onShowModifyPwdFragment() {
        findViewById(R.id.add_oper_btn).setVisibility(View.GONE);
        titleShow.setText("修改密码");
        getFragmentManager().beginTransaction().replace(R.id.frag_container, new ModifyPasswdFragment()).commit();
    }

    private void onShowUserCenterFragment() {
        findViewById(R.id.add_oper_btn).setVisibility(View.GONE);
        titleShow.setText("个人中心");
        getFragmentManager().beginTransaction().replace(R.id.frag_container, new UserCenterFragment()).commit();
    }
}
