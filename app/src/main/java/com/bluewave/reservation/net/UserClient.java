package com.bluewave.reservation.net;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluewave.reservation.model.User;
import com.bluewave.reservation.model.UserPref;
import com.google.gson.Gson;
import com.navercorp.volleyextensions.volleyer.Volleyer;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Developer on 2016-05-12.
 */
public class UserClient extends Client {

    private final static String TAG = "UserClient";
    private final static String URL = "http://hpl.dothome.co.kr/api/user.php";

    private final static String TAG_INSERT_USER = "insert_user";
    private final static String TAG_LOGIN_USER = "login_user";

    public static void insertUser(String id, String password,String name, final Handler handler, final SweetAlertDialog dialog)
    {
        dialog.setTitleText("회원가입중...");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_INSERT_USER)
                .addStringPart("id", id)
                .addStringPart("password", password)
                .addStringPart("name" , name)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_INSERT_USER + " response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error")){
                                handler.onFail();
                            }else{
                                handler.onSuccess(jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            handler.onFail();
                        }
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_INSERT_USER + " error");
                        handler.onFail();
                    }
                })
                .execute();
    }

    public static void login(final String id, final String password, final Handler handler, final SweetAlertDialog dialog)
    {
        dialog.setTitleText("로그인 요청...");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_LOGIN_USER)
                .addStringPart("id", id)
                .addStringPart("password", password)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_LOGIN_USER + " response : " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error")){
                                handler.onFail();
                            }else{
                                UserPref.putId(id);
                                UserPref.putPassword(password);
                                handler.onSuccess(jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            handler.onFail();
                        }
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_LOGIN_USER + " error");
                        handler.onFail();
                    }
                })
                .execute();
    }


}
