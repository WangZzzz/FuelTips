package com.wz.fuel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wz.fuel.R;
import com.wz.fuel.adapter.MenuManager;
import com.wz.fuel.adapter.MineListAdapter;
import com.wz.fuel.menu.MenuDataBean;
import com.wz.fuel.menu.MenuJump;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MineFragment extends BaseFragment {
    @BindView(R.id.lv_settings)
    ListView mLvSettings;

    private List<MenuDataBean> mMenuList;
    private MineListAdapter mAdapter;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void initData() {
        mMenuList = new ArrayList<>();
        List<MenuDataBean> tmpMenus = MenuManager.getInstance(mContext).getMenuList();
        if (tmpMenus != null && tmpMenus.size() > 0) {
            for (MenuDataBean menuDataBean : tmpMenus) {
                if ("1".equals(menuDataBean.getIsActive())) {
                    mMenuList.add(menuDataBean);
                }
            }
        }
        mAdapter = new MineListAdapter(mContext, mMenuList, R.layout.mine_list_item_layout);
        mLvSettings.setAdapter(mAdapter);
        mLvSettings.setOnItemClickListener(mOnItemClickListener);
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MenuDataBean menu = mMenuList.get(position);
            if (menu != null) {
                MenuJump.jump(mContext, menu);
            }
        }
    };
}
