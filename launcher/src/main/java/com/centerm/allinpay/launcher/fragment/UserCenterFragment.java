package com.centerm.allinpay.launcher.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.activity.UserCenterActivity;
import com.centerm.allinpay.launcher.base.BaseActivity;
import com.centerm.allinpay.launcher.base.BaseFragment;
import com.centerm.allinpay.launcher.cont.BroadcastAction;
import com.centerm.allinpay.launcher.cont.PresKey;
import com.centerm.allinpay.launcher.net.Key;
import com.centerm.allinpay.launcher.net.ResponseHandler;
import com.centerm.allinpay.launcher.net.request.ModifyUserInfo;
import com.centerm.allinpay.launcher.net.response.BaseResponse;
import com.centerm.allinpay.launcher.net.response.GetOperListResponse;


import java.util.List;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/10.
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener {
    private EditText nameEdit;
    private EditText phoneEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user_center, null);
        view.findViewById(R.id.confirm_btn).setOnClickListener(this);
        final ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                ((BaseActivity) getActivity()).hideLoading();
                GetOperListResponse result = new GetOperListResponse(response);
                if (result.isSuccess()) {
                    initView(view, result.getOperList());
                    ((UserCenterActivity) getActivity()).setOperList(result.getOperList());
                } else {
                    initView(view, null);
                    ((BaseActivity) getActivity()).showAutoDismissMessageDialog("获取操作员信息失败");
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                ((BaseActivity) getActivity()).hideLoading();
                initView(view, null);
                ((BaseActivity) getActivity()).showAutoDismissMessageDialog("获取操作员信息失败");
            }
        };
        if (getActivity() instanceof UserCenterActivity) {
            UserCenterActivity activity = (UserCenterActivity) getActivity();
            if (activity.getOperList() != null) {
                initView(view, activity.getOperList());
            } else {
                ((BaseActivity) getActivity()).showLoading(null);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((UserCenterActivity) getActivity()).requestOperList(handler);
                    }
                }, 500);
            }
        }
        return view;
    }


    private void initView(View view, List<Map<String, String>> operList) {
        nameEdit = (EditText) view.findViewById(R.id.oper_name_edit);
        phoneEdit = (EditText) view.findViewById(R.id.oper_phone_edit);
        EditText operTypeEdit = (EditText) view.findViewById(R.id.oper_type_show);

        SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        nameEdit.setText(pres.getString(PresKey.OPER_NAME, ""));
        String type = pres.getString(PresKey.OPER_TYPE, "02");
        String operId = pres.getString(PresKey.OPER_ID, "");
        if ("01".equals(type)) {
            operTypeEdit.setText("管理员");
        } else {
            operTypeEdit.setText("普通操作员");
        }
        if (operList != null) {
            for (int i = 0; i < operList.size(); i++) {
                if (operList.get(i).get(Key.OperID).equals(operId)) {
                    phoneEdit.setText(operList.get(i).get(Key.OperPhoneNo));
                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_btn:
                String operName = nameEdit.getText().toString();
                String operPhone = phoneEdit.getText().toString();
                requestModify(operName, operPhone);
                break;
        }
    }

    private void requestModify(final String operName, String operPhone) {
        if (operName == null || operPhone == null) {
            return;
        }
        ((BaseActivity) getActivity()).showLoading(null);
        final SharedPreferences pres = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String userId = pres.getString(PresKey.USER_ID, "");
        String operId = pres.getString(PresKey.OPER_ID, "");
        String passwd = pres.getString(PresKey.PASSWD, "");
        ModifyUserInfo request = new ModifyUserInfo(userId, operId, passwd, operName, operPhone);
        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                ((BaseActivity) getActivity()).hideLoading();
                BaseResponse result = new BaseResponse(response);
                if (result.isSuccess()) {
                    ((BaseActivity) getActivity()).showMessageDialog("修改成功");
                    pres.edit().putString(PresKey.OPER_NAME, operName).commit();
                    if (getActivity() instanceof UserCenterActivity) {
                        ((UserCenterActivity) getActivity()).setOperList(null);
                    }
                    Intent intent = new Intent(BroadcastAction.MODIFY_USER_INFO_SUCCESS);
                    getActivity().sendBroadcast(intent);
                } else {
                    ((BaseActivity) getActivity()).showAutoDismissMessageDialog("修改失败，请重试");
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


}
