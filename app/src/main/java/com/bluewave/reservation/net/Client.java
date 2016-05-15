package com.bluewave.reservation.net;

/**
 * Created by Developer on 2016-05-14.
 */
public class Client {

    protected final static String NAME_TAG = "tag";

    public interface Handler
    {
        void onSuccess(Object object);
        void onFail();
    }
}
