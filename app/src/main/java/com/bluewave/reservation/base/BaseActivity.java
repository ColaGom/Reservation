package com.bluewave.reservation.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.bluewave.reservation.activity.LoginActivity;
import com.bluewave.reservation.activity.MapActivity;
import com.bluewave.reservation.activity.SignInActivity;
import com.bluewave.reservation.activity.StoreActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Store;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Developer on 2016-05-12.
 */
public class BaseActivity extends AppCompatActivity {

    protected final int REQUEST_SIGNIN = 1;
    protected final int REQUEST_LOGIN = 2;

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


    protected void startStoreActivity(Store store)
    {
        Intent intent = new Intent(this, StoreActivity.class);
        intent.putExtra(Const.EXTRA_STORE, store);
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
