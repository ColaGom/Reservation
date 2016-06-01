package com.bluewave.reservation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bluewave.reservation.R;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Global;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.StoreClient;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-05-26.
 */
public class EnterActivity extends BaseActivity {
    private Store mStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        ButterKnife.bind(this);
        mStore = (Store)getIntent().getSerializableExtra(Const.EXTRA_STORE);
    }

    @OnClick(R.id.btn_enter)
    void onClickEnter()
    {
        StoreClient.deleteWaiting(Global.getLoginUser().getId(), mStore.id, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                showToast(R.string.success_enter);
                finish();
            }
            @Override
            public void onFail() {
                showToast(R.string.fail_enter);
            }
        },getProgressDialog());
    }
}
