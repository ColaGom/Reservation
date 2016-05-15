package com.bluewave.reservation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.adapter.StoreAdapter;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.model.UserPref;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.StoreClient;
import com.bluewave.reservation.net.UserClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.lv_store)
    ListView lvStore;

    private int mCurrentSidx = 0;
    private StoreAdapter storeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_store);
        ButterKnife.bind(this);
        storeAdapter = new StoreAdapter(this, R.layout.row_store_thumb);
        lvStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store store = (Store)lvStore.getItemAtPosition(position);

            }
        });
        lvStore.setAdapter(storeAdapter);
        doAutoLogin();
    }

    private void doAutoLogin()
    {
        String id = UserPref.getId();
        String password = UserPref.getPassword();

        if(!TextUtils.isEmpty(id)){
            requestLogin(id, password);
        }else{
            startLoginActivity();
        }
    }

    private void requestStoreList()
    {
        StoreClient.getStoreList(mCurrentSidx + "", "20", new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                List<Store> result = (List<Store>)object;
                mCurrentSidx += result.size();

                storeAdapter.addAll(result);

            }

            @Override
            public void onFail() {

            }
        },getProgressDialog());
    }

    private void requestLogin(String id, String password)
    {
        UserClient.login(id, password, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                requestStoreList();
            }

            @Override
            public void onFail() {
                showToast(R.string.fail_login);
            }
        },getProgressDialog());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_LOGIN)
        {
            if(resultCode == RESULT_OK)
            {
                requestStoreList();
            }
        }
    }
}
