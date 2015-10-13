package com.example.mall.ui;

import android.view.View;
import android.view.View.OnClickListener;

import com.example.mall.R;
import com.example.mall.base.BaseActivity;
import com.example.mall.view.GuideSliderView;

import java.util.ArrayList;
import java.util.List;

import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.indicator.CircleIndicator;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;

public class GuideActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener {
    private InfiniteIndicatorLayout mGuideIndicatorLayout;

    private List<Integer> resIdList;

    @Override
    public void initView() {
        setContentView(R.layout.activity_guide);
        initUI();
        initTitle();
        resIdList = new ArrayList<Integer>();
        resIdList.add(R.layout.layout_guide_1);
        resIdList.add(R.layout.layout_guide_2);
        resIdList.add(R.layout.layout_guide_3);
        resIdList.add(R.layout.layout_guide_4);

        testCustomCircleIndicator();

    }

    private void initUI(){
        mGuideIndicatorLayout = (InfiniteIndicatorLayout) findViewById(R.id.indicator_guide);
    }

    private void initTitle(){
        hideActionBar();
    }

    private void testCustomCircleIndicator() {
        for (Integer resId : resIdList) {
            GuideSliderView textSliderView = new GuideSliderView(GuideActivity.this, resId, finishListener);
            textSliderView
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
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

    /**
     * 结束Activity监听器
     */
    private OnClickListener finishListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        //finish();
    }
}
