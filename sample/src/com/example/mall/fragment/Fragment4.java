package com.example.mall.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mall.R;
import com.example.mall.base.BaseFragment;
import com.example.mall.ui.my.MyDataUI;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class Fragment4 extends BaseFragment {
    private View mMainView;

    @ViewInject(R.id.tv_name)
    private TextView tv_name;

    @ViewInject(R.id.sdv_my_avatar)
    private SimpleDraweeView sdv_my_avatar;

    @Override
    public void initView() {
        initUI();
        initData();
    }

    private void initUI() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mMainView = inflater.inflate(R.layout.fragment_mine, (ViewGroup) getActivity().findViewById(R.id.jazzyPager), false);
        ViewUtils.inject(this, mMainView);
    }

    private void initData(){
        sdv_my_avatar.setImageURI(Uri.parse("http://www.hinews.cn/pic/0/13/10/65/13106543_789011.jpg"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) mMainView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }

        return mMainView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @OnClick({R.id.tv_name})
    public void clickMethod(View v) {
        switch (v.getId()) {
            case R.id.tv_name:
                startActivity(new Intent(getActivity(), MyDataUI.class));
                break;
        }
    }
}

