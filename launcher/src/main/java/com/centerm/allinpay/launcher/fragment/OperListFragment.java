package com.centerm.allinpay.launcher.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.activity.UserCenterActivity;
import com.centerm.allinpay.launcher.base.BaseFragment;
import com.centerm.allinpay.launcher.net.Key;
import com.centerm.allinpay.launcher.net.ResponseHandler;
import com.centerm.allinpay.launcher.net.request.GetOperList;
import com.centerm.allinpay.launcher.net.response.GetOperListResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/10.
 */
public class OperListFragment extends BaseFragment {

    private final static String[] FROM = new String[]{Key.OperName, Key.OperID, Key.OperPhoneNo, Key.TypeName};
    private final static int[] TO = new int[]{R.id.oper_name_show, R.id.oper_id_show, R.id.oper_phone_show, R.id.oper_type_show};
    private final static int RESOURCE = R.layout.user_center_oper_list_item;
    private ListView operList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oper_list, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        operList = (ListView) view.findViewById(R.id.oper_list_v);
        if (getActivity() instanceof UserCenterActivity) {
            final UserCenterActivity activity = (UserCenterActivity) getActivity();
            if ((activity.getOperList()) == null) {
                logger.info(OperListFragment.class, "[联网获取操作员列表]");
                ResponseHandler handler = new ResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        activity.hideLoading();
                        GetOperListResponse result = new GetOperListResponse(response);
                        if (result.isSuccess()) {
                            activity.setOperList(result.getOperList());
                            operList.setAdapter(new SimpleAdapter(activity, result.getOperList(), RESOURCE, FROM, TO));
                        } else {
                            activity.showMessageDialog(result.getReturnMsg());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String response, Throwable error) {
                        activity.hideLoading();
                        activity.showMessageDialog("获取操作员列表失败，请重试");
                    }
                };
                activity.showLoading(null);
                activity.requestOperList(handler);
            } else {
                logger.info(OperListFragment.class, "[操作员列表已存在]");
                operList.setAdapter(new SimpleAdapter(activity, activity.getOperList(), RESOURCE, FROM, TO));
            }
        }
    }


}
