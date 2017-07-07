package com.wz.fuel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wz.fuel.R;
import com.wz.fuel.menu.MenuDataBean;

import java.io.File;
import java.util.List;

public class MineListAdapter extends BaseAdapter {

    private Context mContext;
    private List<MenuDataBean> mMenuList;
    private int mItemLayoutId;

    public MineListAdapter(Context context, List<MenuDataBean> menuList, int itemLayoutId) {
        mContext = context;
        mMenuList = menuList;
        mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mMenuList == null ? 0 : mMenuList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMenuList == null ? null : mMenuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuDataBean menuDataBean = mMenuList.get(position);
        if (menuDataBean != null) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.layout = (RelativeLayout) convertView.findViewById(R.id.rl_item_layout);
                viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String imgPath = MenuManager.getInstance(mContext).getResCacheDir() + File.separator + menuDataBean.getImgName() + ".png";
            viewHolder.tvTitle.setText(menuDataBean.getMenuName());
            Glide.with(mContext).load(new File(imgPath)).error(R.drawable.ic_default).into(viewHolder.ivIcon);
            return convertView;
        }
        return null;
    }


    class ViewHolder {
        RelativeLayout layout;
        ImageView ivIcon;
        TextView tvTitle;
    }
}
