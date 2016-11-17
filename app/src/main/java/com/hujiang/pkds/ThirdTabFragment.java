package com.hujiang.pkds;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Dandan.Cao on 2016/11/7.
 */
public class ThirdTabFragment extends Fragment {
    private MainActivity mainActivity;
    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof MainActivity){
            mainActivity= (MainActivity) activity;
        }
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_more,null);
        TabLayout tabLayout=mainActivity.getTabLayout();
        tabLayout.setVisibility(View.GONE);
        return view;
    }
}
