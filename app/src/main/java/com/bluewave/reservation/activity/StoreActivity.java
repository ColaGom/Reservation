package com.bluewave.reservation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Global;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.model.UserPref;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.StoreClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.ImageVideoBitmapDecoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-05-15.
 */
public class StoreActivity extends BaseActivity {

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
        mStore = (Store)bundle.getSerializable(Const.EXTRA_STORE);

        Glide.with(this).load(Const.URL_LOGO + mStore.logo_url).crossFade().into(ivLogo);
        tvLocation.setText(mStore.location);
        tvContact.setText(mStore.contact);
        tvHoliday.setText(mStore.holiday);
        tvOpeningHour.setText(mStore.opening_hour);

        StoreClient.checkWaiting(Global.getLoginUser().getId(), mStore.id, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                Boolean reservation = (Boolean)object;
                setComponent(reservation);
            }

            @Override
            public void onFail() {
                setComponent(false);
            }
        },getProgressDialog());
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
        },getProgressDialog());
    }

    @OnClick(R.id.btn_gps)
    void onClickGps() {
        startMapActivity(mStore);
    }
}
