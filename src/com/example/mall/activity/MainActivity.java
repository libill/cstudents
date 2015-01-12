package com.example.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mall.R;

public class MainActivity extends Activity{
	private String TAG="MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.web).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
				intent.putExtra("click_url", "http://www.cnblogs.com/liqw/p/4181327.html");
				startActivity(intent);
			}
		});
	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Log.i(TAG, "finish");
		System.exit(0);
		finish();
	}
}
