package com.wz.fuel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wz.fuel.R;
import com.wz.fuel.mvp.bean.FuelRecordBean;
import com.wz.util.TimeUtil;
import com.wz.util.ToastMsgUtil;
import com.wz.view.OnItemClickListener;
import com.wz.view.OnItemLongClickListener;

import java.text.SimpleDateFormat;
import java.util.List;

public class FuelRecordAdapter extends RecyclerView.Adapter<FuelRecordAdapter.MyViewHolder> {

    private static final String TAG = FuelRecordAdapter.class.getSimpleName();

    private Context mContext;
    private List<FuelRecordBean> mRecords;

    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemClickListener mOnItemClickListener;

    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;

    //尾标
    private View mFooterView;

    public FuelRecordAdapter(Context context, List<FuelRecordBean> records) {
        mContext = context;
        mRecords = records;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mRecords.size() && mFooterView != null) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER && mFooterView != null) {
            return new MyViewHolder(mFooterView);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_fuel_record, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {

        } else {
            if (mRecords != null) {
                FuelRecordBean fuelRecord = mRecords.get(position);
                if (fuelRecord != null) {
                    holder.mTvDate.setText(TimeUtil.millis2String(fuelRecord.fuelDate, new SimpleDateFormat("yyyy-MM-dd")));
                    holder.mTvLitres.setText(fuelRecord.litres + "升");
                    holder.mTvTotalPrice.setText(fuelRecord.totalPrice + "元");
                    holder.mTvFuelType.setText(fuelRecord.fuelTypeStr);
                    holder.mTvCurrentMileage.setText(fuelRecord.curentMileage + "公里");
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(holder.itemView, position);
                            }
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (mOnItemLongClickListener != null) {
                                return mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                            }
                            return false;
                        }
                    });
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mFooterView != null) {
            return mRecords == null ? 1 : mRecords.size() + 1;
        } else {
            return mRecords == null ? 0 : mRecords.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTvDate;
        TextView mTvCurrentMileage;
        TextView mTvFuelType;
        TextView mTvTotalPrice;
        TextView mTvLitres;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView != mFooterView) {
                mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
                mTvFuelType = (TextView) itemView.findViewById(R.id.tv_fuel_type);
                mTvTotalPrice = (TextView) itemView.findViewById(R.id.tv_total_price);
                mTvLitres = (TextView) itemView.findViewById(R.id.tv_litres);
                mTvCurrentMileage = (TextView) itemView.findViewById(R.id.tv_current_mileage);

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        ToastMsgUtil.info(mContext, "删除--->", 0);
                        return true;
                    }
                });
            }
        }
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
