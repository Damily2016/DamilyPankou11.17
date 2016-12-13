package com.damily.pkds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.damily.pkds.R;
import com.damily.pkds.entity.DeviceInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dandan.Cao on 2016/12/13.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    Context context;
    DeviceInfo.MessageBean.RecordsBean recordBean;
    ArrayList<DeviceInfo.MessageBean.RecordsBean> records;

    public HomeAdapter(List<DeviceInfo.MessageBean.RecordsBean> records, Context context) {
        super();
        this.context = context;
        this.records = (ArrayList<DeviceInfo.MessageBean.RecordsBean>) records;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.home_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        recordBean = records.get(position);
        holder.tv_name.setText(recordBean.getDeviceRegId());
        holder.tv_datetime.setText(recordBean.getSubmitTime());
        holder.tv_type.setText(recordBean.getTypeName());
        holder.tv_user.setText(recordBean.getSubmitUser());
        holder.itemView.setTag(position);
    }
    @Override
    public int getItemCount() {
        return records.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_datetime, tv_type, tv_user;
        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_datetime = (TextView) view.findViewById(R.id.tv_datetime);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            tv_user = (TextView) view.findViewById(R.id.tv_user);
        }
    }
}
