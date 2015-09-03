package com.example.mall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mall.R;
import com.example.mall.activity.MainActivity;
import com.example.mall.activity.WebViewActivity;

import java.util.HashMap;

import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.indicator.CircleIndicator;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;


public class Fragment1 extends Fragment implements BaseSliderView.OnSliderClickListener {
    private View mMainView;
    private TextView tv;
    private Button btn;
    private InfiniteIndicatorLayout mCustoemIndicatorLayout;
    private HashMap<String, String> url_maps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mMainView = inflater.inflate(R.layout.fragment1, (ViewGroup) getActivity().findViewById(R.id.jazzyPager), false);

        btn = (Button) mMainView.findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tv.setText("Hello Viewpager\"");
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("click_url", "http://www.cnblogs.com/liqw/p/4181327.html");
                startActivity(intent);
            }
        });

        url_maps = new HashMap<String, String>();
        url_maps.put("Page A", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg");
        url_maps.put("Page B", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg");
        url_maps.put("Page C", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg");
        url_maps.put("Page D", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg");

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
        mCustoemIndicatorLayout.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCustoemIndicatorLayout.startAutoScroll();
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
        mCustoemIndicatorLayout = (InfiniteIndicatorLayout) mMainView.findViewById(R.id.indicator_custome_circle);
        for (String name : url_maps.keySet()) {
            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
            textSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .showImageResForError(R.drawable.ic_launcher)
                    .setOnSliderClickListener(this);
            textSliderView.getBundle()
                    .putString("extra", name);
            mCustoemIndicatorLayout.addSlider(textSliderView);
        }
        mCustoemIndicatorLayout.setInfinite(true);
        mCustoemIndicatorLayout.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
        CircleIndicator circleIndicator = ((CircleIndicator) mCustoemIndicatorLayout.getPagerIndicator());
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
