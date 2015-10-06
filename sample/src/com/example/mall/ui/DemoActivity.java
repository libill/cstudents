package com.example.mall.ui;

import android.view.View;

import com.example.mall.R;
import com.example.mall.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.ui.DemoActivity.java
 * @author: liqiongwei
 * @date: 2015-09-29 16:03
 */
public class DemoActivity extends BaseActivity{
    private static final String TAG = DemoActivity.class.getSimpleName();
    @Override
    public void initView() {
        //setContentView(R.layout.activity_demo);
        ViewUtils.inject(this);
        initTitle();
        initUI();
        initData();
    }

    private void initTitle(){

    }

    private void initUI() {

    }

    private void initData() {

    }

    @OnClick({R.id.bt_login, R.id.bt_register})
    public void clickMethod(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                break;
            case R.id.bt_register:
                break;
        }
    }
}
