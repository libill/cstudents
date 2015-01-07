package com.example.mall;

import android.app.Application;

public class MallApplication extends Application {
	private String TAG = "MallApplication";
	private static MallApplication mallApplication;

	public synchronized static MallApplication getInstance() {
		if (null == mallApplication) {
			mallApplication = new MallApplication();
		}
		return mallApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

}
