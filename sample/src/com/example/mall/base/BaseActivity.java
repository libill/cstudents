package com.example.mall.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.example.mall.R;

import java.lang.reflect.Field;

/**
 * BaseActivity抽象类
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.lidroid.xutils.sample.base.BaseActivity.java
 * @author: liqiongwei
 * @date: 2015-08-19 17:19
 */
public abstract class BaseActivity extends ActionBarActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    FragmentManager fragmentManager;
    protected Bundle cfgMetaData;
    protected Boolean isHideActionBar;

    protected boolean isShowActionBarBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        setActionBar();
        initView();
    }

    private void setActionBar() {
        if (detectHideActionBar()) {
            return;
        }

        forceShowOverflowMenu();
        // 显示自定义的view
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        // 设置actionbar的logo
        getSupportActionBar().setLogo(new BitmapDrawable(getResources()));
        // 设置返回drawable
        getSupportActionBar().setHomeAsUpIndicator(
                R.drawable.action_bar_back);
        setActionBarBack(true);
        setActionBarBackground();
    }

    private boolean detectHideActionBar() {
        if (isHideActionBar != null) {
            return isHideActionBar;
        }
        try {

            if (cfgMetaData == null) {
                ActivityInfo activityInfo = getPackageManager().getActivityInfo(
                        getIntent().getComponent(), PackageManager.GET_META_DATA);
                cfgMetaData = activityInfo.metaData;
            }

            isHideActionBar = cfgMetaData != null && cfgMetaData.getBoolean("hideActionBar", false);
        } catch (Exception e) {
            //忽略异常
        }

        return isHideActionBar;
    }

    private void forceShowOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }

    protected void setActionBarBack(boolean isShowActionBarBack) {
        this.isShowActionBarBack = isShowActionBarBack;
        // 页面返回设置
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShowActionBarBack);
        // 页面主按钮【如 登录，主页】设置
        getSupportActionBar().setHomeButtonEnabled(isShowActionBarBack);
        // 隐藏home icon
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                if(isShowActionBarBack) {
                    this.finish();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setActionBarBackground(){
        getActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.drawable.bg_header));
    }

    /**
     * 增加Fragment
     *
     * @param container
     * @param f
     * @returnType void
     */
    public void addFragment(int container, Fragment f) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(container, f);
        ft.commit();
    }

    /**
     * 替换显示Fragment
     *
     * @param container
     * @param f
     * @returnType void
     */
    public void setDisplayedFragment(int container, Fragment f) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(container, f);
        ft.commit();
    }

    public FragmentTransaction getFt() {
        return fragmentManager.beginTransaction();
    }

    /**
     * 增加Fragment到返回栈
     *
     * @param container
     * @param f
     * @returnType void
     */
    public void addFragmentToBackStack(int container, Fragment f) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(container, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * 显示Fragment
     *
     * @param f
     * @returnType void
     */
    public void showFragment(Fragment f) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if ((f != null) && f.isHidden())
            ft.show(f);
        ft.commit();
    }

    /**
     * 隐藏Fragment
     *
     * @param f
     * @returnType void
     */
    public void hideFragment(Fragment f) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if ((f != null) && !f.isHidden())
            ft.hide(f);
        ft.commit();
    }

    /**
     * 自动弹出软 键盘
     *
     * @param editText
     * @returnType void
     */
    public void showSoftkeyboard(final EditText editText) {
        editText.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus(editText.getText().length());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }, 100);
    }

    /**
     * 隐藏软键盘
     *
     * @returnType void
     */
    public void hideSoftkeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public abstract void initView();

    public void setActionBarTitle(int id) {
        getSupportActionBar().setTitle(id);
    }

    public void setActionBarTitle(String titleName) {
        getSupportActionBar().setTitle(titleName);
    }

    public void hideActionBar(){
        getSupportActionBar().hide();
    }
}
