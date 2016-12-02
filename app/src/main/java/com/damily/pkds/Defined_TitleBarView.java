package com.damily.pkds;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by Dandan.Cao on 2016/12/1.
 */
public class Defined_TitleBarView extends RelativeLayout{
    private Button mLeftBtn;
    public Defined_TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.defined_title_bar,this);
        mLeftBtn= (Button) findViewById(R.id.left_btn);
        mLeftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }

}
