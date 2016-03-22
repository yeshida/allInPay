package com.centerm.allinpay.launcher.net.request;

import com.centerm.allinpay.launcher.net.Key;
import com.centerm.allinpay.launcher.utils.MD5Utils;

/**
 * Created by linwanliang on 2016/3/2.
 * 登录请求
 */
public class Login extends BaseRequest {

    private String userId, operId, passwd;

    public Login(String UserID, String OperID, String PassWord) {
        this(true, UserID, OperID, PassWord, true);
    }

    public Login(String UserID, String OperID, String PassWord, boolean encrypt) {
        this(true, UserID, OperID, PassWord, encrypt);
    }

    public Login(boolean jsonStream, String UserID, String OperID, String PassWord, boolean encrypt) {
        super(jsonStream);

        addParam(Key.TransCode, "T00051");
        addParam(Key.UserID, UserID);
        addParam(Key.OperID, OperID);
        if (encrypt) {
            PassWord = MD5Utils.getMD5Str(PassWord);
        }
        addParam(Key.PassWord, PassWord);
        this.userId = UserID;
        this.operId = OperID;
        this.passwd = PassWord;
    }

    public String getUserId() {
        return userId;
    }

    public String getOperId() {
        return operId;
    }

    public String getPasswd() {
        return passwd;
    }
}
