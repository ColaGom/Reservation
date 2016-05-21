package com.bluewave.reservation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.adapter.CommentAdatper;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Comment;
import com.bluewave.reservation.model.GameUser;
import com.bluewave.reservation.model.Global;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.CommentClient;
import com.bluewave.reservation.net.StoreClient;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-05-18.
 */
public class GameActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.tv_join)
    TextView tvJoin;
    @Bind(R.id.lv_comment)
    ListView lvComment;
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @Bind(R.id.et_input)
    EditText etInput;

    private CommentAdatper adapter;
    private int mStartIdx;
    private Store mStore;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        mStore = (Store)getIntent().getSerializableExtra(Const.EXTRA_STORE);
        adapter = new CommentAdatper(this, R.layout.row_comment);
        lvComment.setAdapter(adapter);

        mStartIdx = 0;
        swipeContainer.setOnRefreshListener(this);

        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(true);

                requestCommentList();
            }
        });

        requestJoinUserList();
    }

    private void requestJoinUserList()
    {
        StoreClient.getJoinUserList(mStore.id, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                List<GameUser> list = (List<GameUser>)object;
                if(list.size() == 0)
                {
                    tvJoin.setText(getString(R.string.empty_join));
                }
                else
                {
                    tvJoin.setText("참여자 수 : " + list.size());
                }
            }

            @Override
            public void onFail() {
                tvJoin.setText(getString(R.string.empty_join));
            }
        },getProgressDialog());
    }

    private void requestCommentList()
    {
        if(mStartIdx == 0){
            adapter.clear();
        }

        CommentClient.getComment(mStore.id, mStartIdx + "", "30", Const.POS_GAME, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                List<Comment> list = (List<Comment>)object;
                adapter.addAll(list);

                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFail() {
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void requestInsertComment()
    {
        String content = etInput.getText().toString();

        if(TextUtils.isEmpty(content))
        {
            showToast(R.string.empty_content);
            return;
        }

        CommentClient.insertComment(Global.getLoginUser().getId(), mStore.id, content, Const.POS_GAME, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                mStartIdx = 0;
                requestCommentList();
                etInput.setText("");
            }

            @Override
            public void onFail() {
                showToast(R.string.fail_write_comment);
            }
        }, getProgressDialog());
    }

    @Override
    public void onBackPressed() {
        onClickExit();
        //super.onBackPressed();
    }

    @Override
    public void onRefresh() {
        mStartIdx = 0;
        requestCommentList();
    }

    @OnClick(R.id.btn_send)
    void onClickSend()
    {
        requestInsertComment();
    }

    @OnClick(R.id.btn_exit)
    void onClickExit()
    {
        StoreClient.exitGame(Global.getLoginUser().getId(), mStore.id, new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                showToast(R.string.success_exit);
                finish();
            }

            @Override
            public void onFail() {
                showToast(R.string.fail_exit);
            }
        },getProgressDialog());
    }
}
