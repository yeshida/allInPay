package com.centerm.allinpay.launcher.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.activity.UserCenterActivity;
import com.centerm.allinpay.launcher.base.BaseFragment;
import com.centerm.allinpay.launcher.net.ResponseHandler;
import com.centerm.allinpay.launcher.net.request.AddOper;
import com.centerm.allinpay.launcher.net.response.BaseResponse;
import com.centerm.allinpay.launcher.utils.StringUtils;
import com.centerm.allinpay.launcher.utils.ViewUtils;

/**
 * Created by linwanliang on 2016/3/10.
 * 添加操作员界面
 */
public class AddOperFragment extends BaseFragment implements View.OnClickListener {

    private EditText operNameEdit;
    private EditText operIdEdit;
    private EditText operPhoneEdit;
    private RadioGroup operTypeChooser;
    private EditText passwdEdit;
    private EditText pwdAgainEdit;

    private String newOperType;
    private String userId, operId, passwd;

    public AddOperFragment() {
        super();
    }

    public AddOperFragment(String userId, String operId, String passwd) {
        super();
        this.operId = operId;
        this.passwd = passwd;
        this.userId = userId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_oper, null);
        initView(view);
        return view;
    }

    private void initView(View view) {

        operNameEdit = (EditText) view.findViewById(R.id.oper_name_edit);
        operIdEdit = (EditText) view.findViewById(R.id.oper_id_edit);
        operPhoneEdit = (EditText) view.findViewById(R.id.oper_phone_edit);
        passwdEdit = (EditText) view.findViewById(R.id.passwd_edit);
        pwdAgainEdit = (EditText) view.findViewById(R.id.passwd_again_edit);

        operTypeChooser = (RadioGroup) view.findViewById(R.id.oper_type_chooser);
        operTypeChooser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.manager_tab:
                        newOperType = "01";
                        break;
                    case R.id.oper_tab:
                        newOperType = "02";
                        break;
                }
            }
        });
        operTypeChooser.check(R.id.oper_tab);

        view.findViewById(R.id.cancel_btn).setOnClickListener(this);
        view.findViewById(R.id.confirm_btn).setOnClickListener(this);
        view.findViewById(R.id.see_pwd_btn).setOnClickListener(this);
        view.findViewById(R.id.see_pwd_btn2).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                if (getActivity() instanceof UserCenterActivity) {
                    ((UserCenterActivity) getActivity()).onShowOperListFragment();
                }
                break;

            case R.id.confirm_btn:
                requestAddOper();
                break;

            case R.id.see_pwd_btn:
                tooglePasswdVisiable(passwdEdit);
                break;

            case R.id.see_pwd_btn2:
                tooglePasswdVisiable(pwdAgainEdit);
                break;
        }
    }

    private void tooglePasswdVisiable(EditText view) {
        int inputType = view.getInputType();
        if (inputType == EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            view.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD | EditorInfo.TYPE_CLASS_TEXT);
        } else {
            String text = view.getText().toString();
            view.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            view.setText(text);
        }
    }

    private void requestAddOper() {
        String newName = getString(operNameEdit);
        String newId = getString(operIdEdit);
        String newPhone = getString(operPhoneEdit);
        String newPasswd = getString(passwdEdit);
        String newPwdAgain = getString(pwdAgainEdit);

        if (StringUtils.isStrNull(newName)) {
            showToast("用户名不能为空");
            return;
        }
        if (StringUtils.isStrNull(newId)) {
            showToast("操作员号不能为空");
            return;
        }
        if (!StringUtils.isMobileNumValid(newPhone)) {
            showToast("手机号不合法");
            return;
        }

        String checkPasswd = StringUtils.checkPasswdStr(newPasswd);
        if (!"OK".equals(checkPasswd)) {
            showToast(checkPasswd);
            return;
        }
        if (!newPwdAgain.equals(newPasswd)) {
            showToast("两次输入密码不一致");
            return;
        }
        ((UserCenterActivity) getActivity()).showLoading(null);
        AddOper request = new AddOper(userId, operId, passwd,
                newId, newPasswd, newName, newPhone, newOperType);
        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                ((UserCenterActivity) getActivity()).hideLoading();
                BaseResponse result = new BaseResponse(response);
                if (result.isSuccess()) {
                    showToast("新增操作员成功");
                    if (getActivity() instanceof UserCenterActivity) {
                        ((UserCenterActivity) getActivity()).setOperList(null);
                        ((UserCenterActivity) getActivity()).onShowOperListFragment();
                    }
                } else {
                    showToast(result.getReturnMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                ((UserCenterActivity) getActivity()).hideLoading();
                showToast("新增操作员失败，请重试");
            }
        };
        httpClient.post(getActivity(), request, handler);
    }

    private void showToast(String content) {
        if (getActivity() instanceof UserCenterActivity) {
            ((UserCenterActivity) getActivity()).showAutoDismissMessageDialog(content);
        } else {
            ViewUtils.showToast(getActivity(), content);
        }
    }


    private String getString(EditText editText) {
        return editText.getText().toString();
    }
}
