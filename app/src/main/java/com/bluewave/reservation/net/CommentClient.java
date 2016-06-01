package com.bluewave.reservation.net;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bluewave.reservation.model.Comment;
import com.bluewave.reservation.model.Global;
import com.bluewave.reservation.model.User;
import com.bluewave.reservation.model.UserPref;
import com.google.gson.Gson;
import com.navercorp.volleyextensions.volleyer.Volleyer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Colabear on 2016-05-16.
 */
public class CommentClient extends Client {
    private final static String TAG = "CommentClient";
    private final static String URL = "http://hpl.dothome.co.kr/api/comment.php";

    private final static String TAG_INSERT_COMMENT = "insert_comment";
    private final static String TAG_GET_COMMENT = "get_comment";
    private final static String TAG_DELETE_COMMENT = "delete_comment";

    public static void insertComment(String user_id, String store_id, String content, String position, final Handler handler, final SweetAlertDialog dialog) {
        dialog.setTitleText("전송중...");
        dialog.show();

        Volleyer.volleyer()
                .post(URL)
                .addStringPart(NAME_TAG, TAG_INSERT_COMMENT)
                .addStringPart("user_id", user_id)
                .addStringPart("store_id", store_id)
                .addStringPart("position", position)
                .addStringPart("content", content)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_INSERT_COMMENT + " response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("error")) {
                                handler.onFail();
                            } else {
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
                        handler.onFail();
                        Log.d(TAG, TAG_INSERT_COMMENT + "error");
                    }
                })
                .execute();
    }

    public static void insertCommentP2P(String sender_id, String recv_id, String store_id, String content, final Handler handler, final SweetAlertDialog dialog) {
        dialog.setTitleText("전송중...");
        dialog.show();

        Volleyer.volleyer()
                .post(URL)
                .addStringPart(NAME_TAG, TAG_INSERT_COMMENT)
                .addStringPart("sender_id", sender_id)
                .addStringPart("recv_id", recv_id)
                .addStringPart("store_id", store_id)
                .addStringPart("content", content)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_INSERT_COMMENT + "P2P response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("error")) {
                                handler.onFail();
                            } else {
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
                        handler.onFail();
                        Log.d(TAG, TAG_INSERT_COMMENT + "P2P error");
                    }
                })
                .execute();
    }

    /**
     * @param store_id
     * @param sidx
     * @param count
     * @param position game - 복불복 게임, store - 매장
     * @param handler
     */
    public static void getComment(String store_id, String sidx, String count, String position, final Handler handler) {

        Volleyer.volleyer()
                .post(URL)
                .addStringPart(NAME_TAG, TAG_GET_COMMENT)
                .addStringPart("store_id", store_id)
                .addStringPart("sidx", sidx)
                .addStringPart("count", count)
                .addStringPart("position", position)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, TAG_GET_COMMENT + " response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("error")) {
                                handler.onFail();
                            } else {
                                JSONArray jsonArray = jsonObject.getJSONArray("comments");

                                Gson gson = new Gson();
                                List<Comment> list = new ArrayList<Comment>();

                                for (int i = 0; i < jsonArray.length(); ++i) {
                                    list.add(gson.fromJson(jsonArray.get(i).toString(), Comment.class));
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
                        handler.onFail();
                        Log.d(TAG, TAG_GET_COMMENT + "error");
                    }
                })
                .execute();
    }

    public static void getCommentP2P(String sender_id, String recv_id, String store_id, String sidx, String count, final Handler handler) {
        Volleyer.volleyer()
                .post(URL)
                .addStringPart(NAME_TAG, TAG_GET_COMMENT)
                .addStringPart("sender_id", sender_id)
                .addStringPart("recv_id", recv_id)
                .addStringPart("store_id", store_id)
                .addStringPart("sidx", sidx)
                .addStringPart("count", count)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, TAG_GET_COMMENT + "P2P response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("error")) {
                                handler.onFail();
                            } else {
                                JSONArray jsonArray = jsonObject.getJSONArray("comments");

                                Gson gson = new Gson();
                                List<Comment> list = new ArrayList<Comment>();

                                for (int i = 0; i < jsonArray.length(); ++i) {
                                    list.add(gson.fromJson(jsonArray.get(i).toString(), Comment.class));
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
                        Log.d(TAG, TAG_GET_COMMENT + "P2P error");
                        handler.onFail();
                    }
                })
                .execute();
    }

    public static void deleteComment(String uid, final Handler handler, final SweetAlertDialog dialog) {
        Volleyer.volleyer()
                .post(URL)
                .addStringPart(NAME_TAG, TAG_DELETE_COMMENT)
                .addStringPart("uid", uid)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_DELETE_COMMENT + " response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("error")) {
                                handler.onFail();
                            } else {
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
                        Log.d(TAG, TAG_DELETE_COMMENT + " error");
                        handler.onFail();
                    }
                })
                .execute();
    }
}
