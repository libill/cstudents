package com.example.mall.ui.login;

import android.content.Intent;
import android.view.View;

import com.example.mall.R;
import com.example.mall.base.BaseActivity;
import com.example.mall.view.EditTextAutoView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by Administrator on 2015/10/6.
 */
public class ForgetPasswordUI  extends BaseActivity {
    private static final String TAG = ForgetPasswordUI.class.getSimpleName();

    @ViewInject(R.id.et_auto_view_phone)
    private EditTextAutoView et_auto_view_phone;
    @Override
    public void initView() {
        setContentView(R.layout.ui_forget_password);
        ViewUtils.inject(this);
        initTitle();
        initUI();
        initData();
    }

    private void initTitle(){
        setActionBarTitle(R.string.forget_password);
    }

    private void initUI() {

    }

    private void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        et_auto_view_phone.requestFocus();
        showSoftkeyboard(et_auto_view_phone.getEditText());
    }

    @OnClick({R.id.bt_next_step})
    public void clickMethod(View v) {
        switch (v.getId()) {
            case R.id.bt_next_step:
                startActivity(new Intent(this, SMSVerificationUI.class));
                break;
        }
    }
}

