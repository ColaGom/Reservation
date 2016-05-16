package com.bluewave.reservation.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.adapter.WaitingAdapter;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.model.WaitingInfo;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.StoreClient;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-05-17.
 */
public class WaitingActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.lv_waiting)
    ListView lvWaiting;
    @Bind(R.id.swpie_container)
    SwipeRefreshLayout swipeContainer;
    @Bind(R.id.root)
    RelativeLayout root;

    private WaitingAdapter adapterWaiting;
    private PopupWindow popupWindow;
    private Store mStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        ButterKnife.bind(this);

        mStore = (Store) getIntent().getSerializableExtra(Const.EXTRA_STORE);
        adapterWaiting = new WaitingAdapter(this, R.layout.row_waiting);
        lvWaiting.setAdapter(adapterWaiting);

        lvWaiting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WaitingInfo info = (WaitingInfo) lvWaiting.getItemAtPosition(position);
                showWaitingPopup(info);
            }
        });

        swipeContainer.setOnRefreshListener(this);

        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(true);

                requestWaitingList();
            }
        });
    }

    private void hideWaitingPopup()
    {
        if(popupWindow.isShowing())
            popupWindow.dismiss();
    }

    @Override
    public void onBackPressed() {
        if(popupWindow.isShowing())
            popupWindow.dismiss();
        else
            super.onBackPressed();
    }

    private void showWaitingPopup(WaitingInfo info) {
        Log.d(Const.TAG, "show");
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popup_waiting_info, null);

        popupView.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideWaitingPopup();
            }
        });

        TextView tvName = (TextView) popupView.findViewById(R.id.tv_name);
        tvName.setText(String.format("순번 : %d 이름 : %s",info.wait_num,info.user_name));

        popupWindow = new PopupWindow(
                popupView
                , RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        popupWindow.showAtLocation(findViewById(R.id.root), Gravity.CENTER, 0, 0);
    }

    private void requestWaitingList() {
        swipeContainer.setRefreshing(true);
        adapterWaiting.clear();
        StoreClient.getWaitingList(mStore.id, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                swipeContainer.setRefreshing(false);
                List<WaitingInfo> list = (List<WaitingInfo>) object;
                adapterWaiting.addAll(list);
            }

            @Override
            public void onFail() {
                swipeContainer.setRefreshing(false);
            }
        });
    }


    @OnClick(R.id.btn_enter)
    void onClickEnter() {

    }

    @Override
    public void onRefresh() {
        requestWaitingList();
    }
}
