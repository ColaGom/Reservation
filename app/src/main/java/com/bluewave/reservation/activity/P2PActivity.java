package com.bluewave.reservation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ListView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.adapter.CommentAdatper;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Comment;
import com.bluewave.reservation.model.Global;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.CommentClient;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-05-22.
 */
public class P2PActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    @Bind(R.id.lv_comment)
    ListView lvComment;

    @Bind(R.id.et_input)
    EditText etInput;

    private CommentAdatper adapter;
    private Store mStore;
    private String mOpponentId;
    private int mStartIdx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        mStore = (Store)getIntent().getSerializableExtra(Const.EXTRA_STORE);
        mOpponentId = getIntent().getStringExtra(Const.EXTRA_OPPENT_ID);
        adapter = new CommentAdatper(this, R.layout.row_comment);
        lvComment.setAdapter(adapter);

        swipeContainer.setOnRefreshListener(this);

        swipeContainer.post(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(true);

                requestCommentList();
            }
        });
    }


    @OnClick(R.id.btn_send)
    void onClickSend()
    {
        requestInsertComment();
    }

    private void requestCommentList()
    {
        if(mStartIdx == 0){
            adapter.clear();
        }

        CommentClient.getCommentP2P(Global.getLoginUser().getId() ,mOpponentId , mStore.id, mStartIdx + "", "30", new Client.Handler() {
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

        CommentClient.insertCommentP2P(Global.getLoginUser().getId(), mOpponentId, mStore.id, content, new Client.Handler() {
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
    public void onRefresh() {
        mStartIdx = 0;
        requestCommentList();
    }
}
