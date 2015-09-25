package com.example.mall.ui.contacts;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashMap;
import java.util.Map;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.sortlistview.Pinyin4jUtil.java
 * @author: liqiongwei
 * @date: 2015-09-21 16:05
 */
public class Pinyin4jUtil {
    private static final String TAG = Pinyin4jUtil.class.getSimpleName();

    /**
     * 全拼
     */
    public final static String CON_FULL_PIN_YIN = "FULL_PIN_YIN";
    /**
     * 每个字符拼音首字母
     */
    public final static String CON_SHORT_PIN_YIN = "SHORT_PIN_YIN";
    /**
     * 全拼第一个字母
     */
    public final static String CON_FIRST_CHAR = "FIRST_CHAR";

    /**
     * 汉字转拼音
     * 需转拼音的汉字(只取字符前四个文字进入拼音转换)
     */
    public static Map<String, String> getNamToPinYin(String name) {
        String fullpin = "";// 全拼
        String shortpin = "";// 每个字符拼音首字母
        String firtChart = "";// 全拼第一个字母
        Map<String, String> mPinMap = new HashMap<String, String>();
        if (!TextUtils.isEmpty(name)) {
            StringBuffer sbFull = new StringBuffer();
            StringBuffer sbShort = new StringBuffer();
            int leg = name.length() > 4 ? 4 : name.length();
            for (int i = 0; i < leg; i++) {
                String partName = getCharacterPinYin(name.charAt(i));
                if (partName != null && !partName.equals("")
                        && partName.length() > 0) {
                    sbFull.append(partName);
                    sbShort.append(partName.charAt(0));
                }
            }
            fullpin = sbFull.toString();
            shortpin = sbShort.toString();
            if (fullpin != null && !fullpin.equals("") && fullpin.length() > 0) {
                firtChart = String.valueOf(fullpin.charAt(0));
            }
        }
        mPinMap.put(CON_FIRST_CHAR, firtChart);
        mPinMap.put(CON_FULL_PIN_YIN, fullpin);
        mPinMap.put(CON_SHORT_PIN_YIN, shortpin);
        return mPinMap;
    }


    /**
     * 汉字转拼音
     *
     * @param c
     * @return
     * @returnType String
     */
    @SuppressLint("DefaultLocale")
    public static String getCharacterPinYin(char c) {
        if ((c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
            return String.valueOf(c).toLowerCase();
        }

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String pinyin[] = null;
        try {
            pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
            // 注意返回的字符串数组，因为可能是多音字
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        // 如果c不是汉字，toHanyuPinyinStringArray会返回null
        if (pinyin == null) {
            return "}";
        } else {
            return pinyin[0];
        }

    }
}
