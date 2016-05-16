package com.bluewave.reservation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.model.User;
import com.bluewave.reservation.model.WaitingInfo;

/**
 * Created by Developer on 2016-05-17.
 */
public class WaitingAdapter extends ArrayAdapter<WaitingInfo> {
    LayoutInflater inflater;
    int res;
    public WaitingAdapter(Context context, int resource) {
        super(context, resource);
        inflater = LayoutInflater.from(context);
        res = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            convertView = inflater.inflate(res, parent, false);
            holder = new ViewHolder();
            holder.tvInfo = (TextView) convertView.findViewById(R.id.tv_info);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        WaitingInfo info = getItem(position);

        holder.tvInfo.setText(String.format("%d 번째 손님 %s",info.wait_num, info.user_name));

        return convertView;
    }

    class ViewHolder
    {
        TextView tvInfo;
    }
}
