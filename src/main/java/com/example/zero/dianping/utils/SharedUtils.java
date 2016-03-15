package com.example.zero.dianping.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zero on 16/3/4.
 * 实现标记的写入和读取
 */
public class SharedUtils {
    private static final String FILE_NAME="dazhongdianping";
    private static final String MODE_NAME="welcome";
    public static boolean getWelcomeBoolean(Context context){
       return context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE).getBoolean(MODE_NAME,false);
    }
    public static void putWelcomeBoolean(Context context,boolean isFirst){
       SharedPreferences.Editor editor= context.getSharedPreferences(FILE_NAME, context.MODE_APPEND).edit();
        editor.putBoolean(MODE_NAME,isFirst);
        editor.commit();
    }
    //保存和读取定位信息
    public static String getCityName(Context context){
        return context.getSharedPreferences(FILE_NAME,context.MODE_PRIVATE).getString("cityName","选择城市");
    }
    public static void putCityName(Context context,String cityName){
        SharedPreferences.Editor editor=context.getSharedPreferences(FILE_NAME, context.MODE_APPEND).edit();
        editor.putString("cityName",cityName);
        editor.commit();
    }
}
