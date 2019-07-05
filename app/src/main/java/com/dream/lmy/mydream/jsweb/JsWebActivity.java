package com.dream.lmy.mydream.jsweb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.dream.lmy.mydream.R;

public class JsWebActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = JsWebActivity.class.getName();
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsweb_layout);
        initView();
        initData();
    }

    void initView() {
        mWebView = findViewById(R.id.web_js);

    }

    void initData() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置js交互权限
//        webSettings.set
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//设置允许JS弹框
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.e(TAG, "ALERT");
                return true;
//                return super.onJsAlert(view, url, message, result);
            }
        });

        mWebView.addJavascriptInterface(new JsObject(),"android");
        mWebView.loadUrl("file:///android_asset/test.html");

//        mWebView.loadUrl("javascript:callJS()");

        // 4.4版本之后才可以用
        mWebView.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.e(TAG, "ALERT" + value);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public class JsObject extends Object {

        @JavascriptInterface
        public void android() {

        }
    }
}
