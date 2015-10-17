package com.example.mall.ui.main;

import android.os.Bundle;
import android.view.View;

import com.example.mall.R;
import com.example.mall.base.BaseActivity;
import com.wefika.flowlayout.FlowLayout;

/**
 * Created by Administrator on 2015/10/17.
 */
public class VisibilityActivity extends BaseActivity {

    private FlowLayout mFlowLayout;

    @Override
    public void initView() {
        setContentView(R.layout.activity_visibility);

        mFlowLayout = (FlowLayout) findViewById(R.id.flow);
    }

    public void addItem(View view) {

        int color = getResources().getColor(R.color.black);

        View newView = new View(this);
        newView.setBackgroundColor(color);

        FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(100, 100);
        params.rightMargin = 10;
        newView.setLayoutParams(params);

        mFlowLayout.addView(newView);
    }

    public void removeItem(View view) {

        mFlowLayout.removeView(getLastView());

    }

    public void toggleItem(View view) {

        View last = getLastView();

        if(last.getVisibility() == View.VISIBLE) {
            last.setVisibility(View.GONE);
        } else {
            last.setVisibility(View.VISIBLE);
        }

    }

    private View getLastView() {
        return mFlowLayout.getChildAt(mFlowLayout.getChildCount() - 1);
    }

}
