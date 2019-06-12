package com.xchallenge.tnt.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

    private static final String SP_NAME = "config";
    private static SharedPreferences sp;

    /**
     * 保存字符串
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putString(key, value).apply();
    }

    /**
     * 加密保存
     *
     * @param context
     * @param key
     * @param value
     */
//    public static void saveStringWithEncode(Context context, String key, String value) {
//        if (sp == null)
//            sp = context.getSharedPreferences(SP_NAME, 0);
//        String s = null;
//        try {
//            s = EncryptUtil.encodeStr(value);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        sp.edit().putString(key, s).apply();
//    }

    /**
     * 解密读取
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
//    public static String getStringWithDecode(Context context, String key, String defValue) {
//        if (sp == null)
//            sp = context.getSharedPreferences(SP_NAME, 0);
//        String s = sp.getString(key, defValue);
//        if (!s.equals(defValue)) {
//            try {
//                s = EncryptUtil.decodeStr(s);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return s;
//    }


    /**
     * 保存布尔
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putBoolean(key, value).apply();
    }

    public static void saveInt(Context context, String key, int value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getInt(key, defValue);
    }

    /**
     * 返回字符串
     *
     * @param context  上下文
     * @param key      key
     * @param defValue 默认值
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getString(key, defValue);
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getBoolean(key, defValue);
    }
}
