package com.bluewave.reservation.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.adapter.StoreAdapter;
import com.bluewave.reservation.base.BaseActivity;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Store;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Developer on 2016-06-06.
 */
public class FavoriteActivity extends BaseActivity {

    private StoreAdapter storeAdapter;
    @Bind(R.id.lv_store)
    ListView lvStore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomActionBar(R.layout.actionbar_main, R.string.favorite, false);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        ArrayList<Store> listStore= (ArrayList<Store>)getIntent().getSerializableExtra(Const.EXTRA_STORE);
        storeAdapter = new StoreAdapter(this, R.layout.row_store);

        lvStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store store = (Store) lvStore.getItemAtPosition(position);
                startStoreActivity(store);
            }
        });
        lvStore.setAdapter(storeAdapter);
        storeAdapter.addAll(listStore);
    }

}
