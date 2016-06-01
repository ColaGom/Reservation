package com.bluewave.reservation.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.adapter.StoreAdapter;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.gcm.RegistrationIntentService;
import com.bluewave.reservation.model.Global;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.model.UserPref;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.CommentClient;
import com.bluewave.reservation.net.StoreClient;
import com.bluewave.reservation.net.UserClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Bind(R.id.lv_store)
    ListView lvStore;
    @Bind(R.id.btn_logout)
    Button btnLogout;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private int mCurrentSidx = 0;
    private StoreAdapter storeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCustomActionbar(R.string.title_store, false);
        ButterKnife.bind(this);
        storeAdapter = new StoreAdapter(this, R.layout.row_store_thumb);
        lvStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store store = (Store) lvStore.getItemAtPosition(position);
                startStoreActivity(store);
            }
        });
        lvStore.setAdapter(storeAdapter);
        doAutoLogin();
    }

    private void registrationGCM() {
        if (!UserPref.getSentToken()) {
            if (checkPlayServices()) {
                Log.d(Const.TAG, "Start RegistrationIntentService");
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private void doAutoLogin() {
        String id = UserPref.getId();
        String password = UserPref.getPassword();

        if (!TextUtils.isEmpty(id)) {
            requestLogin(id, password);
        } else {
            startLoginActivity();
        }
    }

    private void requestStoreList() {
        StoreClient.getStoreList(mCurrentSidx + "", "20", new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                List<Store> result = (List<Store>) object;
                mCurrentSidx += result.size();

                storeAdapter.addAll(result);
            }

            @Override
            public void onFail() {

            }
        }, getProgressDialog());
    }

    private void requestLogin(String id, String password) {
        UserClient.login(id, password, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                registrationGCM();
                requestStoreList();
                setComponent();
            }

            @Override
            public void onFail() {
                showToast(R.string.fail_login);
            }
        }, getProgressDialog());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                registrationGCM();
                requestStoreList();
                setComponent();
            }
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(Const.TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
//
//    @OnClick(R.id.btn_logout)
//    void OnClickLogOut() {
//        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE)
//                .setTitleText(getString(R.string.logout))
//                .setContentText(getString(R.string.msg_logout))
//                .setCancelText(getString(R.string.no))
//                .setConfirmText(getString(R.string.yes))
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        Global.setLoginUser(null);
//                        UserPref.putSentToken(false);
//                        UserPref.putId("");
//                        UserPref.putPassword("");
//                        setComponent();
//                        startLoginActivity();
//                        sweetAlertDialog.dismiss();
//                    }
//                }).show();
//    }

    private void setComponent() {
        if (Global.getLoginUser() == null) {
            btnLogout.setVisibility(View.GONE);
            lvStore.setVisibility(View.GONE);
        } else {
            btnLogout.setVisibility(View.VISIBLE);
            lvStore.setVisibility(View.VISIBLE);
        }
    }
}
