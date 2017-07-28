package com.wz.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class LoadMoreOnScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = LoadMoreOnScrollListener.class.getSimpleName();

    private LinearLayoutManager mLinearLayoutManager;
    private int totalItemCount;

    //记录前一个totalItemCount
    private int previousTotal;
    private int visibleItemCount;

    //在屏幕可见的item中的第一个
    private int firstVisibleItem;

    //是否正在加载数据
    private boolean isLoading = false;

    public LoadMoreOnScrollListener(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
        previousTotal = mLinearLayoutManager.getItemCount();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //当前屏幕看到的item数量
        visibleItemCount = mLinearLayoutManager.getChildCount();
        //当前recyclerView中item的总数量
        totalItemCount = mLinearLayoutManager.getItemCount();
        //屏幕中第一个可见的item
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        if (totalItemCount > previousTotal) {
            //说明数据已经加载结束
            isLoading = false;
            previousTotal = totalItemCount;
        }
        if (!isLoading && totalItemCount - visibleItemCount <= firstVisibleItem) {
            loadMoreData();
            isLoading = true;
        }
    }

    public abstract void loadMoreData();

    /**
     * 初始化，在添加新的数据时，刷新
     */
    public void init() {
        totalItemCount = 0;
        visibleItemCount = 0;
        previousTotal = 0;
        isLoading = false;
    }
}
