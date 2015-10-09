package com.example.mall.ui.my;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.OptionsPopupWindow;
import com.bigkoo.pickerview.TimePopupWindow;
import com.bigkoo.pickerview.TimePopupWindow.Type;
import com.bigkoo.pickerview.TimePopupWindow.OnTimeSelectListener;
import com.example.mall.R;
import com.example.mall.base.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.ui.my.MyInfomationUI.java
 * @author: liqiongwei
 * @date: 2015-10-08 09:41
 */
public class MyDataUI extends BaseActivity {
    private static final String TAG = MyDataUI.class.getSimpleName();

    private ArrayList<String> options1Items = new ArrayList<String>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    @ViewInject(R.id.tvTime)
    private TextView tvTime;
    @ViewInject(R.id.tvOptions)
    private TextView tvOptions;
    TimePopupWindow pwTime;
    OptionsPopupWindow pwOptions;

    @Override
    public void initView() {
        setContentView(R.layout.ui_my_data);
        ViewUtils.inject(this);
        initTitle();
        initUI();
        initData();
    }

    private void initTitle(){
        setActionBarTitle(R.string.my_data);
    }

    private void initUI() {
        //时间选择器
        pwTime = new TimePopupWindow(this, Type.ALL);
        //时间选择后回调
        pwTime.setOnTimeSelectListener(new OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tvTime.setText(getTime(date));
            }
        });
        new AlertView("上传头像", null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                this, AlertView.Style.ActionSheet, new OnItemClickListener(){
            public void onItemClick(Object o,int position){
                Toast.makeText(MyDataUI.this, "点击了第" + position + "个",
                        Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    private void initData() {


        //选项选择器
        pwOptions = new OptionsPopupWindow(this);

        //选项1
        options1Items.add("广东");
        options1Items.add("湖南");

        //选项2
        ArrayList<String> options2Items_01=new ArrayList<String>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        ArrayList<String> options2Items_02=new ArrayList<String>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);

        //选项3
        ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> options3Items_02 = new ArrayList<ArrayList<String>>();
        ArrayList<String> options3Items_01_01=new ArrayList<String>();
        options3Items_01_01.add("白云");
        options3Items_01_01.add("天河");
        options3Items_01_01.add("海珠");
        options3Items_01_01.add("越秀");
        options3Items_01.add(options3Items_01_01);
        ArrayList<String> options3Items_01_02=new ArrayList<String>();
        options3Items_01_02.add("南海");
        options3Items_01.add(options3Items_01_02);
        ArrayList<String> options3Items_01_03=new ArrayList<String>();
        options3Items_01_03.add("常平");
        options3Items_01_03.add("虎门");
        options3Items_01.add(options3Items_01_03);

        ArrayList<String> options3Items_02_01=new ArrayList<String>();
        options3Items_02_01.add("长沙1");
        options3Items_02.add(options3Items_02_01);
        ArrayList<String> options3Items_02_02=new ArrayList<String>();
        options3Items_02_02.add("岳1");
        options3Items_02.add(options3Items_02_02);

        options3Items.add(options3Items_01);
        options3Items.add(options3Items_02);

        //三级联动效果
        pwOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
        pwOptions.setLabels("省", "市", "区");
        //设置默认选中的三级项目
        pwOptions.setSelectOptions(0, 0, 0);
        //监听确定选择按钮
        pwOptions.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1)
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
                tvOptions.setText(tx);
            }
        });

    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    @OnClick({R.id.tvTime, R.id.tvOptions})
    public void clickMethod(View v) {
        switch (v.getId()) {
            case R.id.tvTime:
                //弹出时间选择器
                pwTime.showAtLocation(tvTime, Gravity.BOTTOM, 0, 0, new Date());
                break;
            case R.id.tvOptions:
                //点击弹出选项选择器
                pwOptions.showAtLocation(tvTime, Gravity.BOTTOM, 0, 0);
                break;
        }
    }
}
