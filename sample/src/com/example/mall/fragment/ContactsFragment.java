package com.example.mall.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mall.R;
import com.example.mall.base.BaseFragment;
import com.example.mall.ui.contacts.ClearEditText;
import com.example.mall.ui.contacts.Pinyin4jUtil;
import com.example.mall.ui.contacts.PinyinComparator;
import com.example.mall.ui.contacts.SideBar;
import com.example.mall.ui.contacts.SortAdapter;
import com.example.mall.ui.contacts.SortModel;
import com.example.mall.ui.contacts.SideBar.OnTouchingLetterChangedListener;
import com.example.mall.ui.main.MainActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 联系人
 * 切换tab:((MainActivity) getActivity()).chooseWhichTabInFront(1);
 */
public class ContactsFragment extends BaseFragment {
    private View mMainView;
    @ViewInject(R.id.sort_listview)
    private ListView sortListView;
    @ViewInject(R.id.sidrbar)
    private SideBar sideBar;
    @ViewInject(R.id.dialog)
    private TextView dialog;
    private SortAdapter sortAdapter;
    @ViewInject(R.id.et_clear)
    private ClearEditText et_clear;

    /**
     * 汉字转换成拼音的类
     */
    private List<SortModel> sortModelList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    public void initView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mMainView = inflater.inflate(R.layout.fragment_contacts, (ViewGroup) getActivity().findViewById(R.id.jazzyPager), false);
        ViewUtils.inject(this, mMainView);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) mMainView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mMainView;
    }

    private void initData() {
        sortModelList = new ArrayList<SortModel>();
        sortAdapter = new SortAdapter(getActivity(), sortModelList);
        sortListView.setAdapter(sortAdapter);
        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = sortAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getActivity(), ((SortModel) sortAdapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //根据输入框输入值的改变来过滤搜索
        et_clear.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 获取联系人异步类
     */
    class GetContactsDataTask extends AsyncTask<Void, Integer, Integer> {
        protected void onPreExecute() {
        }

        @Override
        protected Integer doInBackground(Void... params) {
            sortModelList = filledContactsData(getResources().getStringArray(R.array.contacts_array));
            // 根据a-z进行排序源数据
            Collections.sort(sortModelList, pinyinComparator);
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            sortAdapter.updateListView(sortModelList);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }
    }

    /**
     * 为ListView填充联系人数据
     *
     * @param contactsArray
     * @return
     */
    private List<SortModel> filledContactsData(String[] contactsArray) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < contactsArray.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(contactsArray[i]);
            //汉字转换成拼音
            Map<String, String> mPinMap = Pinyin4jUtil.getNamToPinYin(contactsArray[i]);
            String fullPinYin = mPinMap.get(Pinyin4jUtil.CON_FULL_PIN_YIN);
            String sortString = fullPinYin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = sortModelList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : sortModelList) {
                String name = sortModel.getName();
                Map<String, String> mPinMap = Pinyin4jUtil.getNamToPinYin(name);
                String fullPinYin = mPinMap.get(Pinyin4jUtil.CON_FULL_PIN_YIN);
                if (name.indexOf(filterStr.toString()) != -1 || fullPinYin.startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        sortAdapter.updateListView(filterDateList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetContactsDataTask().execute();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}

