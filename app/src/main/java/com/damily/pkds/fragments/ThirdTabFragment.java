package com.damily.pkds.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.damily.pkds.R;
import com.damily.pkds.view.LoginActivity;
import com.damily.pkds.view.MainActivity;
import com.damily.pkds.view.RegisterActivity;

/**
 * Created by Dandan.Cao on 2016/11/7.
 */
public class ThirdTabFragment extends Fragment implements View.OnClickListener {
    private Button btn_login,btn_register;
    private MainActivity mainActivity;
    private View view;
    private Button left_btn;
    private TextView title_tv;
    private static final String TAG = "ThirdTabFragment";
    public void onAttach(Activity activity) {
        if (activity instanceof MainActivity){
            mainActivity= (MainActivity) activity;
        }
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity.getSupportActionBar().hide();
        Log.i(TAG, "ThirdTabFragment:------------ ");
       view =inflater.inflate(R.layout.activity_thirdtab,null);
        TabLayout tabLayout=mainActivity.getTabLayout();
        tabLayout.setVisibility(View.GONE);
        left_btn= (Button) view.findViewById(R.id.left_btn);          //自定义view的
        left_btn.setVisibility(View.GONE);                                //自定义view的
        title_tv= (TextView) view.findViewById(R.id.title_tv);//自定义view
        title_tv.setText("Me");//自定义view
        initView();
        return view;
    }

    private void initView() {
        btn_login= (Button) view.findViewById(R.id.btn_login);
        btn_register= (Button) view.findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                Intent loginIntent=new Intent(getActivity(),LoginActivity.class);
                startActivity(loginIntent);

                break;
            case R.id.btn_register:
               Intent registerIntent=new Intent(getActivity(),RegisterActivity.class);
                startActivity(registerIntent);

                break;
        }
    }
}
