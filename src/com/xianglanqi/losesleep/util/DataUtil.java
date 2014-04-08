package com.xianglanqi.losesleep.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.xianglanqi.losesleep.model.User;

public class DataUtil {

    private static final String KEY_DATA = "key_data";

    public static final void saveUser(Context context, User user) {
        Log.d("hy", "saveUser\n" + user.toString());
        Editor editor = context.getSharedPreferences(KEY_DATA, Context.MODE_PRIVATE).edit();
        editor.putLong("id", user.getId());
        editor.putString("name", user.getName());
        editor.putBoolean("gender", user.getGender());
        editor.putString("avatar", user.getAvatar());
        editor.putString("deviceId", user.getDeviceId());
        editor.commit();
    }

    public static final User loadUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences(KEY_DATA, Context.MODE_PRIVATE);
        if (!sp.contains("id")) {
            return null;
        }
        User user = new User();
        user.setId(sp.getLong("id", 0));
        user.setName(sp.getString("name", ""));
        user.setGender(sp.getBoolean("gender", true));
        user.setAvatar(sp.getString("avatar", ""));
        user.setDeviceId(sp.getString("deviceId", ""));
        Log.d("hy", "loadUser\n" + user.toString());
        return user;
    }
}
