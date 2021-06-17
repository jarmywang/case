package com.example.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.Choreographer;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.arnold.common.architecture.utils.Preconditions;
import com.arnold.common.mvp.BaseModel;
import com.arnold.common.network.utils.ZipHelper;
import com.eebochina.train.analytics.PluginAutoTrackHelper;


public class Main5Activity extends AppCompatActivity {

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        WebView webView = findViewById(R.id.wv);
        WebSettings webSettings = webView.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);

        // 通过addJavascriptInterface()将Java对象映射到JS对象
//        webView.addJavascriptInterface(new AndroidToJs(), "wx");//AndroidtoJS类对象映射到js的test对象

//        webView.loadUrl("https://wework-h5-dev.2haohr.com/approval/detail?id=3649bb56d47a4688befc2c5aa735a278&accesstoken=ityjhapvev1b0ncsckosz03lkwkuwdzu");
//        webView.loadUrl("https://wework-h5-dev.2haohr.com/approval/home?accesstoken=ityjhapvev1b0ncsckosz03lkwkuwdzu");
        webView.loadUrl("https://wework-h5-test.2haohr.com/self-serve?accesstoken=d9x1xljjyuwlu662to633h23mrreox7g");
    }

    private void test() {
        Handler handler = new Handler();
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {

            }
        });

        PluginAutoTrackHelper pluginAutoTrackHelper;
        BaseModel baseModel;
        com.arnold.common.mvvm.BaseModel baseModel1;
        Preconditions preconditions;
        ZipHelper zipHelper;

    }

}

//interface JsCallBack {
//
//}
//
//class AndroidToJs {
//    @JavascriptInterface
//    public void invoke(String msg, Object o) {
//        System.out.println("JS成功调用了Android的方法"+msg);
//    }
//}