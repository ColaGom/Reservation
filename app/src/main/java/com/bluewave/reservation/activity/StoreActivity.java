package com.bluewave.reservation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Store;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        Store store = (Store)bundle.getSerializable(Const.EXTRA_STORE);

        Glide.with(this).load(Const.URL_LOGO + store.logo_url).crossFade().into(ivLogo);
        tvLocation.setText(store.location);
        tvContact.setText(store.contact);
        tvHoliday.setText(store.holiday);
        tvOpeningHour.setText(store.opening_hour);
    }

    @OnClick(R.id.btn_reservation)
    void onClickReservation() {

    }

    @OnClick(R.id.btn_gps)
    void onClickGps() {

    }
}
