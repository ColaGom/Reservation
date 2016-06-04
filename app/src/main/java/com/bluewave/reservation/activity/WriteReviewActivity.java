package com.bluewave.reservation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.widget.EditText;

import com.bluewave.reservation.R;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Store;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-05-15.
 */
public class WriteReviewActivity extends BaseActivity implements OnRequestPermissionsResultCallback {

    @Bind(R.id.et_input)
    EditText etInput;

    private Store mStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        mStore = (Store)bundle.getSerializable(Const.EXTRA_STORE);

        setCustomActionBar(R.layout.actionbar_complete, mStore.name, true);
        setContentView(R.layout.activity_write_review);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_complete)
    void onClickCompelte()
    {

    }
}
