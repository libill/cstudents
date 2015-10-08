package com.example.mall.ui.login;

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
public class SMSVerificationUI extends BaseActivity {
    private static final String TAG = SMSVerificationUI.class.getSimpleName();

    @ViewInject(R.id.et_auto_view_code)
    private EditTextAutoView et_auto_view_code;
    @ViewInject(R.id.et_auto_view_password)
    private EditTextAutoView et_auto_view_password;
    @ViewInject(R.id.et_auto_view_new_password)
    private EditTextAutoView et_auto_view_new_password;

    @Override
    public void initView() {
        setContentView(R.layout.ui_sms_verification);
        ViewUtils.inject(this);
        initTitle();
        initUI();
        initData();
    }

    private void initTitle(){
        setActionBarTitle(R.string.sms_verification);
    }

    private void initUI() {

    }

    private void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        et_auto_view_code.requestFocus();
        showSoftkeyboard(et_auto_view_code.getEditText());
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideSoftkeyboard();
    }

    @OnClick({R.id.bt_get_again, R.id.bt_ok})
    public void clickMethod(View v) {
        switch (v.getId()) {
            case R.id.bt_get_again:
                break;
            case R.id.bt_ok:
                break;
        }
    }
}

