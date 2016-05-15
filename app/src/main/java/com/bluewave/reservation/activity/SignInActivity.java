package com.bluewave.reservation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;

import com.bluewave.reservation.R;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.net.UserClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-05-12.
 */
public class SignInActivity extends BaseActivity {

    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_id)
    EditText etId;
    @Bind(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_signin)
    void requestSignin() {
        final String id = etId.getText().toString();
        final String password = etPassword.getText().toString();
        String name = etName.getText().toString();

        if (TextUtils.isEmpty(id)) {
            showToast(R.string.input_id);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast(R.string.input_password);
            return;
        }
        if (TextUtils.isEmpty(name)) {
            showToast(R.string.input_name);
            return;
        }

        UserClient.insertUser(id, password, name, new UserClient.Handler() {
            @Override
            public void onSuccess(Object object) {
                showToast(R.string.success_signin);
                Intent intent = new Intent();
                intent.putExtra(Const.EXTRA_ID, id);
                intent.putExtra(Const.EXTRA_PASSWORD, password);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFail() {
                showToast(R.string.fail_signin);
            }
        }, getProgressDialog());
    }
}
