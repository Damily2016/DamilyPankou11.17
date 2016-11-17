package com.hujiang.pkds.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.hujiang.pkds.R;

/**
 * Created by Dandan.Cao on 2016/11/8.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private Button back,register_btn,imgbtn_qq,imgbtn_wx;
   //  ImageButton imgbtn_qq,imgbtn_wx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        back = (Button) findViewById(R.id.back);
       /* imgbtn_wx = (ImageButton) findViewById(R.id.imgbtn_wx);
        imgbtn_qq = (ImageButton) findViewById(R.id.imgbtn_qq);*/
              imgbtn_wx = (Button) findViewById(R.id.imgbtn_wx);
        imgbtn_qq = (Button) findViewById(R.id.imgbtn_qq);
        register_btn = (Button) findViewById(R.id.register_btn);
        back.setOnClickListener(this);
        imgbtn_wx.setOnClickListener(this);
        imgbtn_qq.setOnClickListener(this);
        register_btn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
            break;
            case R.id.register_btn:
                Toast.makeText(this,"click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imgbtn_wx:
                Toast.makeText(this,"click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imgbtn_qq:
                Toast.makeText(this,"click", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
