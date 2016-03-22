package com.centerm.allinpay.launcher.net.response;

import org.json.JSONException;
import org.json.JSONObject;

import static com.centerm.allinpay.launcher.net.Key.*;

/**
 * Created by linwanliang on 2016/2/29.
 */
public class BaseResponse {

    private String response;
    private String returnCode;
    private String returnMsg;
    private boolean isSuccess;
    protected JSONObject root;


    public BaseResponse(String response) {
        this.response = response;
        try {
            root = new JSONObject(response);
            returnCode = root.getString(RetCode);
            if ("00".equals(returnCode)) {
                isSuccess = true;
            }
            returnMsg = root.getString(RetMsg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getRoot() {
        return root;
    }

    public String getResponse() {
        return response;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
