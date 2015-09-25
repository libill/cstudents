package com.example.mall.model;

import com.example.mall.model.base.BaseModel;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.model.BannerModel.java
 * @author: liqiongwei
 * @date: 2015-09-21 09:28
 */
public class BannerModel extends BaseModel {
    private static final String TAG = BannerModel.class.getSimpleName();

    private String URL;

    public BannerModel(String URL){
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
