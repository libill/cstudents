package com.example.mall.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.mall.R;

import cn.lightsky.infiniteindicator.slideview.BaseSliderView;

/**
 * Created by Administrator on 2015/9/4.
 */
public class GuideSliderView extends BaseSliderView {
    private Context mContext;
    private View v;
    private static OnClickListener clickListener;

    public GuideSliderView(Context context, int resId, OnClickListener onClickListener) {
        super(context);
        v = LayoutInflater.from(getContext()).inflate(resId, null);
        this.clickListener = onClickListener;
    }

    @Override
    public View getView() {
        if(null == v){
            v = LayoutInflater.from(getContext()).inflate(R.layout.layout_guide_1, null);
        }
        ImageView target = (ImageView) v.findViewById(R.id.iv_ok);
        ImageView iv_jump = (ImageView) v.findViewById(R.id.iv_jump);
        if(null != iv_jump) {
            iv_jump.setOnClickListener(clickListener);
        }
        bindEventAndShow(v, target);
        return v;
    }
}
