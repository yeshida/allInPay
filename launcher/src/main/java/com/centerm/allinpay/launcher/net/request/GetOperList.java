package com.centerm.allinpay.launcher.net.request;

import com.centerm.allinpay.launcher.net.Key;

/**
 * Created by linwanliang on 2016/3/10.
 * 获取操作员列表
 */
public class GetOperList extends Login {

    public GetOperList(String UserID, String OperID, String PassWord) {
        super(true, UserID, OperID, PassWord, false);
        addParam(Key.TransCode,"T00054");
        addParam(Key.KeyFlag, "01");
    }
}
