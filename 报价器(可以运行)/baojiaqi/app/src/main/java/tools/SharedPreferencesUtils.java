package tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.example.fc_android_bj_new.MainActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by liutao on 2017/9/18.
 */

public class SharedPreferencesUtils {

    public static void putInt(Context context,String key,int content) {
        SharedPreferences sp = context.getSharedPreferences("userInfo.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key,content);
        editor.commit();
    }
    public static int getInt(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences("userInfo.txt", Context.MODE_PRIVATE);
        return sp.getInt(key,MainActivity.defaultTime);
    }


    public static void putBoolean(Context context,String key,boolean content) {
        SharedPreferences sp = context.getSharedPreferences("userInfo.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,content);
        editor.commit();
    }
    public static boolean getBoolean(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences("userInfo.txt", Context.MODE_PRIVATE);
        return sp.getBoolean(key,true);
    }

    public static void putString(Context context,String key,String content) {
        SharedPreferences sp = context.getSharedPreferences("userInfo.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,content);
        editor.commit();
    }
    public static String getString(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences("userInfo.txt", Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }


    public static String getStringBJ(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences("userInfo.txt", Context.MODE_PRIVATE);
        return sp.getString(key,"tupian");
    }


}
