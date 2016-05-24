package com.bluewave.reservation.activity;

import android.content.Context;
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
import com.bluewave.reservation.model.Global;
import com.bluewave.reservation.model.ReservationInfo;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.model.WaitingInfo;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.CommentClient;
import com.bluewave.reservation.net.StoreClient;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Developer on 2016-05-17.
 */
public class WaitingActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.lv_waiting)
    ListView lvWaiting;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @Bind(R.id.root)
    RelativeLayout root;

    private WaitingAdapter adapterWaiting;
    private PopupWindow popupWindow;
    private Store mStore;
    private ReservationInfo mInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        ButterKnife.bind(this);

        mStore = (Store) getIntent().getSerializableExtra(Const.EXTRA_STORE);
        mInfo = (ReservationInfo) getIntent().getSerializableExtra(Const.EXTRA_INFO);
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
        if(popupWindow != null && popupWindow.isShowing())
            popupWindow.dismiss();
        else
            super.onBackPressed();
    }

    private void showWaitingPopup(final WaitingInfo info) {
        Log.d(Const.TAG, "show");
        LayoutInflater inflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.popup_waiting_info, null);

        View btnYield = popupView.findViewById(R.id.btn_yield);
        View btnP2P = popupView.findViewById(R.id.btn_p2p);

        if(Global.getLoginUser().getId().equals(info.user_id))
        {
            btnYield.setVisibility(View.GONE);
            btnP2P.setVisibility(View.GONE);
        }
        else
        {
            btnYield.setVisibility(View.VISIBLE);
            btnP2P.setVisibility(View.VISIBLE);

            btnYield.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mInfo.wait_num > info.wait_num)
                    {
                        showToast(R.string.fail_yield);
                    }else {
                        new SweetAlertDialog(WaitingActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText(getString(R.string.yield))
                                .setContentText(String.format("현재 나의 순번 %d, 상대방의 순번 %d번 교체하시겠습니까?", mInfo.wait_num, info.wait_num))
                                .setCancelText(getString(R.string.no))
                                .setConfirmText(getString(R.string.yes))
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        CommentClient.yieldWaiting(Global.getLoginUser().getId(), info.user_id, mStore.id, new Client.Handler() {
                                            @Override
                                            public void onSuccess(Object object) {
                                                showToast(R.string.success_yield);
                                                requestWaitingList();
                                            }

                                            @Override
                                            public void onFail() {
                                                showToast(R.string.fail_yield);
                                            }
                                        },getProgressDialog());
                                        sweetAlertDialog.dismiss();
                                    }
                                }).show();
                    }
                }
            });

            btnP2P.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startP2PActivity(mStore, info.user_id);
                }
            });
        }

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
        startCommentActivity(mStore);
    }

    @Override
    public void onRefresh() {
        requestWaitingList();
    }
}
