package com.centerm.allinpay.launcher.net.request;

import com.centerm.allinpay.launcher.net.Key;
import com.centerm.allinpay.launcher.utils.MD5Utils;

/**
 * Created by linwanliang on 2016/3/11.
 */
public class AddOper extends GetOperList {
    public AddOper(String UserID, String OperID, String PassWord,
                   String newOperId, String newPasswd, String newOperName, String newOperPhone, String operType) {
        super(UserID, OperID, PassWord);
        addParam(Key.TransCode, "T00052");
        addParam(Key.NewOperID, newOperId);
        addParam(Key.NewPassWord, MD5Utils.getMD5Str(newPasswd));
        addParam(Key.NewOperName, newOperName);
        addParam(Key.NewOperPhoneNo, newOperPhone);
        addParam(Key.OperType, operType);
    }
}
