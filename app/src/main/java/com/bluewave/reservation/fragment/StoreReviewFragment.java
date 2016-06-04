package com.bluewave.reservation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bluewave.reservation.R;
import com.bluewave.reservation.activity.WriteReviewActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Store;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Developer on 2016-06-05.
 */
public class StoreReviewFragment extends Fragment {

    protected final int REQUEST_WRITE_REVIEW = 1;

    @Bind(R.id.root_review)
    LinearLayout rootReview;

    private Store mStore;

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
    }
}
