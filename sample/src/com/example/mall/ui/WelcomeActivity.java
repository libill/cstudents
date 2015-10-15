package com.example.mall.ui;

import android.content.Intent;
import android.os.Handler;

import com.example.mall.R;
import com.example.mall.base.BaseActivity;
import com.example.mall.ui.login.LoginActivity;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.activity.WelcomeActivity.java
 * @author: liqiongwei
 * @date: 2015-08-26 09:23
 */
public class WelcomeActivity extends BaseActivity {
    private static final String TAG = WelcomeActivity.class.getSimpleName();

    private Handler mHandler = new Handler() {
    };


    @Override
    public void initView() {
        setContentView(R.layout.activity_welcome);
        initUI();
        initData();
    }

    private void initUI(){

    }

    private void initData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToMainActivity();
            }
        },500);
    }

    private void goToMainActivity(){
        //Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
