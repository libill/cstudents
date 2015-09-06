package com.example.mall.model.base;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.model.base.BaseModel.java
 * @author: liqiongwei
 * @date: 2015-09-06 14:35
 */
public class BaseModel implements Serializable {
    private static final String TAG = BaseModel.class.getSimpleName();

    private static final long serialVersionUID = 1L;

    public String pack() {

		/*
         * Field[] declaredFields = this.getClass().getDeclaredFields(); for
		 * (Field field : declaredFields) { //通过反射序列化。或者使用第三方的序列化方法 }
		 */
        return "";
    }

    public void unpack(String str) {
        // 反序列化
    }

    public String encode() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}

