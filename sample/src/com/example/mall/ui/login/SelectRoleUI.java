package com.example.mall.ui.login;

import android.content.Intent;
import android.view.View;

import com.example.mall.R;
import com.example.mall.base.BaseActivity;
import com.example.mall.sharemanager.ShareManager;
import com.example.mall.ui.main.MainActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by Administrator on 2015/10/6.
 */
public class SelectRoleUI extends BaseActivity {
    private static final String TAG = SelectRoleUI.class.getSimpleName();

    private ShareManager shareManager;
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
        shareManager = new ShareManager(this);
    }

    private void goToMainUI(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);//| Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.bt_coach, R.id.bt_trainee})
    public void clickMethod(View v) {
        switch (v.getId()) {
            case R.id.bt_coach:
                shareManager.setIsCoach(true);
                goToMainUI();
                break;
            case R.id.bt_trainee:
                shareManager.setIsCoach(false);
                goToMainUI();
                break;
        }
    }
}
