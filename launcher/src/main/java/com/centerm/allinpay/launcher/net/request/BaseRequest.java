package com.centerm.allinpay.launcher.net.request;


import com.centerm.allinpay.launcher.MyApplication;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.centerm.allinpay.launcher.net.Key.*;

/**
 * Created by linwanliang on 2016/2/29.
 * 在该类中进行网络请求基础参数的封装。
 */
public class BaseRequest {

    private final static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");
    private Map<String, String> params;

    private boolean jsonStream;

    /**
     * 构造函数。已对接口共有参数进行封装。
     *
     * @param jsonStream 是否通过json数据格式进行参数传递
     */
    public BaseRequest(boolean jsonStream) {
        params = new HashMap<String, String>();
        this.jsonStream = jsonStream;
        reset();
    }


    /**
     * 添加参数。
     *
     * @param key   参数名
     * @param value 参数值
     */
    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public void reset() {
        if (params == null) {
            return;
        }
        params.clear();
        params.put(TerminalID, MyApplication.getSn());
        params.put(PinpadID, MyApplication.getAipSn());
        params.put(PsamID, MyApplication.getAipSn());
        String time = DATE_FORMATTER.format(new Date());
        params.put(Series, time.substring(6, 12));
        params.put(TransTime, time);
        params.put(MAC,"000000000");
    }

    public Map<String, String> getParams() {
        return params;
    }

    public boolean isJsonStream() {
        return jsonStream;
    }

    public void setJsonStream(boolean jsonStream) {
        this.jsonStream = jsonStream;
    }

    public String getJsonParams() {
        JSONObject object = new JSONObject(getParams());
        return object.toString();
    }
}
