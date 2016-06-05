package com.bluewave.reservation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RatingBar;

import com.bluewave.reservation.R;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Global;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.StoreClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-05-15.
 */
public class WriteReviewActivity extends BaseActivity implements OnRequestPermissionsResultCallback {

    @Bind(R.id.et_input)
    EditText etInput;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;

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
        String content = etInput.getText().toString();

        if(TextUtils.isEmpty(content))
        {
            showToast(R.string.input_content);
            return;
        }

        StoreClient.insertReview(Global.getLoginUser().getId(), mStore.id, etInput.getText().toString(), ratingBar.getRating(), new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                showToast(R.string.success_write_review);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail() {
                showToast(R.string.fail_write_review);
            }
        },getProgressDialog());
    }
}
