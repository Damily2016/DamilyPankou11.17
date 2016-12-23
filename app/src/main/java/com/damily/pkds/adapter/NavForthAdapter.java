package com.damily.pkds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.damily.pkds.R;
import com.damily.pkds.entity.GiftInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dandan.Cao on 2016/12/13.
 */
public class NavForthAdapter extends RecyclerView.Adapter<NavForthAdapter.MyViewHolder> {
    Context context;
    GiftInfo.DataBean.ItemsBean itemsBean;
    ArrayList<GiftInfo.DataBean.ItemsBean> items;
    //    RequestManager requestManager=Glide.with(context);
    public interface OnRecycleViewListener {
        void onItemClick(View view, int position);
        //void onItemLongClick(View view, int position);
    }

    private OnRecycleViewListener onRecycleViewListener;

    public void setOnRecycleViewListener(OnRecycleViewListener mOnItemClickListener) {
        this.onRecycleViewListener = mOnItemClickListener;
    }

    public NavForthAdapter(List<GiftInfo.DataBean.ItemsBean> items, Context context) {
        super();
        this.context = context;
        this.items = (ArrayList<GiftInfo.DataBean.ItemsBean>) items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.navforth_adapter, parent, false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        itemsBean = items.get(position);
        holder.tv_name.setText(itemsBean.getData().getName());
        holder.tv_description.setText(itemsBean.getData().getDescription());
        Glide.with(context).load(itemsBean.getData().getCover_image_url()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.cover_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                onRecycleViewListener.onItemClick(holder.itemView, pos);
            }
        });
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView cover_image;
        TextView tv_description;

        public MyViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            cover_image = (ImageView) view.findViewById(R.id.cover_image);
            tv_description = (TextView) view.findViewById(R.id.tv_description);
        }
    }
}
