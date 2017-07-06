package com.wz.fuel.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MineListAdapter extends ArrayAdapter<String> {

    private Context mContext;

    public MineListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
    }
}
