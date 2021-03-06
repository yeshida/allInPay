package com.centerm.allinpay.launcher.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.centerm.allinpay.launcher.net.MyHttpClient;
import com.centerm.allinpay.launcher.utils.Log4d;

/**
 * Created by linwanliang on 2016/2/26.
 */
public class BaseFragment extends Fragment {
    protected static final Log4d logger = Log4d.getInstance();
    protected static MyHttpClient httpClient = MyHttpClient.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
