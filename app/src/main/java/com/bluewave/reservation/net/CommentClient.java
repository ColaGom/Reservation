package com.bluewave.reservation.net;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.navercorp.volleyextensions.volleyer.Volleyer;

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

    public static void inesrtComment(String user_id, String store_id, String content, final Handler handler, final SweetAlertDialog dialog)
    {
        Volleyer.volleyer()
                .post(URL)
                .addStringPart(NAME_TAG, TAG_INSERT_COMMENT)
                .addStringPart("user_id", user_id)
                .addStringPart("store_id", store_id)
                .addStringPart("content", content)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_INSERT_COMMENT + " response : " + response);
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_INSERT_COMMENT + "error");
                    }
                })
                .execute();
    }

    public static void insertCommentP2P(String sender_id, String recv_id, String content, final Handler handler, final SweetAlertDialog dialog)
    {
        Volleyer.volleyer()
                .post(URL)
                .addStringPart(NAME_TAG, TAG_INSERT_COMMENT)
                .addStringPart("sender_id", sender_id)
                .addStringPart("recv_id", recv_id)
                .addStringPart("content", content)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_INSERT_COMMENT + "P2P response : " + response);
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_INSERT_COMMENT + "P2P error");
                    }
                })
                .execute();
    }

    public static void getComment(String store_id, String sidx, String count,final Handler handler, final SweetAlertDialog dialog)
    {
        Volleyer.volleyer()
                .post(URL)
                .addStringPart(NAME_TAG, TAG_GET_COMMENT)
                .addStringPart("store_id", store_id)
                .addStringPart("sidx", sidx)
                .addStringPart("count", count)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_GET_COMMENT + " response : " + response);
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_GET_COMMENT + "error");
                    }
                })
                .execute();
    }

    public static void getCommentP2P(String sender_id, String recv_id, String sidx, String count, final Handler handler, final SweetAlertDialog dialog)
    {
        Volleyer.volleyer()
                .post(URL)
                .addStringPart(NAME_TAG, TAG_GET_COMMENT)
                .addStringPart("sender_id", sender_id)
                .addStringPart("recv_id", recv_id)
                .addStringPart("sidx", sidx)
                .addStringPart("count", count)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_GET_COMMENT + "P2P response : " + response);
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_GET_COMMENT + "P2P error");
                    }
                })
                .execute();
    }

    public static void deleteComment(String uid, final Handler handler, final SweetAlertDialog dialog)
    {
        Volleyer.volleyer()
                .post(URL)
                .addStringPart(NAME_TAG, TAG_DELETE_COMMENT)
                .addStringPart("uid", uid)
                .withListener(new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_DELETE_COMMENT + " response : " + response);
                    }
                })
                .withErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Log.d(TAG, TAG_DELETE_COMMENT + " error");
                    }
                })
                .execute();
    }
}
