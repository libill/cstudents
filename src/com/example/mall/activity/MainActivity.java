package com.example.mall.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.example.mall.R;
import com.example.mall.fragment.Fragment1;
import com.example.mall.fragment.Fragment2;
import com.example.mall.fragment.Fragment3;
import com.example.mall.fragment.Fragment4;
import com.example.mall.view.JazzyViewPager;
import com.example.mall.view.JazzyViewPager.SlideCallback;
import com.example.mall.view.JazzyViewPager.TransitionEffect;
import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends FragmentActivity {
	private JazzyViewPager jazzyPager;
	List<Map<String, View>> tabViews = new ArrayList<Map<String, View>>();
	public TabHost tabHost;

	private Fragment1 mfragment1;
	private Fragment2 mfragment2;
	private Fragment3 mfragment3;
	private Fragment4 mfragment4;
	private ArrayList<Fragment> fragmentList;
	ArrayList<String> titleList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		jazzyPager = (JazzyViewPager) findViewById(R.id.jazzyPager);

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("0").setIndicator(createTab("首页", 0)).setContent(android.R.id.tabcontent));
		tabHost.addTab(tabHost.newTabSpec("1").setIndicator(createTab("统计", 1)).setContent(android.R.id.tabcontent));
		tabHost.addTab(tabHost.newTabSpec("2").setIndicator(createTab("消息", 2)).setContent(android.R.id.tabcontent));
		tabHost.addTab(tabHost.newTabSpec("3").setIndicator(createTab("设置", 3)).setContent(android.R.id.tabcontent));
		// 点击tabHost 来切换不同的消息
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				int index = Integer.parseInt(tabId);
				setTabSelectedState(index, 4);
				tabHost.getTabContentView().setVisibility(View.GONE);// 隐藏content
			}
		});
		tabHost.setCurrentTab(0);

		mfragment1 = new Fragment1();
		mfragment2 = new Fragment2();
		mfragment3 = new Fragment3();
		mfragment4 = new Fragment4();

		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(mfragment1);
		fragmentList.add(mfragment2);
		fragmentList.add(mfragment3);
		fragmentList.add(mfragment4);

		titleList.add("1");
		titleList.add("2");
		titleList.add("3");
		titleList.add("4");

		initJazzyPager(TransitionEffect.Standard);
	}

	/**
	 * 动态创建 TabWidget
	 * 的Tab项,并设置normalLayout的alpha为1，selectedLayout的alpha为0[显示normal，隐藏selected]
	 * 
	 * @param tabLabelText
	 * @param tabIndex
	 * @return
	 */
	private View createTab(String tabLabelText, int tabIndex) {
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.main_tabwidget_layout, null);
		TextView normalTV = (TextView) tabIndicator.findViewById(R.id.normalTV);
		TextView selectedTV = (TextView) tabIndicator.findViewById(R.id.selectedTV);
		normalTV.setText(tabLabelText);
		selectedTV.setText(tabLabelText);
		ImageView normalImg = (ImageView) tabIndicator.findViewById(R.id.normalImg);
		ImageView selectedImg = (ImageView) tabIndicator.findViewById(R.id.selectedImage);
		switch (tabIndex) {
		case 0:
			normalImg.setImageResource(R.drawable.icon_tab_home_default);
			selectedImg.setImageResource(R.drawable.icon_tab_home_pressed);
			break;
		case 1:
			normalImg.setImageResource(R.drawable.scan_qr);
			selectedImg.setImageResource(R.drawable.scan_qr_hl);
			break;
		case 2:
			normalImg.setImageResource(R.drawable.scan_street);
			selectedImg.setImageResource(R.drawable.scan_street_hl);
			break;
		case 3:
			normalImg.setImageResource(R.drawable.scan_word);
			selectedImg.setImageResource(R.drawable.scan_word_hl);
			break;
		}
		View normalLayout = tabIndicator.findViewById(R.id.normalLayout);
		normalLayout.setAlpha(1f);// 透明度显示
		View selectedLayout = tabIndicator.findViewById(R.id.selectedLayout);
		selectedLayout.setAlpha(0f);// 透明的隐藏
		Map<String, View> map = new HashMap<String, View>();
		map.put("normal", normalLayout);
		map.put("selected", selectedLayout);
		tabViews.add(map);
		return tabIndicator;
	}

	/**
	 * 设置tab状态选中
	 * 
	 * @param index
	 */
	private void setTabSelectedState(int index, int tabCount) {
		for (int i = 0; i < tabCount; i++) {
			if (i == index) {
				tabViews.get(i).get("normal").setAlpha(0f);
				tabViews.get(i).get("selected").setAlpha(1f);
			} else {
				tabViews.get(i).get("normal").setAlpha(1f);
				tabViews.get(i).get("selected").setAlpha(0f);
			}
		}
		jazzyPager.setCurrentItem(index, false);// false表示 代码切换 pager
												// 的时候不带scroll动画
	}

	@Override
	protected void onResume() {
		super.onResume();
		setTabSelectedState(tabHost.getCurrentTab(), 4);
	}

	private void initJazzyPager(TransitionEffect effect) {
		tabHost.setCurrentTab(0);
		jazzyPager.setTransitionEffect(effect);
		jazzyPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
		jazzyPager.setPageMargin(0);
		jazzyPager.setFadeEnabled(true);
		jazzyPager.setSlideCallBack(new SlideCallback() {
			@Override
			public void callBack(int position, float positionOffset) {
				// Log.i("", position+"  "+positionOffset);
				Map<String, View> map = tabViews.get(position);
				ViewHelper.setAlpha(map.get("selected"), positionOffset);
				ViewHelper.setAlpha(map.get("normal"), 1 - positionOffset);
			}
		});
		jazzyPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				tabHost.setCurrentTab(position);
			}

			@Override
			public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
			}

			@Override
			public void onPageScrollStateChanged(int paramInt) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Toggle Fade");
		String[] effects = this.getResources().getStringArray(R.array.jazzy_effects);
		for (String effect : effects)
			menu.add(effect);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().toString().equals("Toggle Fade")) {
			jazzyPager.setFadeEnabled(!jazzyPager.getFadeEnabled());
		} else {
			TransitionEffect effect = TransitionEffect.valueOf(item.getTitle().toString());
			initJazzyPager(effect);
		}
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
		
		// 重新加载数据
		initJazzyPager(TransitionEffect.Standard);
	}

	public class MyViewPagerAdapter extends FragmentPagerAdapter {
		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			jazzyPager.setObjectForPosition(fragmentList.get(position), position);
			return super.instantiateItem(container, position);
		}

		@Override
		public Fragment getItem(int position) {
			return fragmentList.get(position);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titleList.get(position);
		}

	}
}
