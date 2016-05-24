package com.bluewave.reservation.net;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluewave.reservation.model.GameUser;
import com.bluewave.reservation.model.ReservationInfo;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.model.WaitingInfo;
import com.google.gson.Gson;
import com.navercorp.volleyextensions.volleyer.Volleyer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Developer on 2016-05-14.
 */
public class StoreClient extends Client {

    private final static String TAG = "StoreClient";
    private final static String URL = "http://hpl.dothome.co.kr/api/store.php";

    private final static String TAG_GET_STORE_LIST = "get_store_list";
    private final static String TAG_CHECK_WAITING = "check_waiting";
    private final static String TAG_INSERT_WAITING = "insert_waiting";
    private final static String TAG_DELETE_WAITING = "delete_waiting";
    private final static String TAG_MODIFY_WAITING = "modify_waiting";
    private final static String TAG_GET_RESERVATION_INFO = "get_reservation_info";
    private final static String TAG_GET_WAITING_LIST = "get_waiting_list";
    private final static String TAG_JOIN_GAME = "join_game";
    private final static String TAG_EXIT_GAME = "exit_game";
    private final static String TAG_GET_JOIN_USER_LIST = "get_join_user";

    public static void deleteWaiting(String user_id, String store_id, final Handler handler,final SweetAlertDialog dialog)
    {
        dialog.setTitleText("예약 취소중..");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_DELETE_WAITING)
                .addStringPart("user_id", user_id)
                .addStringPart("store_id", store_id)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_DELETE_WAITING + " response : " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error"))
                            {
                                handler.onFail();
                            }
                            else
                            {
                                handler.onSuccess(jsonObject);
                            }
                        } catch (JSONException e) {
                            handler.onFail();
                            e.printStackTrace();
                        }
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        handler.onFail();
                        Log.d(TAG, TAG_DELETE_WAITING + " error");
                    }
                })
                .execute();
    }


    public static void modifyWaiting(String user_id, String store_id, final Handler handler,final SweetAlertDialog dialog)
    {
        dialog.setTitleText("예약 수정중..");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_MODIFY_WAITING)
                .addStringPart("user_id", user_id)
                .addStringPart("store_id", store_id)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_MODIFY_WAITING + " response : " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error"))
                            {
                                handler.onFail();
                            }
                            else
                            {
                                handler.onSuccess(jsonObject);
                            }
                        } catch (JSONException e) {
                            handler.onFail();
                            e.printStackTrace();
                        }
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        handler.onFail();
                        Log.d(TAG, TAG_MODIFY_WAITING + " error");
                    }
                })
                .execute();
    }

    public static void joinGame(String user_id, String store_id, final Handler handler,final SweetAlertDialog dialog)
    {
        dialog.setTitleText("참여중....");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_JOIN_GAME)
                .addStringPart("user_id", user_id)
                .addStringPart("store_id", store_id)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_JOIN_GAME + " response : " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error"))
                            {
                                handler.onFail();
                            }
                            else
                            {
                                handler.onSuccess(jsonObject);
                            }
                        } catch (JSONException e) {
                            handler.onFail();
                            e.printStackTrace();
                        }
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        handler.onFail();
                        Log.d(TAG, TAG_JOIN_GAME + " error");
                    }
                })
                .execute();
    }

    public static void exitGame(String user_id, String store_id, final Handler handler,final SweetAlertDialog dialog)
    {
        dialog.setTitleText("퇴장....");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_EXIT_GAME)
                .addStringPart("user_id", user_id)
                .addStringPart("store_id", store_id)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_EXIT_GAME + " response : " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error"))
                            {
                                handler.onFail();
                            }
                            else
                            {
                                handler.onSuccess(jsonObject);
                            }
                        } catch (JSONException e) {
                            handler.onFail();
                            e.printStackTrace();
                        }
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        handler.onFail();
                        Log.d(TAG, TAG_EXIT_GAME + " error");
                    }
                })
                .execute();
    }

    public static void getJoinUserList(String store_id,final Handler handler, final SweetAlertDialog dialog)
    {
        dialog.setTitleText("참여자 불러오는중...");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_GET_JOIN_USER_LIST)
                .addStringPart("store_id", store_id)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_GET_JOIN_USER_LIST + " response : " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error"))
                            {
                                handler.onFail();
                            }
                            else
                            {
                                List<GameUser> list = new ArrayList<GameUser>();
                                JSONArray jsonArray = jsonObject.getJSONArray("users");
                                Gson gson = new Gson();
                                for(int i = 0 ; i < jsonArray.length() ; ++i)
                                {
                                    list.add(gson.fromJson(jsonArray.get(i).toString(), GameUser.class));
                                }

                                handler.onSuccess(list);
                            }
                        } catch (JSONException e) {
                            handler.onFail();
                            e.printStackTrace();
                        }
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        handler.onFail();
                        Log.d(TAG, TAG_GET_JOIN_USER_LIST + " error");
                    }
                })
                .execute();
    }

    public static void getWaitingList(String store_id, final  Handler handler)
    {
        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_GET_WAITING_LIST)
                .addStringPart("store_id", store_id)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, TAG_GET_WAITING_LIST + " response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error")){
                                handler.onFail();
                            }else{
                                Gson gson = new Gson();
                                List<WaitingInfo> list = new ArrayList<WaitingInfo>();

                                JSONArray jsonArray = jsonObject.getJSONArray("waiting_list");

                                for(int i = 0 ; i < jsonArray.length() ; ++i)
                                {
                                    list.add(gson.fromJson(jsonArray.get(i).toString(), WaitingInfo.class));
                                }

                                handler.onSuccess(list);
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
                        Log.d(TAG, TAG_GET_WAITING_LIST + " error");
                        handler.onFail();
                    }
                })
                .execute();
    }

    public static void getReservationInfo(String user_id, String store_id, final Handler handler, final SweetAlertDialog dialog)
    {
        dialog.setTitleText("예약정보 불러오는중...");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_GET_RESERVATION_INFO)
                .addStringPart("user_id", user_id)
                .addStringPart("store_id", store_id)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_GET_RESERVATION_INFO + " response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error")){
                                handler.onFail();
                            }else{
                                ReservationInfo info = new Gson().fromJson(jsonObject.getString("info"), ReservationInfo.class);
                                handler.onSuccess(info);
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
                        Log.d(TAG, TAG_GET_RESERVATION_INFO + " error");
                        handler.onFail();
                    }
                })
                .execute();
    }

    public static void insertWaiting(String user_id, String store_id, final Handler handler, final SweetAlertDialog dialog)
    {
        dialog.setTitleText("예약중...");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_INSERT_WAITING)
                .addStringPart("user_id", user_id)
                .addStringPart("store_id", store_id)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_INSERT_WAITING + " response : " + response);
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
                        Log.d(TAG, TAG_INSERT_WAITING + " error");
                    }
                })
                .execute();
    }


    public static void checkWaiting(String user_id, final  Handler handler, final  SweetAlertDialog dialog)
    {
        dialog.setTitleText("대기정보 확인중...");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_CHECK_WAITING)
                .addStringPart("user_id", user_id)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_CHECK_WAITING + " response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error")){
                                handler.onFail();
                            }else{
                                handler.onSuccess(jsonObject.getBoolean("waiting"));
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
                        Log.d(TAG, TAG_CHECK_WAITING + " error");
                    }
                })
                .execute();
    }

    public static void checkWaiting(String user_id, String store_id, final  Handler handler, final  SweetAlertDialog dialog)
    {
        dialog.setTitleText("대기정보 확인중...");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_CHECK_WAITING)
                .addStringPart("user_id", user_id)
                .addStringPart("store_id", store_id)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_CHECK_WAITING + " response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error")){
                                handler.onFail();
                            }else{
                                handler.onSuccess(jsonObject.getBoolean("waiting"));
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
                        Log.d(TAG, TAG_CHECK_WAITING + " error");
                    }
                })
                .execute();
    }


    public static void getStoreList(String sidx, String count, final  Handler handler, final  SweetAlertDialog dialog)
    {
        dialog.setTitleText("정보 불러오는중...");
        dialog.show();

        Volleyer.volleyer().post(URL)
                .addStringPart(NAME_TAG, TAG_GET_STORE_LIST)
                .addStringPart("sidx", sidx)
                .addStringPart("count", count)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_GET_STORE_LIST + " response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("error")){
                                handler.onFail();
                            }else{
                                JSONArray jsonArray = jsonObject.getJSONArray("store");
                                Gson gson = new Gson();
                                List<Store> storeList = new ArrayList<Store>();
                                for(int i = 0 ; i < jsonArray.length() ; ++i)
                                {
                                    storeList.add(gson.fromJson(jsonArray.get(i).toString(), Store.class));
                                }
                                handler.onSuccess(storeList);
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
                        Log.d(TAG, TAG_GET_STORE_LIST + " error");
                    }
                })
                .execute();
    }
}
