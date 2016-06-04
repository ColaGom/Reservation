package com.bluewave.reservation.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Developer on 2016-05-12.
 */
public class UserPref {
    private static  final String PREF_NAME = "Reservation";

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static Context context;

    public static void init(Context c){
        context = c;
        preferences = context.getSharedPreferences(PREF_NAME, 0);
        editor = preferences.edit();
    }

    private static final String KEY_ID = "key.id";

    public static void putId(String userId)
    {
        editor.putString(KEY_ID, userId).apply();
    }

    public static String getId()
    {
        return preferences.getString(KEY_ID, "");
    }

    private static final String KEY_PASSWORD = "key.password";

    public static void putPassword(String password)
    {
        editor.putString(KEY_PASSWORD, password).apply();
    }

    public static String getPassword()
    {
        return preferences.getString(KEY_PASSWORD, "");
    }

    private static final String KEY_GCM_TOKEN = "key.gcm.token";

    public static  void putToken(String token)
    {
        editor.putString(KEY_GCM_TOKEN, token).apply();
    }

    public static String getToken()
    {
        return preferences.getString(KEY_GCM_TOKEN, "");
    }

    private  static  final  String KEY_REG_ID = "key.reg.id";

    public static  void putRegId(String id){
        editor.putString(KEY_REG_ID,id).apply();
    }

    public static  String getRegid()
    {
        return preferences.getString(KEY_REG_ID, "");
    }

    private static  final String KEY_APP_VERSION = "key.app.version";

    public static  void putAppVersion(int version)
    {
        editor.putInt(KEY_APP_VERSION, version).apply();
    }

    public static  int getAppVersion()
    {
        return preferences.getInt(KEY_APP_VERSION, 0);
    }

    private static final String KEY_SENT_TOKEN = "key.send.token";

    public static void putSentToken(boolean value)
    {
        editor.putBoolean(KEY_SENT_TOKEN, value).apply();
    }

    public static boolean getSentToken()
    {
        return preferences.getBoolean(KEY_SENT_TOKEN, false);
    }

    private static final String KEY_FAVORITE = "key.favorite";


    public static List<Integer> getStoreFavoriteList()
    {
        String str = preferences.getString(KEY_FAVORITE, "");

        if(TextUtils.isEmpty(str))
        {
            return new ArrayList<>();
        }

        return new Gson().fromJson(str, new TypeToken<List<Integer>>(){}.getType());
    }

    public static void putStoreFavorite(Integer uid)
    {
        List<Integer> list = getStoreFavoriteList();
        list.add(uid);

        editor.putString(KEY_FAVORITE, new Gson().toJson(list,  new TypeToken<List<Integer>>(){}.getType())).apply();
    }

    public static void deleteStoreFavorite(Integer uid)
    {
        List<Integer> list = getStoreFavoriteList();
        list.remove(uid);

        editor.putString(KEY_FAVORITE, new Gson().toJson(list,  new TypeToken<List<Integer>>(){}.getType())).apply();
    }

    public static boolean getStoreFavorite(Integer uid)
    {
        return getStoreFavoriteList().contains(uid);
    }

    public static void switchStoreFavorite(Integer uid)
    {
        boolean favorite = !getStoreFavorite(uid);

        if(favorite)
        {
            putStoreFavorite(uid);
        }
        else
        {
            deleteStoreFavorite(uid);
        }
    }
}
