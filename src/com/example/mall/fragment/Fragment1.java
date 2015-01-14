package com.example.mall.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mall.R;
import com.example.mall.activity.WebViewActivity;
import com.example.mall.view.autoscrollviewpager.AutoScrollViewPager;
import com.example.mall.view.autoscrollviewpager.CirclePageIndicator;

public class Fragment1 extends Fragment {
	private View mMainView;
	private TextView tv;
	private Button btn;
	AutoScrollViewPager viewPager;
	private List<View> viewList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.v("huahua", "fragment1-->onCreate()");

		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment1, (ViewGroup) getActivity().findViewById(R.id.jazzyPager), false);

		tv = (TextView) mMainView.findViewById(R.id.tv1);
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

		LayoutInflater lf = getActivity().getLayoutInflater();
		View view1 = lf.inflate(R.layout.layout_pager_item, null);
		View view2 = lf.inflate(R.layout.layout_pager_item, null);
		viewList = new ArrayList<View>();
		viewList.add(view1);
		viewList.add(view2);
		viewPager = (AutoScrollViewPager) mMainView.findViewById(R.id.view_pager);


		
		viewPager.setInterval(5000);
		viewPager.startAutoScroll();
		viewPager.setCurrentItem(0);
		viewPager.setBorderAnimation(true);
		viewPager.setAdapter(new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
//				container.removeView(viewList.get(position));
			}

			@Override
			public int getItemPosition(Object object) {

				return super.getItemPosition(object);
			}

			@Override
			public int getCount() {
				return 10;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				LayoutInflater lf = getActivity().getLayoutInflater();
				View view1 = lf.inflate(R.layout.layout_pager_item, null);
				
				
				ImageView imageView = (ImageView) view1.findViewById(R.id.imageview);
				if (position == 0) {
					imageView.setBackgroundResource(R.drawable.show);

				}else {
					imageView.setBackgroundResource(R.drawable.scan_book);
					
				}
				container.addView(view1);
				return view1;
			}
		});
		
		CirclePageIndicator mIndicator = (CirclePageIndicator) mMainView.findViewById(R.id.indicator);
        mIndicator.setViewPager(viewPager);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.v("huahua", "fragment1-->onCreateView()");

		ViewGroup p = (ViewGroup) mMainView.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
			Log.v("huahua", "fragment1-->�Ƴ��Ѵ��ڵ�View");
		}

		return mMainView;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("huahua", "fragment1-->onDestroy()");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("huahua", "fragment1-->onPause()");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("huahua", "fragment1-->onResume()");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("huahua", "fragment1-->onStart()");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("huahua", "fragment1-->onStop()");
	}

}
