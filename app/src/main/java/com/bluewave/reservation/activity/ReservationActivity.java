package com.bluewave.reservation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Global;
import com.bluewave.reservation.model.ReservationInfo;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.StoreClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-05-16.
 */
public class ReservationActivity extends BaseActivity {

    @Bind(R.id.tv_user_waiting)
    TextView tvUserWaiting;
    @Bind(R.id.tv_current_waiting)
    TextView tvCurrentWaiting;
    @Bind(R.id.tv_prediction_time)
    TextView tvPredictionTime;

    private Store mStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        ButterKnife.bind(this);

        mStore = (Store) getIntent().getSerializableExtra(Const.EXTRA_STORE);
        requestReservationInfo();
    }

    private void requestReservationInfo()
    {
        StoreClient.getReservationInfo(Global.getLoginUser().getId(), mStore.id, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                ReservationInfo info = (ReservationInfo)object;

                tvUserWaiting.setText(String.format("%d 번째",info.wait_num));
                tvCurrentWaiting.setText(String.format("%d 명",info.total_count));
                tvPredictionTime.setText(String.format("%d 분",(info.wait_num) * 10));
            }

            @Override
            public void onFail() {
                showToast(R.string.fail_request_reservation);
                finish();
            }
        },getProgressDialog());
    }

    @OnClick(R.id.btn_gps)
    void onClickGPS()
    {
        startMapActivity(mStore);
    }

    @OnClick(R.id.btn_game)
    void onClickGame()
    {

    }

    @OnClick(R.id.btn_waiting_room)
    void onClickWaitingRoom()
    {
        startWaitingActivity(mStore);
    }

    @OnClick(R.id.btn_cancel)
    void onClickCancel()
    {

    }

    @OnClick(R.id.btn_modify)
    void onClickModify()
    {

    }
}
