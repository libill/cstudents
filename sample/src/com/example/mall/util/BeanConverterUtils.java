package com.example.mall.util;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 转换器
 * 1:将JavaBean 转换成Map、JSONObject
 * 2:将JSONObject 转换成Map
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.util.BeanConverterUtils.java
 * @author: liqiongwei
 * @date: 2015-09-06 16:33
 */
public class BeanConverterUtils {
    private static final String TAG = BeanConverterUtils.class.getSimpleName();

    /**
     * 将javaBean转换成Map
     *
     * @param javaBean javaBean
     * @return Map对象
     */
    public static Map<String, String> toMap(Object javaBean) {
        Map<String, String> result = new HashMap<String, String>();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[]) null);
                    result.put(field, null == value ? "" : value.toString());
                }
            } catch (Exception e) {
            }
        }

        return result;
    }

    /**
     * 将json对象转换成Map
     *
     * @param jsonObject json对象
     * @return Map对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> toMap(JSONObject jsonObject) {
        Map<String, String> result = new HashMap<String, String>();
        Iterator<String> iterator = jsonObject.keys();
        String key = null;
        String value = null;
        try {
            while (iterator.hasNext()) {
                key = iterator.next();
                value = jsonObject.getString(key);
                result.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将javaBean转换成JSONObject
     *
     * @param bean javaBean
     * @return json对象
     */
    public static JSONObject toJSON(Object bean) {
        return new JSONObject(toMap(bean));
    }

    /**
     * 将map转换成Javabean
     *
     * @param javabean javaBean
     * @param data     map数据
     */
    public static Object toJavaBean(Object javabean, Map<String, String> data) {
        Method[] methods = javabean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("set")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    method.invoke(javabean, new Object[]
                            {
                                    data.get(field)
                            });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return javabean;
    }

    /**
     * 将javaBean转换成JSONObject
     *
     * @param javabean
     * @return json对象
     * @throws ParseException json解析异常
     */
    public static void toJavaBean(Object javabean, String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        Map<String, String> datas = toMap(jsonObject);
        toJavaBean(javabean, datas);
    }
}