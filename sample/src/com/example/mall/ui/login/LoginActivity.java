package com.example.mall.ui.login;

import com.example.mall.R;
import com.example.mall.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.ui.login.LoginActivity.java
 * @author: liqiongwei
 * @date: 2015-09-28 10:48
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);

        setActionBarTitle(R.string.login);
        setActionBarBack(false);
        initUI();
        initData();
    }

    private void initUI(){

    }

    private void initData(){

    }
}
