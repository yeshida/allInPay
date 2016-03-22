package com.centerm.allinpay.launcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.centerm.allinpay.launcher.R;
import com.centerm.allinpay.launcher.base.BaseActivity;

/**
 * Created by linwanliang on 2016/3/8.
 */
public class WebViewActivity extends BaseActivity {

    public final static String KEY_INDEX_HTML = "KEY_INDEX_HTML";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Intent intent = getIntent();
        String index = intent.getStringExtra(KEY_INDEX_HTML);
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///" + index);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
