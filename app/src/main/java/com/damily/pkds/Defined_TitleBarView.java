package com.damily.pkds;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by Dandan.Cao on 2016/12/1.
 */
public class Defined_TitleBarView extends RelativeLayout{
    private Button mLeftBtn,mHomeBtn;
    private DrawerLayout mDrawerLayout;
    public Defined_TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.defined_title_bar,this);
        mLeftBtn= (Button) findViewById(R.id.left_btn);
        mHomeBtn= (Button) findViewById(R.id.home_btn);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        mLeftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
        mHomeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            //    mDrawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }

}
