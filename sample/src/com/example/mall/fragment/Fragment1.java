package com.example.mall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mall.R;
import com.example.mall.ui.WebViewActivity;
import com.example.mall.base.BaseFragment;
import com.example.mall.model.BannerModel;
import com.example.mall.ui.main.VisibilityActivity;

import java.util.ArrayList;

import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.indicator.CircleIndicator;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;


public class Fragment1 extends BaseFragment implements BaseSliderView.OnSliderClickListener {
    private View mMainView;
    private Button btn;
    private InfiniteIndicatorLayout mCustomIndicatorLayout;
    private ArrayList<BannerModel> url_maps;

    @Override
    public void initView() {

        initUI();
        initData();
    }

    private void initUI(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mMainView = inflater.inflate(R.layout.fragment1, (ViewGroup) getActivity().findViewById(R.id.jazzyPager), false);

        btn = (Button) mMainView.findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("click_url", "http://www.cnblogs.com/liqw/p/4181327.html");
                startActivity(intent);

                startActivity(new Intent(getActivity(), VisibilityActivity.class));
            }
        });
    }

    private void initData(){
        url_maps = new ArrayList<BannerModel>();
        BannerModel bannerModel = new BannerModel("https://www.baidu.com/img/bdlogo.png");
        url_maps.add(bannerModel);
        bannerModel = new BannerModel("http://p1.qhimg.com/t0151320b1d0fc50be8.png");
        url_maps.add(bannerModel);
        bannerModel = new BannerModel("https://www.sogou.com/images/logo2014/error180x60.png");
        url_maps.add(bannerModel);

        testCustomCircleIndicator();
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
        mCustomIndicatorLayout.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCustomIndicatorLayout.startAutoScroll();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void testCustomCircleIndicator() {
        mCustomIndicatorLayout = (InfiniteIndicatorLayout) mMainView.findViewById(R.id.indicator_custome_circle);
        for (BannerModel model : url_maps) {
            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
            textSliderView
                    .image(model.getURL())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .showImageResForError(R.drawable.ic_launcher)
                    .setOnSliderClickListener(this);
            textSliderView.getBundle()
                    .putString("extra", model.getURL());
            mCustomIndicatorLayout.addSlider(textSliderView);
        }
        mCustomIndicatorLayout.setInfinite(true);
        mCustomIndicatorLayout.setInterval(1000 * 5);// 时间间隙
        mCustomIndicatorLayout.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
        CircleIndicator circleIndicator = ((CircleIndicator) mCustomIndicatorLayout.getPagerIndicator());
        final float density = getResources().getDisplayMetrics().density;
        circleIndicator.setBackgroundColor(getResources().getColor(R.color.transparent));
        circleIndicator.setRadius(5 * density);
        circleIndicator.setPageColor(0x880000FF);
        circleIndicator.setFillColor(0xFF888888);
        circleIndicator.setStrokeColor(0xFF000000);
        circleIndicator.setStrokeWidth(2 * density);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }
}
