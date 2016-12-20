package com.damily.pkds.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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
import com.damily.pkds.adapter.NavForthAdapter;
import com.damily.pkds.common.URL;
import com.damily.pkds.entity.GiftInfo;
import com.damily.pkds.view.MainActivity;
import com.damily.pkds.view.Test;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Dandan.Cao on 2016/12/12.
 */
public class NavForthFragment extends Fragment  {
    private RecyclerView rv_navforth_recyclerView;
    private GiftInfo giftInfo;
    private NavForthAdapter mAdapter;
    GiftInfo.DataBean.ItemsBean itemsBean;
    List<GiftInfo.DataBean.ItemsBean> itemss;
    private MainActivity mainActivity;
    public List<GiftInfo.DataBean.ItemsBean> datas;
    private static final String PARAM_GIFT="com.damily.pkds.entity.GiftInfo";
    private static final String TAG = "NavForthFragment";
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
        View view = inflater.inflate(R.layout.navigation_forth, null);
        initView(view);
        initData();
    /*    new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(5*1000);
                    } catch (InterruptedException e) {
                       mHandler.sendMessage(mHandler.obtainMessage());
                    }
                }
            }
        }).start();*/
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

    }
   /* Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            refresh();
        }


    };
    private void refresh() {
        mAdapter.notifyDataSetChanged();
    }*/
    private void initView(View view) {
        rv_navforth_recyclerView = (RecyclerView) view.findViewById(R.id.rv_navforth_recyclerView);
      // rv_navforth_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StaggeredGridLayoutManager sgGridLayoutManager=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        rv_navforth_recyclerView.setLayoutManager(sgGridLayoutManager);
        rv_navforth_recyclerView.setHasFixedSize(true);
        TabLayout tabLayout=mainActivity.getTabLayout();
        RadioGroup main_tab_group= (RadioGroup) mainActivity.findViewById(R.id.main_tab_group);
        tabLayout.setVisibility(View.GONE);
        main_tab_group.setVisibility(View.GONE);
    }
    private void initData() {
        String url = URL.GIFT;
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                    if ((result != null) & (isNetworkConnected(getContext()))) {
                        Gson gson = new Gson();
                        giftInfo = gson.fromJson(result, GiftInfo.class);
                        datas=giftInfo.getData().getItems();
                        mAdapter = new NavForthAdapter(datas, getContext());
                        mAdapter.setOnRecycleViewListener(new NavForthAdapter.OnRecycleViewListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Log.i(TAG, giftInfo.getData().getItems().get(position).getData().getPurchase_url());
                                String url = giftInfo.getData().getItems().get(position).getData().getPurchase_url();
                                Intent intent=new Intent(getActivity(),Test.class);
                                intent.putExtra(PARAM_GIFT,url);
                                startActivity(intent);
                            }
                        });
                        rv_navforth_recyclerView.setAdapter(mAdapter);

                     mAdapter.notifyDataSetChanged();

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
       /* {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("APPKEY", "123456789");

                return params;
            }
        };*/
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

}
