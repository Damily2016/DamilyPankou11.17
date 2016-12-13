package com.damily.pkds.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.damily.pkds.R;
import com.damily.pkds.utils.Code;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.smssdk.SMSSDK;

/**
 * Created by Dandan.Cao on 2016/11/8.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private Button  btn_login, imgbtn_qq, imgbtn_wx, vc_refresh;
    private EditText vc_code;
    private ImageView vc_image;
    private String getCode=null;  //获取验证码值
    private TextView title_tv;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.login);
        SMSSDK.initSDK(this, "19414e46903d1", "67286a76c0ebf8247bc4ab0fec2e183e");
        initView();
    }

    private void initView() {
        title_tv= (TextView) findViewById(R.id.title_tv);//自定义view
        title_tv.setText("Login");//自定义view
        imgbtn_wx = (Button) findViewById(R.id.imgbtn_wx);
        imgbtn_qq = (Button) findViewById(R.id.imgbtn_qq);
        btn_login = (Button) findViewById(R.id.btn_login);
        vc_refresh = (Button) findViewById(R.id.vc_refresh);
        vc_image = (ImageView) findViewById(R.id.vc_image);
        vc_image.setImageBitmap(Code.getInstance().getBitmap());
        getCode=Code.getInstance().getCode();
        vc_code = (EditText) findViewById(R.id.vc_code);
        imgbtn_wx.setOnClickListener(this);
        imgbtn_qq.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        vc_refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vc_refresh:
                vc_image.setImageBitmap(Code.getInstance().getBitmap());
                getCode=Code.getInstance().getCode();  //获取显示的验证码
                break;

            case R.id.btn_login:
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imgbtn_wx:
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imgbtn_qq:
               // Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                authorize(qq);
                break;
        }
    }
    private void authorize(Platform plat) {
        if (plat == null) {
          //  popupOthers();
            return;
        }
//判断指定平台是否已经完成授权
        if(plat.isAuthValid()) {
            String userId = plat.getDb().getUserId();
            if (userId != null) {
              //  UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                return;
                //login(plat.getName(), userId, null);

            }
        }
        //plat.setPlatformActionListener(this);
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(true);
        //获取用户资料
        plat.showUser(null);
    }
}
