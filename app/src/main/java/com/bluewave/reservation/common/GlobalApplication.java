package com.bluewave.reservation.common;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.bluewave.reservation.model.UserPref;
import com.navercorp.volleyextensions.volleyer.Volleyer;
import com.navercorp.volleyextensions.volleyer.factory.DefaultRequestQueueFactory;

/**
 * Created by Developer on 2016-05-12.
 */
public class GlobalApplication extends Application {

    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        initVolley();
        initVolleyer();
        UserPref.init(this);
    }

    /**
     * Http 통신용 라이브러리인 Volley 초기화 부분
     */
    private void initVolley() {
        requestQueue = DefaultRequestQueueFactory.create(this);
        requestQueue.start();
    }

    private void initVolleyer() {
        Volleyer.volleyer(requestQueue).settings().setAsDefault().done();
    }
}
