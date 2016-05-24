package com.bluewave.reservation.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bluewave.reservation.activity.CommentActivity;
import com.bluewave.reservation.activity.GameActivity;
import com.bluewave.reservation.activity.LoginActivity;
import com.bluewave.reservation.activity.MapActivity;
import com.bluewave.reservation.activity.P2PActivity;
import com.bluewave.reservation.activity.ReservationActivity;
import com.bluewave.reservation.activity.SignInActivity;
import com.bluewave.reservation.activity.StoreActivity;
import com.bluewave.reservation.activity.WaitingActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.ReservationInfo;
import com.bluewave.reservation.model.Store;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Developer on 2016-05-12.
 */
public class BaseActivity extends AppCompatActivity {

    protected final int REQUEST_SIGNIN = 1;
    protected final int REQUEST_LOGIN = 2;
    protected final int REQUEST_PERMISSION = 3;

    protected SweetAlertDialog getProgressDialog() {
        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setCancelable(false);

        return dialog;
    }

    protected void startMapActivity(Store store)
    {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra(Const.EXTRA_STORE, store);
        startActivity(intent);
    }

    protected void startP2PActivity(Store store, String oppent_id)
    {
        Intent intent = new Intent(this, P2PActivity.class);
        intent.putExtra(Const.EXTRA_STORE, store);
        intent.putExtra(Const.EXTRA_OPPENT_ID, oppent_id);
        startActivity(intent);
    }

    protected void startStoreActivity(Store store)
    {
        Intent intent = new Intent(this, StoreActivity.class);
        intent.putExtra(Const.EXTRA_STORE, store);
        startActivity(intent);
    }

    protected void startReservationActivity(Store store)
    {
        Intent intent = new Intent(this, ReservationActivity.class);
        intent.putExtra(Const.EXTRA_STORE, store);
        startActivity(intent);
    }

    protected void startCommentActivity(Store store)
    {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra(Const.EXTRA_STORE, store);
        startActivity(intent);
    }

    protected void startGameActivity(Store store)
    {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(Const.EXTRA_STORE, store);
        startActivity(intent);
    }

    protected void startWaitingActivity(Store store, ReservationInfo info)
    {
        Intent intent = new Intent(this, WaitingActivity.class);
        intent.putExtra(Const.EXTRA_STORE, store);
        intent.putExtra(Const.EXTRA_INFO, info);
        startActivity(intent);
    }


    protected void startSignInActivity()
    {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivityForResult(intent, REQUEST_SIGNIN);
    }

    protected void startLoginActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    protected void showToast(int id) {
        showToast(getString(id));
    }

    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
