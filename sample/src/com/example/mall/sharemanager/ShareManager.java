package com.example.mall.sharemanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.sharemanager.ShareManager.java
 * @author: liqiongwei
 * @date: 2015-10-09 09:24
 */

public class ShareManager {
    private SharedPreferences share;
    private Editor editor;
    private String TAG = ShareManager.class.getSimpleName();

    private ShareManager() {
        super();
    }

    public void clear() {
        editor.clear().commit();
    }

    ;

    public ShareManager(Context context) {
        super();
        share = context.getSharedPreferences(ShareContents.ShareName, Context.MODE_PRIVATE);
        editor = share.edit();
    }

    /**
     * 获取myName
     *
     * @return
     */
    public String getMyName() {
        String result = share.getString(ShareContents.myName, "");
        return result;
    }

    /**
     * 设置myName
     *
     * @param myName
     */
    public void setMyName(String myName) {
        editor.putString(ShareContents.myName, myName).commit();
    }

    /**
     * 获取isGood
     *
     * @return
     */
    public boolean isCoach() {
        boolean result = share.getBoolean(ShareContents.isCoach, false);
        return result;
    }

    /**
     * 设置isGood
     *
     * @param isCoach
     */
    public void setIsCoach(boolean isCoach) {
        editor.putBoolean(ShareContents.isCoach, isCoach).commit();
    }
}
