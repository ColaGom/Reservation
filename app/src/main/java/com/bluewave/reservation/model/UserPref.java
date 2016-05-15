package com.bluewave.reservation.model;

import android.content.Context;
import android.content.SharedPreferences;

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
        editor.putString(KEY_ID, userId);
        editor.commit();
    }

    public static String getId()
    {
        return preferences.getString(KEY_ID, "");
    }

    private static final String KEY_PASSWORD = "key.password";

    public static void putPassword(String password)
    {
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public static String getPassword()
    {
        return preferences.getString(KEY_PASSWORD, "");
    }

    private static final String KEY_GCM_TOKEN = "key.gcm.token";

    public static  void putToken(String token)
    {
        editor.putString(KEY_GCM_TOKEN, token);
        editor.commit();
    }

    public static String getToken()
    {
        return preferences.getString(KEY_GCM_TOKEN, "");
    }

    private  static  final  String KEY_REG_ID = "key.reg.id";

    public static  void putRegId(String id){
        editor.putString(KEY_REG_ID,id);
        editor.commit();
    }

    public static  String getRegid()
    {
        return preferences.getString(KEY_REG_ID, "");
    }

    private static  final String KEY_APP_VERSION = "key.app.version";

    public static  void putAppVersion(int version)
    {
        editor.putInt(KEY_APP_VERSION, version);
        editor.commit();
    }

    public static  int getAppVersion()
    {
        return preferences.getInt(KEY_APP_VERSION, 0);
    }
}
