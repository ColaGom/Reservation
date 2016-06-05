package com.bluewave.reservation.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Developer on 2016-05-14.
 */
public class Store implements Serializable {

    public Integer uid;
    public String id;
    public String name;
    public String logo_url;
    public String location;
    public String latlng;
    public String opening_hour;
    public String holiday;
    public String contact;
    public String review_count;
    public String raiting_avg;
    public int service_time;
    public int table_num;
    public int incomming_hour;

    public int getWaitMinute()
    {
        return incomming_hour / (service_time * table_num) * ((service_time * table_num)-incomming_hour);
    }

    public LatLng getLatlng()
    {
        String[] arr = latlng.split(",");
        if(arr.length != 2)
            return null;

        return new LatLng(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
    }
}
