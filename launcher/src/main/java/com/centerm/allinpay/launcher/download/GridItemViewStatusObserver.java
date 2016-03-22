package com.centerm.allinpay.launcher.download;

import android.content.Context;

import com.centerm.allinpay.launcher.utils.Log4d;
import com.centerm.allinpay.launcher.view.GridItemView;


import java.io.File;

/**
 * Created by linwanliang on 2016/3/8.
 *
 * @deprecated 不用了
 */
public class GridItemViewStatusObserver implements DownloadStatusObserver {

    private final static Log4d logger = Log4d.getInstance();
    private final static String TAG = GridItemViewStatusObserver.class.getSimpleName();
    private Context context;
    private GridItemView bindedView;

    public GridItemViewStatusObserver(Context context, GridItemView bindedView) {
        this.context = context;
        this.bindedView = bindedView;
        bindedView.setStatusObserver(this);
    }

    @Override
    public void onCancel() {
        logger.info(TAG, "");
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onProgress() {

    }

    @Override
    public void onSuccess(int statusCode, File file) {

    }

    @Override
    public void onFailure(int statusCode, Throwable throwable, File file) {

    }


}
