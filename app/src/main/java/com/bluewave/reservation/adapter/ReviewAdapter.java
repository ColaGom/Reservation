package com.bluewave.reservation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.common.Const;
import com.bluewave.reservation.model.Review;
import com.bluewave.reservation.model.Store;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Developer on 2016-05-15.
 */
public class ReviewAdapter extends ArrayAdapter<Review> {
    private LayoutInflater inflater;
    private int res;

    public ReviewAdapter(Context context, int resource) {
        super(context, resource);
        inflater = LayoutInflater.from(context);
        res = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(res, parent, false);
            holder.bind(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Review data = getItem(position);

        holder.tvName.setText(data.user_name);
        holder.tvDate.setText(data.insert_date + " " + "평점 : " + data.raiting);
        holder.tvContent.setText(data.content);

        return convertView;
    }

    class ViewHolder
    {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_content)
        TextView tvContent;

        void bind(View rootView)
        {
            ButterKnife.bind(this, rootView);
        }
    }
}
