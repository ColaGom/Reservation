package com.bluewave.reservation.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bluewave.reservation.R;
import com.bluewave.reservation.activity.WriteReviewActivity;
import com.bluewave.reservation.adapter.ReviewAdapter;
import com.bluewave.reservation.base.BaseFragment;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Review;
import com.bluewave.reservation.model.Store;
import com.bluewave.reservation.net.Client;
import com.bluewave.reservation.net.StoreClient;
import com.bluewave.reservation.view.ObservableScrollView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-06-05.
 */
public class StoreReviewFragment extends BaseFragment implements ObservableScrollView.ScrollViewListener {

    protected final int REQUEST_WRITE_REVIEW = 1;

    @Bind(R.id.root_review)
    LinearLayout rootReview;

    ReviewAdapter reviewAdapter;

    private Store mStore;
    private int mCurrentSidx = 0;
    private boolean isLastReview = false;
    private boolean isLoading = false;

    public static final StoreReviewFragment newInstance(Store store) {
        StoreReviewFragment f = new StoreReviewFragment();
        Bundle b = new Bundle();
        b.putSerializable(Const.EXTRA_STORE, store);
        f.setArguments(b);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStore = (Store) getArguments().getSerializable(Const.EXTRA_STORE);
        reviewAdapter = new ReviewAdapter(getContext(), R.layout.row_review);

        requestReview();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_store_review, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.btn_write)
    void onClickWrite() {
        Intent intent = new Intent(getContext(), WriteReviewActivity.class);
        intent.putExtra(Const.EXTRA_STORE, mStore);
        startActivityForResult(intent, REQUEST_WRITE_REVIEW);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_WRITE_REVIEW && resultCode == Activity.RESULT_OK)
        {
            refresh();
        }
    }


    private void refresh()
    {
        rootReview.removeAllViews();
        reviewAdapter.clear();
        mCurrentSidx = 0;
        requestReview();
    }

    public void requestReview()
    {
        if(isLastReview) return;

        isLoading = true;

        StoreClient.getReivewList(mStore.id, mCurrentSidx + "", "10", new Client.Handler() {
            @Override
            public void onSuccess(Object object) {
                isLoading = false;
                List<Review> list = (List<Review>)object;
                reviewAdapter.addAll(list);

                for(int i = mCurrentSidx ; i < mCurrentSidx + list.size() ;++i )
                {
                    View v = reviewAdapter.getView(i, null, null);
                    rootReview.addView(v);
                }

                mCurrentSidx += list.size();
            }

            @Override
            public void onFail() {
                isLoading = false;
                isLastReview = true;
            }
        },getProgressDialog());
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int diff) {

        if(!isLoading && !isLastReview && diff < 100)
        {
            requestReview();
        }
    }
}
