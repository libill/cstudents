package com.example.mall.ui.login;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.example.mall.R;
import com.example.mall.activity.WebViewActivity;
import com.example.mall.base.BaseActivity;
import com.example.mall.view.EditTextAutoView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.ui.login.RegisterActivity.java
 * @author: liqiongwei
 * @date: 2015-09-29 16:01
 */
public class RegisterActivity extends BaseActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    @ViewInject(R.id.et_auto_view_account)
    private EditTextAutoView et_auto_view_account;
    @ViewInject(R.id.bt_count_down)
    private Button bt_count_down;

    @Override
    public void initView() {
        setContentView(R.layout.activity_register);
        ViewUtils.inject(this);
        initTitle();
        initUI();
        initData();
    }

    private void initTitle() {
        setActionBarTitle(R.string.register);
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

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            bt_count_down.setText("重新发送("+millisUntilFinished / 1000+")");
        }

        @Override
        public void onFinish() {
            bt_count_down.setEnabled(true);
            bt_count_down.setText(getString(R.string.get_security_code));
        }
    };

    @OnClick({R.id.tv_user_guide, R.id.bt_register, R.id.bt_count_down})
    public void clickMethod(View v) {
        switch (v.getId()) {
            case R.id.tv_user_guide:
                Intent intent = new Intent(RegisterActivity.this, WebViewActivity.class);
                intent.putExtra("click_url", "file:///android_asset/html/protocol/index.html");
                startActivity(intent);
                break;
            case R.id.bt_register:
                startActivity(new Intent(this, SelectRoleUI.class));
                break;
            case R.id.bt_count_down:
                timer.start();
                break;
        }
    }
}
