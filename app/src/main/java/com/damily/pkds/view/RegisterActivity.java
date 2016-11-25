package com.damily.pkds.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.damily.pkds.R;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * Created by Dandan.Cao on 2016/11/8.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    private Button back_register, register_btn, btn_registerText;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        back_register = (Button) findViewById(R.id.back_register);//exit register page
        register_btn = (Button) findViewById(R.id.register_btn);//register
        btn_registerText = (Button) findViewById(R.id.btn_registerText);//register text
        back_register.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        btn_registerText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_register:
                this.finish();
                break;
            case R.id.register_btn:

                break;
            case R.id.btn_registerText:
                Log.i(TAG, "点击发送验证码控件: ------------------");
              //  SMSSDK.initSDK(this, "19414e46903d1", "67286a76c0ebf8247bc4ab0fec2e183e");
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler(){
                    @Override
                    public void afterEvent(int event, int result, Object data) {
//                        super.afterEvent(event, result, data);
                        if(result== SMSSDK.RESULT_COMPLETE){
                            @SuppressWarnings("unchecked")
                            HashMap<String,Object> maps= (HashMap<String, Object>) data;
                            String country= (String) maps.get("country");
                            String phone= (String) maps.get("phone");
                            registerUserInfo(country,phone);
                        }

                    }
                });
                registerPage.show(RegisterActivity.this);
                break;

        }
    }
    private void registerUserInfo(String country, String phone) {
        Random r=new Random();
        String uid=Math.abs(r.nextInt())+"";
        String nickname="damily";
       SMSSDK.submitUserInfo(uid,nickname,null,country,phone);

    }
}
