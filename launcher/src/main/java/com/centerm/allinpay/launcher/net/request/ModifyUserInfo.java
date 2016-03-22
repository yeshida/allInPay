package com.centerm.allinpay.launcher.net.request;

import com.centerm.allinpay.launcher.net.Key;
import com.centerm.allinpay.launcher.utils.MD5Utils;

/**
 * Created by linwanliang on 2016/3/10.
 */
public class ModifyUserInfo extends GetOperList {

    public ModifyUserInfo(String UserID, String OperID, String PassWord, String newPasswd) {
        super(UserID, OperID, PassWord);
        addParam(Key.TransCode,"T00053");
        addParam(Key.PassWord, MD5Utils.getMD5Str(PassWord));
        addParam(Key.NewPassWord, MD5Utils.getMD5Str(newPasswd));
    }

    public ModifyUserInfo(String UserID, String OperID, String PassWord, String operName, String operPhone) {
        super(UserID, OperID, PassWord);
        addParam(Key.TransCode,"T00053");
        if (operName != null) {
            addParam(Key.OperName, operName);
        }
        if (operPhone != null) {
            addParam(Key.OperPhoneNo, operPhone);
        }
    }
}
