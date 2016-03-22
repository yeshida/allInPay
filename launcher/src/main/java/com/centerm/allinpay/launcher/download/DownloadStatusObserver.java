package com.centerm.allinpay.launcher.download;

import java.io.File;

/**
 * Created by linwanliang on 2016/3/8.
 * @deprecated 不用了
 */
public interface DownloadStatusObserver {

    public void onCancel();

    public void onStart();

    public void onProgress();

    public void onSuccess(int statusCode, File file);

    public void onFailure(int statusCode, Throwable throwable, File file);


}
