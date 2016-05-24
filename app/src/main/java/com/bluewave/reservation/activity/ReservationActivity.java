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
    private ReservationInfo mInfo;

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
                mInfo = (ReservationInfo)object;

                tvUserWaiting.setText(String.format("%d 번째",mInfo.wait_num));
                tvCurrentWaiting.setText(String.format("%d 명",mInfo.total_count));
                tvPredictionTime.setText(String.format("%d 분",(mInfo.wait_num) * 10));
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
        StoreClient.joinGame(Global.getLoginUser().getId(), mStore.id, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                startGameActivity(mStore);
            }

            @Override
            public void onFail() {
                showToast(R.string.fail_join);
            }
        },getProgressDialog());
    }

    @OnClick(R.id.btn_waiting_room)
    void onClickWaitingRoom()
    {
        startWaitingActivity(mStore, mInfo);
    }

    @OnClick(R.id.btn_cancel)
    void onClickCancel()
    {
        StoreClient.deleteWaiting(Global.getLoginUser().getId(), mStore.id, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                showToast(R.string.success_delete_waiting);
                finish();
            }
            @Override
            public void onFail() {
                showToast(R.string.fail_delete_waiting);
            }
        },getProgressDialog());
    }

    @OnClick(R.id.btn_modify)
    void onClickModify()
    {
        StoreClient.modifyWaiting(Global.getLoginUser().getId(), mStore.id, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                requestReservationInfo();
            }
            @Override
            public void onFail() {
                showToast(R.string.fail_modify_waiting);
            }
        },getProgressDialog());
    }
}
