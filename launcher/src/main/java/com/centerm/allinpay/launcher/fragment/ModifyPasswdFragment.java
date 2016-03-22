package com.centerm.allinpay.launcher.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.base.BaseActivity;
import com.centerm.allinpay.launcher.base.BaseFragment;
import com.centerm.allinpay.launcher.cont.PresKey;
import com.centerm.allinpay.launcher.net.ResponseHandler;
import com.centerm.allinpay.launcher.net.request.ModifyUserInfo;
import com.centerm.allinpay.launcher.net.response.BaseResponse;
import com.centerm.allinpay.launcher.utils.MD5Utils;
import com.centerm.allinpay.launcher.utils.StringUtils;

/**
 * Created by linwanliang on 2016/3/10.
 */
public class ModifyPasswdFragment extends BaseFragment implements View.OnClickListener {

    private EditText oldPwdEdit, newPwdEdit, againPwdEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_passwd, null);
        oldPwdEdit = (EditText) view.findViewById(R.id.old_pwd_edit);
        newPwdEdit = (EditText) view.findViewById(R.id.new_pwd_edit);
        againPwdEdit = (EditText) view.findViewById(R.id.new_pwd_again_edit);
        view.findViewById(R.id.confirm_btn).setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_btn:
                if (checkInput()) {
                    requestModifyPwd(oldPwdEdit.getText().toString(), newPwdEdit.getText().toString());
                }
                break;
        }
    }

    private boolean checkInput() {
        String oldPwd = oldPwdEdit.getText().toString();
        String newPwd = newPwdEdit.getText().toString();
        String againPwd = againPwdEdit.getText().toString();
        String checkOld = StringUtils.checkPasswdStr(oldPwd);
        if ("OK".equals(checkOld)) {
            String checkNew = StringUtils.checkPasswdStr(newPwd);
            if ("OK".equals(checkNew)) {
                if (newPwd.equals(againPwd)) {
                    return true;
                } else {
                    ((BaseActivity) getActivity()).showAutoDismissMessageDialog("两次输入的密码不一致");
                }
            } else {
                ((BaseActivity) getActivity()).showAutoDismissMessageDialog("新" + checkNew);
            }
        } else {
            ((BaseActivity) getActivity()).showAutoDismissMessageDialog("旧" + checkOld);
        }
        return false;
    }

    private void requestModifyPwd(final String pwd, final String newPwd) {
        ((BaseActivity) getActivity()).showLoading(null);
        final SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = pres.getString(PresKey.USER_ID, "");
        String operId = pres.getString(PresKey.OPER_ID, "");
        ModifyUserInfo request = new ModifyUserInfo(userId, operId, pwd, newPwd);
        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                ((BaseActivity) getActivity()).hideLoading();
                BaseResponse result = new BaseResponse(response);
                if (result.isSuccess()) {
                    clear();
                    ((BaseActivity) getActivity()).showMessageDialog("修改成功");
                    pres.edit().putString(PresKey.PASSWD, MD5Utils.getMD5Str(newPwd)).commit();
                } else {
                    ((BaseActivity) getActivity()).showAutoDismissMessageDialog(result.getReturnMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                ((BaseActivity) getActivity()).hideLoading();
                ((BaseActivity) getActivity()).showAutoDismissMessageDialog("修改失败，请重试");
            }
        };
        httpClient.post(getActivity(), request, handler);
    }

    private void clear() {
        oldPwdEdit.setText("");
        newPwdEdit.setText("");
        againPwdEdit.setText("");
    }


}
