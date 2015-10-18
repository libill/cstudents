package com.example.mall.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


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
    private TextView tv_bar_title;
    private ImageView iv_bar_logo;
    private TextView tv_bar_more;

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
        initCustomActionBar();
        setActionBarBackground();
    }

    private void initCustomActionBar() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(
                new BitmapDrawable(getResources()));
        View view = getLayoutInflater().inflate(
                R.layout.layout_custom_actionbar, null);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.LEFT);
        tv_bar_title = (TextView) view.findViewById(R.id.tv_bar_title);
        iv_bar_logo = (ImageView) view.findViewById(R.id.iv_bar_logo);
        tv_bar_more = (TextView) view.findViewById(R.id.tv_bar_more);
        getSupportActionBar().setCustomView(view, lp);
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

    protected void setActionBarBack(boolean isShowActionBarBack, OnClickListener l) {
        this.isShowActionBarBack = isShowActionBarBack;
        if(isShowActionBarBack){
            if(null != l){
                iv_bar_logo.setOnClickListener(l);
            } else {
                iv_bar_logo.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }
        iv_bar_logo.setVisibility(isShowActionBarBack?View.VISIBLE:View.GONE);
        tv_bar_more.setVisibility(View.INVISIBLE);
    }

    protected void setActionBarBackAndMore(boolean isShowActionBarBack, OnClickListener clickListener) {
        this.isShowActionBarBack = isShowActionBarBack;
        iv_bar_logo.setVisibility(View.VISIBLE);
        tv_bar_more.setVisibility(View.VISIBLE);
        tv_bar_more.setOnClickListener(clickListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                if (isShowActionBarBack) {
                    this.finish();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setActionBarBackground() {
        getActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.drawable.bg_header));
    }

    public void showRightTwoButtons(int leftId, View.OnClickListener lefeListener,
                                    int rightId, View.OnClickListener RightListener) {
        View view = getLayoutInflater().inflate(R.layout.layout_actionbar_two_button, null);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.RIGHT);
        getSupportActionBar().setCustomView(view, lp);
        TextView leftTv = (TextView) view.findViewById(R.id.bt_left);
        leftTv.setVisibility(View.VISIBLE);
        TextView rightTv = (TextView) view.findViewById(R.id.bt_right);
        leftTv.setText(leftId);
        leftTv.setOnClickListener(lefeListener);
        rightTv.setText(rightId);
        rightTv.setOnClickListener(RightListener);
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
        tv_bar_title.setText(id);
    }

    public void setActionBarTitle(String titleName) {
        tv_bar_title.setText(titleName);
    }

    public void hideActionBar() {
        getSupportActionBar().hide();
    }
}
