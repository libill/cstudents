package com.example.mall.ui.login;

import android.content.Intent;
import android.view.View;

import com.example.mall.R;
import com.example.mall.ui.main.MainActivity;
import com.example.mall.base.BaseActivity;
import com.example.mall.view.EditTextAutoView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

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

    @ViewInject(R.id.et_auto_view_account)
    private EditTextAutoView et_auto_view_account;

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);

        setActionBarTitle(R.string.login);
        setActionBarBack(false);
        initUI();
        initData();
    }

    private void initUI() {

    }

    private void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        et_auto_view_account.requestFocus();
        showSoftkeyboard(et_auto_view_account.getEditText());
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoftkeyboard();
    }

    @OnClick({R.id.tv_forget_password, R.id.bt_login, R.id.bt_register})
    public void clickMethod(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.bt_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;
            case R.id.tv_forget_password:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordUI.class));
                break;
        }
    }
}
