package com.centerm.allinpay.launcher.net;

import android.content.Context;
import android.os.Environment;
import android.os.StrictMode;

import com.centerm.allinpay.launcher.net.request.BaseRequest;
import com.centerm.allinpay.launcher.utils.Log4d;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.KeyStore;

/**
 * Created by linwanliang on 2016/2/29.
 */
public class MyHttpClient {

    private final static Log4d logger = Log4d.getInstance();
    private final static String TAG = MyHttpClient.class.getSimpleName();
    private static MyHttpClient instance;
    private static Address commonAddr;


    private AsyncHttpClient innerClient;
    private String charSet = "UTF-8";

    private MyHttpClient() {
        innerClient = new AsyncHttpClient();
        innerClient.setResponseTimeout(20 * 1000);
        innerClient.setConnectTimeout(20 * 1000);
        Environment.getDataDirectory();
    }

    public static MyHttpClient getInstance() {
        if (instance == null) {
            instance = new MyHttpClient();
        }
        return instance;
    }

    public static void setAddress(Address addr) {
        MyHttpClient.commonAddr = addr;
    }

    public static Address getAddress() {
        return commonAddr;
    }

    /**
     * 通过POST方式发送一个Http请求。
     *
     * @param context 当前环境上下文，不能为空
     * @param addr    请求地址
     * @param request 请求实体类，包含请求参数
     * @param handler 响应接收器
     */
    public void post(Context context, Address addr, BaseRequest request, final ResponseHandler handler) {
        if (addr == null) {
            logger.warn(MyHttpClient.class, "请求地址为空");
            return;
        }
        //HTTPS通道
        if (addr.isHttps()) {
            innerClient.setSSLSocketFactory(createSSLSocketFactory());
        } else {
            innerClient.setSSLSocketFactory(null);
        }
        RequestParams params = new RequestParams(request.getParams());
        if (request.isJsonStream()) {
            params.setUseJsonStreamer(true);
        }
        logger.info(TAG, "[请求地址]--" + addr.getAddress());
        logger.info(TAG, "[请求参数]--" + request.getJsonParams());
        innerClient.post(context, addr.generateUrl(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = "";
                try {
                    response = new String(bytes, charSet);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    response = new String(bytes);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                logger.info(TAG, "[请求成功]--" + response);
                if (handler != null) {
                    handler.onSuccess(i, response);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                String response = null;
                try {
                    response = new String(bytes, charSet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.warn(TAG, "[请求失败]--" + (throwable == null ? "" : throwable.toString()));
                if (handler != null) {
                    handler.onFailure(i, response, throwable);
                }
            }
        });
    }


    public void post(Context context, BaseRequest request, final ResponseHandler handler) {
        post(context, commonAddr, request, handler);
    }

    /**
     * 构造SSL通道。
     *
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactory() {
       MySslSocketFactory sf = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            sf = new MySslSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sf;
    }

    /**
     * 获取当前编码
     *
     * @return
     */
    public String getCharSet() {
        return charSet;
    }

    /**
     * 设置编码
     *
     * @param charSet
     */
    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public AsyncHttpClient getInnerClient() {
        return innerClient;
    }

    //////////////////////////////////////////////////////////////
    public void test(Context context) {
        String url = "https://123.138.28.20:7055/ACPS_ONL/GateWayAction.do";
        RequestParams params = new RequestParams();
        params.setUseJsonStreamer(true);
        params.put(Key.TransCode, "T00051");
        params.put(Key.TerminalID, "AIP123456789");
        params.put(Key.PinpadID, "pinpadid");
        params.put(Key.PsamID, "psamid");
        params.put(Key.Series, "seires");
        params.put(Key.TransTime, "201602291410");
        params.put(Key.UserID, "13544555503");
        params.put(Key.OperID, "99");
        params.put(Key.PassWord, "123456");
        innerClient.setSSLSocketFactory(createSSLSocketFactory());
        innerClient.post(context, url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] responseBody) {

                        try {
                            String response = new String(responseBody, "UTF-8");
                            logger.info("test", "【网络返回：】 == " + response);
                        } catch (UnsupportedEncodingException e) {
                            logger.info("test", "异常：" + e.toString());
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, org.apache.http.Header[] headers,
                                          byte[] responseBody, Throwable error) {
                        logger.warn("test", "【请求失败】");
                    }
                }
        );
    }

    public void test2(Context context) {
        String url = "http://123.138.28.20:18057/download/appversion/zhuanxiu/base/html/index.html/20151222114118/0/zhuanxiu_1.0.zip";
        innerClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                logger.info("test", "请求成功");
                if (headers != null) {
                    for (Header header : headers) {
                        logger.info("test", "====" + header.getName() + "    " + header.getValue());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                logger.warn("test", "请求失败" + error.toString());

            }
        });
    }

}
