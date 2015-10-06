package com.example.mall.base;

import android.support.v4.app.Fragment;
import android.os.Bundle;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.base.BaseFragment.java
 * @author: liqiongwei
 * @date: 2015-09-28 09:48
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    public abstract void initView();
}
