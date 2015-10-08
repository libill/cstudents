package com.example.mall.ui.login;

import android.view.View;

import com.example.mall.R;
import com.example.mall.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by Administrator on 2015/10/6.
 */
public class SelectRoleUI extends BaseActivity {
    private static final String TAG = SelectRoleUI.class.getSimpleName();
    @Override
    public void initView() {
        setContentView(R.layout.ui_select_role);
        ViewUtils.inject(this);
        initTitle();
        initUI();
        initData();
    }

    private void initTitle(){
        setActionBarTitle(R.string.select_role);
    }

    private void initUI() {

    }

    private void initData() {

    }

    @OnClick({R.id.bt_coach, R.id.bt_trainee})
    public void clickMethod(View v) {
        switch (v.getId()) {
            case R.id.bt_coach:
                break;
            case R.id.bt_trainee:
                break;
        }
    }
}
