package com.example.mall.activity;

import android.widget.Toast;

import com.example.mall.R;
import com.example.mall.base.BaseActivity;

import java.util.HashMap;

import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.indicator.CircleIndicator;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

public class GuideActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener {
    private InfiniteIndicatorLayout mGuideIndicatorLayout;

    private HashMap<String, String> url_maps;

    @Override
    public void initView() {
        setContentView(R.layout.activity_guide);
        initUI();
        initTitle();
        url_maps = new HashMap<String, String>();
        url_maps.put("Page A", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/a.jpg");
        url_maps.put("Page B", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/b.jpg");
        url_maps.put("Page C", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/c.jpg");
        url_maps.put("Page D", "https://raw.githubusercontent.com/lightSky/InfiniteIndicator/master/res/d.jpg");

        testCustomCircleIndicator();
    }

    private void initUI(){
        mGuideIndicatorLayout = (InfiniteIndicatorLayout) findViewById(R.id.indicator_guide);
    }

    private void initTitle(){
        hideActionBar();
    }

    private void testCustomCircleIndicator() {
        for (String name : url_maps.keySet()) {
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .showImageResForError(R.drawable.ic_launcher)
                    .setOnSliderClickListener(this);
            textSliderView.getBundle()
                    .putString("extra", name);
            mGuideIndicatorLayout.addSlider(textSliderView);
        }
        mGuideIndicatorLayout.setInfinite(false);
        mGuideIndicatorLayout.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
        CircleIndicator circleIndicator = ((CircleIndicator) mGuideIndicatorLayout.getPagerIndicator());
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
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
