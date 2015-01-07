package com.example.mall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.mall.R;

public class WebViewActivity extends Activity {
	private WebView mWebView;
	private Button iv_return;
	private static ProgressBar loadProgressBar;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		mWebView = (WebView) findViewById(R.id.webview);
		loadProgressBar = (ProgressBar) findViewById(R.id.pb);

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new SampleWebViewClient());
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setLoadsImagesAutomatically(true);
		mWebView.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
		mWebView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Log.i("ontouch", "12313131");
				return false;
			}

		});

		mWebView.setWebChromeClient(new MyWebChromeClient());

		Bundle bundle = getIntent().getExtras();
		String click_url = null;
		if (bundle != null) {
			click_url = bundle.getString("click_url");
		}

		if (click_url == null || click_url.equalsIgnoreCase("")) {
			click_url = "http://www.baidu.com";
		}
		mWebView.loadUrl("file:///android_asset/html/index.html");
		// mWebView.loadUrl(click_url);
		goToReturn();
	}

	private static class SampleWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	
	private static class MyWebChromeClient extends WebChromeClient {
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
				if (loadProgressBar.getVisibility() == View.GONE){
					loadProgressBar.setVisibility(View.VISIBLE);
				}
			}
			super.onProgressChanged(view, newProgress);

			Log.i("rrr", newProgress + "");
		}
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

	public void goToReturn() {
		iv_return = (Button) findViewById(R.id.iv_return);
		iv_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// // TODO Auto-generated method stub
				// Intent intent2 = null;
				// if (Constants.recommend.equals(tag)){
				// intent2= new Intent(WebViewActivity.this,
				// TabHostActivity.class);
				// } else {
				// intent2= new Intent(WebViewActivity.this,
				// TabHostActivity.class);
				// }
				//
				// startActivity(intent2);
				finish();
			}

		});
	}

}