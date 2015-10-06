package com.example.mall.activity;

import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.support.v7.app.ActionBar.LayoutParams;

import com.example.mall.R;
import com.example.mall.base.BaseActivity;
import com.example.mall.base.BaseFragment;
import com.example.mall.fragment.Fragment1;
import com.example.mall.fragment.Fragment2;
import com.example.mall.fragment.ContactsFragment;
import com.example.mall.fragment.Fragment4;
import com.example.mall.model.TestModel;
import com.example.mall.network.Test;
import com.example.mall.view.JazzyViewPager;
import com.example.mall.view.JazzyViewPager.SlideCallback;
import com.example.mall.view.JazzyViewPager.TransitionEffect;
import com.lidroid.xutils.ViewUtils;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {
	private static String[] tabName = new String[]{"首页","统计","联系人","我的"};
	private JazzyViewPager jazzyPager;
	List<Map<String, View>> tabViews = new ArrayList<Map<String, View>>();
	public TabHost tabHost;

	private Fragment1 mfragment1;
	private Fragment2 mfragment2;
	private ContactsFragment mfragmentContacts;
	private Fragment4 mfragment4;
	private ArrayList<BaseFragment> fragmentList;
	ArrayList<String> titleList = new ArrayList<String>();

	private TextView tv_bar_title;
	private ImageView iv_bar_logo;

	@Override
	public void initView() {
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);
		initUI();
		initData();

		TestModel m = new TestModel();
		m.setTitle("sdf");
		Test.getData(this, m);
	}

	private void initUI(){
		jazzyPager = (JazzyViewPager) findViewById(R.id.jazzyPager);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		initCustomActionBar();
	}

	private void initCustomActionBar() {
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setHomeAsUpIndicator(
				new BitmapDrawable(getResources()));
		View view = getLayoutInflater().inflate(
				R.layout.layout_custom_actionbar, null);
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT, Gravity.LEFT);
		tv_bar_title = (TextView) view.findViewById(R.id.tv_bar_title);
		iv_bar_logo = (ImageView) view.findViewById(R.id.iv_bar_logo);
		getSupportActionBar().setCustomView(view, lp);
	}

	private void setCustomActionBarTitle(int position){
		iv_bar_logo.setBackgroundResource(R.drawable.ic_launcher);
		tv_bar_title.setText(tabName[position]);
	}

	private void initData(){
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("0").setIndicator(createTab(0)).setContent(android.R.id.tabcontent));
		tabHost.addTab(tabHost.newTabSpec("1").setIndicator(createTab(1)).setContent(android.R.id.tabcontent));
		tabHost.addTab(tabHost.newTabSpec("2").setIndicator(createTab(2)).setContent(android.R.id.tabcontent));
		tabHost.addTab(tabHost.newTabSpec("3").setIndicator(createTab(3)).setContent(android.R.id.tabcontent));
		// 点击tabHost 来切换不同的消息
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				int index = Integer.parseInt(tabId);
				setTabSelectedState(index, tabName.length);
				tabHost.getTabContentView().setVisibility(View.GONE);// 隐藏content
			}
		});
		tabHost.setCurrentTab(0);

		mfragment1 = new Fragment1();
		mfragment2 = new Fragment2();
		mfragmentContacts = new ContactsFragment();
		mfragment4 = new Fragment4();

		fragmentList = new ArrayList<BaseFragment>();
		fragmentList.add(mfragment1);
		fragmentList.add(mfragment2);
		fragmentList.add(mfragmentContacts);
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
	 * @param tabIndex
	 * @return
	 */
	private View createTab(int tabIndex) {
		String tabLabelText = tabName[tabIndex];
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
				setCustomActionBarTitle(index);
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
		setTabSelectedState(tabHost.getCurrentTab(), tabName.length);
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

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		menu.add("Toggle Fade");
//		String[] effects = this.getResources().getStringArray(R.array.jazzy_effects);
//		for (String effect : effects)
//			menu.add(effect);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		if (item.getTitle().toString().equals("Toggle Fade")) {
//			jazzyPager.setFadeEnabled(!jazzyPager.getFadeEnabled());
//		} else {
//			TransitionEffect effect = TransitionEffect.valueOf(item.getTitle().toString());
//			initJazzyPager(effect);
//		}
//		return true;
//	}

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
