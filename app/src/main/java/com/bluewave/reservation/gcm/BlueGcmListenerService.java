package com.bluewave.reservation.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.bluewave.reservation.R;
import com.bluewave.reservation.activity.MainActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.GamePush;
import com.bluewave.reservation.model.P2PPush;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Developer on 2016-05-20.
 */
public class BlueGcmListenerService extends GcmListenerService {
    private final String TYPE_P2P = "p2p";
    private final String TYPE_GAME = "game";
    private final String types[] = {TYPE_P2P, TYPE_GAME};
    private static final String TAG = "GcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {

        Log.d(TAG, "From: " + from);

        for (String type : types) {
            if (data.get(type) != null) {
                String message = data.getString(type);
                Log.d(TAG, "type: " + type);
                Log.d(TAG, "message: " + message);
                processMessage(type, message);
                break;
            }
        }
    }

    private void processMessage(String type, String message) {
        switch (type) {
            case TYPE_P2P: {
                P2PPush push = new Gson().fromJson(message, P2PPush.class);
                Intent intent = new Intent(this, MainActivity.class);
                String title = "1:1 대화 알림";
                String content = String.format("[%s] %s님께 메세지 도착", push.store_name, push.sender_name);

                sendNotification(title, content, 1, intent);

                break;
            }
            case TYPE_GAME: {
                GamePush push = new Gson().fromJson(message, GamePush.class);
                Intent intent = new Intent(this, MainActivity.class);
                String title = "복불복 게임 진행";
                String content = String.format("[%s]", push.store_name);

                sendNotification(title, content, 2, intent);
                sendLocalBroadCast(Const.ACTION_END_GAME);
                break;
            }
        }
    }

    private void sendLocalBroadCast(String action)
    {
        Intent intent = new Intent(action);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param content GCM message received.
     */
    private void sendNotification(String title, String content, int requestCode, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(requestCode, notificationBuilder.build());
    }
}
