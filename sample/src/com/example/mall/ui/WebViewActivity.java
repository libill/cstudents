package com.example.mall.ui;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mall.R;
import com.example.mall.base.BaseActivity;
import com.example.mall.db.DBManager;
import com.example.mall.model.Favorite;
import com.example.mall.util.FormatUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class WebViewActivity extends BaseActivity implements OnClickListener {
    private String TAG = "WebViewActivity";
    private WebView mWebView;
    private static ProgressBar loadProgressBar;

    DBManager dbManager = null;

    public interface RefreshListener {
        public void onRefresh();
    }

    private boolean isShowBottom = false;

    @Override
    public void initView() {
        setContentView(R.layout.activity_webview);
        ViewUtils.inject(this);
        setActionBarBack(true, null);
        initUI();
        initData();
    }

    private void initUI() {
        mWebView = (WebView) findViewById(R.id.webview);
        loadProgressBar = (ProgressBar) findViewById(R.id.pb);
        WebSettings settings = mWebView.getSettings();
        setWebViewSettings(settings);

        mWebView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }

        });
        mWebView.setWebChromeClient(new MyWebChromeClient());
    }

    private void setWebViewSettings(WebSettings settings) {
        // webview 初始化设置
        settings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new SampleWebViewClient());
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
    }

    private void initData() {
        dbManager = DBManager.getDBManager();
        // webview 加载页面
        Bundle bundle = getIntent().getExtras();
        String click_url = null;
        if (bundle != null) {
            click_url = bundle.getString("click_url");
            isShowBottom = bundle.getBoolean("isShowBottom", false);
        }

        if (click_url == null || click_url.equalsIgnoreCase("")) {
            click_url = "http://www.cnblogs.com/liqw";
        }
        // mWebView.loadUrl("file:///android_asset/html/index.html");
        //mWebView.loadUrl("file:///android_asset/html/protocol/index.html");
        mWebView.loadUrl(click_url);

    }

    private class SampleWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            loadProgressBar.setProgress(newProgress);

            if (newProgress == 100) {

                // 视觉上让手机用户看到加载完毕，进度条到100后再消失
                loadProgressBar.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        loadProgressBar.setVisibility(View.GONE);
                    }
                });
            } else {
                if (loadProgressBar.getVisibility() == View.GONE) {
                    loadProgressBar.setVisibility(View.VISIBLE);
                }
            }
            super.onProgressChanged(view, newProgress);

            Log.i("rrr", newProgress + "");
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setWebViewTitle(title);
        }

    }

    private void setWebViewTitle(String title) {
        String titleName = title;
        if (titleName.length() > 16) {
            titleName = titleName.substring(0, 16) + "..";
        }
        setActionBarTitle(titleName);
    }

    /**
     * 点击返回物理键，返回上一个网页记录
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio_button0:
                break;
            default:
                break;
        }
    }

}