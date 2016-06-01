package com.bluewave.reservation.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.common.PermissionUtils;
import com.bluewave.reservation.model.Global;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.StoreClient;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-05-15.
 */
public class StoreActivity extends BaseActivity implements OnRequestPermissionsResultCallback {

    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tv_contact)
    TextView tvContact;
    @Bind(R.id.tv_holiday)
    TextView tvHoliday;
    @Bind(R.id.tv_opening_hour)
    TextView tvOpeningHour;

    @Bind(R.id.btn_waiting)
    Button btnWaiting;

    @Bind(R.id.btn_reservation)
    Button btnReservation;

    private Store mStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        mStore = (Store) bundle.getSerializable(Const.EXTRA_STORE);

        Glide.with(this).load(Const.URL_LOGO + mStore.logo_url).crossFade().into(ivLogo);
        tvLocation.setText(mStore.location);
        tvContact.setText(mStore.contact);
        tvHoliday.setText(mStore.holiday);
        tvOpeningHour.setText(mStore.opening_hour);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permission, REQUEST_PERMISSION);
        }

        requestCheckWaiting();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestCheckWaiting();
    }

    private void requestInsertWaiting() {
        StoreClient.insertWaiting(Global.getLoginUser().getId(), mStore.id, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                showToast(R.string.success_insert);
                setComponent(true);
            }

            @Override
            public void onFail() {
                showToast(R.string.fail_insert);
            }
        }, getProgressDialog());
    }

    private void requestCheckWaiting() {
        StoreClient.checkWaiting(Global.getLoginUser().getId(), mStore.id, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                Boolean reservation = (Boolean) object;
                setComponent(reservation);
            }

            @Override
            public void onFail() {
                setComponent(false);
            }
        }, getProgressDialog());
    }

    private float getDistance() {


        Location userLocation = getLastKnownLocation();

        if(userLocation == null)
        {
            return -1;
        }

        LatLng storeLocation = mStore.getLatlng();
        float[] result = new float[1];
        Location.distanceBetween(userLocation.getLatitude(), userLocation.getLongitude(), storeLocation.latitude, storeLocation.longitude, result);

        return result[0];
    }

    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    private void setComponent(boolean reservation)
    {
        if(reservation)
        {
            btnReservation.setVisibility(View.GONE);
            btnWaiting.setVisibility(View.VISIBLE);
        }
        else
        {
            btnReservation.setVisibility(View.VISIBLE);
            btnWaiting.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.btn_waiting)
    void onClickWaiting(){

        startReservationActivity(mStore);
    }

    @OnClick(R.id.btn_reservation)
    void onClickReservation() {

        float distance = getDistance();

        if(distance == -1)
        {
            showToast("거리 측정 실패");
            return;
        }
        else if(mStore.id.equals("pass_dis") || distance < 500)
        {
            requestChehckAndInsertWaiting();
        }
        else
        {
            showToast("500M 거리 내의 위치에서만 예약가능합니다.");
        }
    }

    private void requestChehckAndInsertWaiting()
    {
        StoreClient.checkWaiting(Global.getLoginUser().getId(), new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                Boolean result = (Boolean)object;
                if(result)
                {
                    showToast(R.string.already_reservation);
                }
                else
                {
                    requestInsertWaiting();
                }
            }

            @Override
            public void onFail() {

            }
        },getProgressDialog());
    }

    @OnClick(R.id.btn_gps)
    void onClickGps() {
        startMapActivity(mStore);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permission, REQUEST_PERMISSION);
            showToast(R.string.need_permission);
            return;
        }
    }
}
