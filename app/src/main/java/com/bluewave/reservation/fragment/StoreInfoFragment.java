package com.bluewave.reservation.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Store;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Developer on 2016-06-05.
 */
public class StoreInfoFragment extends Fragment{

    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tv_contact)
    TextView tvContact;
    @Bind(R.id.tv_holiday)
    TextView tvHoliday;
    @Bind(R.id.tv_opening_hour)
    TextView tvOpeningHour;

    private Store mStore;

    public static final StoreInfoFragment newInstance(Store store)
    {
        StoreInfoFragment f = new StoreInfoFragment();
        Bundle b = new Bundle();
        b.putSerializable(Const.EXTRA_STORE, store);
        f.setArguments(b);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStore = (Store)getArguments().getSerializable(Const.EXTRA_STORE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_store_info, container, false);
        ButterKnife.bind(this, rootView);

        tvLocation.setText(mStore.location);
        tvContact.setText(mStore.contact);
        tvHoliday.setText(mStore.holiday);
        tvOpeningHour.setText(mStore.opening_hour);

        return rootView;
    }
}
