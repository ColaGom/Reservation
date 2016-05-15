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
public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_id)
    EditText etId;
    @Bind(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    void onClickLogin()
    {
        String id = etId.getText().toString();
        String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(id)){
            showToast(R.string.input_id);
            return;
        }
        if(TextUtils.isEmpty(password)){
            showToast(R.string.input_password);
            return;
        }

        requsetLogin(id, password);
    }

    private void requsetLogin(String id, String password)
    {
        UserClient.login(id, password, new UserClient.Handler() {
            @Override
            public void onSuccess(Object object) {
                showToast(R.string.success_login);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail() {
                startLoginActivity();
            }
        },getProgressDialog());
    }


    @OnClick(R.id.btn_signin)
    void onClickSignIn()
    {
        startSignInActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_SIGNIN)
        {
            if(resultCode == RESULT_OK)
            {
                String id = data.getStringExtra(Const.EXTRA_ID);
                String password = data.getStringExtra(Const.EXTRA_PASSWORD);

                requsetLogin(id, password);
            }
        }
    }
}
