package com.damily.pkds.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.damily.pkds.R;
import com.damily.pkds.adapter.HomeAdapter;
import com.damily.pkds.common.URL;
import com.damily.pkds.entity.DeviceInfo;
import com.damily.pkds.view.MainActivity;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Dandan.Cao on 2016/12/12.
 */
public class NavThirdFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout rf_layout;
    private RecyclerView rv_recyclerView;
    private DeviceInfo deviceInfo;
    private List<DeviceInfo.MessageBean.RecordsBean> records;
    private HomeAdapter mAdapter;
    private MainActivity mainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity){
            mainActivity= (MainActivity) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_third, null);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        rf_layout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        rf_layout.setOnRefreshListener(this);
        rv_recyclerView = (RecyclerView) view.findViewById(R.id.rv_recyclerView);
        rv_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TabLayout tabLayout=mainActivity.getTabLayout();
        RadioGroup main_tab_group= (RadioGroup) mainActivity.findViewById(R.id.main_tab_group);
        tabLayout.setVisibility(View.GONE);
        main_tab_group.setVisibility(View.GONE);
    }

    private void initData() {
        String url = URL.DEVICEFIX;
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                    if ((result != null) & (isNetworkConnected(getContext()))) {
                        Gson gson = new Gson();
                        deviceInfo = gson.fromJson(result, DeviceInfo.class);
                        records = deviceInfo.getMessage().getRecords();
                        mAdapter = new HomeAdapter(records, getContext());
                        rv_recyclerView.setAdapter(mAdapter);
                        rf_layout.setRefreshing(false);
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError instanceof NoConnectionError) {
                    Toast.makeText(getActivity(),"当前网络不可连接，请检查网络设置",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRequestQueue.add(request);

    }
    private boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    @Override
    public void onRefresh() {
        initData();
    }
}
