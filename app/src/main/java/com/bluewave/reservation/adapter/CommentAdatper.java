package com.bluewave.reservation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bluewave.reservation.R;
import com.bluewave.reservation.model.Comment;

/**
 * Created by Developer on 2016-05-18.
 */
public class CommentAdatper extends ArrayAdapter<Comment> {

    private LayoutInflater inflater;
    private int res;

    public CommentAdatper(Context context, int resource) {
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
            holder.tvComment = (TextView) convertView.findViewById(R.id.tv_comment);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Comment comment = getItem(position);
        holder.tvComment.setText(String.format("%s(%d번) : %s",comment.user_name, comment.wait_num, comment.content));

        return convertView;
    }

    class ViewHolder
    {
        TextView tvComment;
    }
}
