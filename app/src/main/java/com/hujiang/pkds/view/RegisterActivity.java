package com.hujiang.pkds.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.hujiang.pkds.R;

/**
 * Created by Dandan.Cao on 2016/11/8.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button back_register,register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        back_register = (Button) findViewById(R.id.back_register);//exit register page
        register_btn = (Button) findViewById(R.id.register_btn);//register
        back_register.setOnClickListener(this);
        register_btn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_register:
                this.finish();
            break;
            case R.id.register_btn:

                break;
        }
    }
}