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
import com.bluewave.reservation.model.Store;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Developer on 2016-05-15.
 */
public class StoreAdapter extends ArrayAdapter<Store> {
    private LayoutInflater inflater;
    private int res;
    public StoreAdapter(Context context, int resource) {
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

        Store data = getItem(position);

        if(!TextUtils.isEmpty(data.logo_url))
            Glide.with(getContext()).load(Const.URL_LOGO + data.logo_url).bitmapTransform(new CropCircleTransformation(getContext())).crossFade().into(holder.ivLogo);

        holder.tvTitle.setText(data.name);
        holder.tvInfo.setText(data.contact);

        return convertView;
    }

    class ViewHolder
    {
        @Bind(R.id.iv_logo)
        ImageView ivLogo;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_info)
        TextView tvInfo;

        void bind(View rootView)
        {
            ButterKnife.bind(this, rootView);
        }
    }
}
