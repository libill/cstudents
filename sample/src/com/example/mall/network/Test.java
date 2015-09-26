package com.example.mall.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.mall.MallApplication;
import com.example.mall.model.TestModel;
import com.example.mall.model.base.BaseModel;
import com.example.mall.util.BeanConverterUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.network.test.java
 * @author: liqiongwei
 * @date: 2015-09-06 11:49
 */
public class Test {
    private static final String TAG = Test.class.getSimpleName();

    public static void getData(final Context mContext, BaseModel respCLass) {
        Map<String, String> appendHeader = BeanConverterUtils.toMap(respCLass);
        String url = "http://www.baidu.com/";
        JsonRequestWithAuth<TestModel> userRequest = new JsonRequestWithAuth<TestModel>(
                url, TestModel.class, new Listener<TestModel>() {
            @Override
            public void onResponse(TestModel response) {
                Log.e("TAG", response.toString());
            }
        }, appendHeader, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,
                        VolleyErrorHelper.getMessage(error, mContext),
                        Toast.LENGTH_LONG).show();
            }
        });
        MallApplication.getInstance().addToRequestQueue(userRequest);
    }

}
