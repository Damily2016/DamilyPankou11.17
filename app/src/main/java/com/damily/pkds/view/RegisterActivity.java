package com.damily.pkds.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.damily.pkds.R;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
/**
 * Created by Dandan.Cao on 2016/11/24.
 */
public class RegisterActivity extends Activity {
    private static final int CODE_ING = 1;   //已发送，倒计时
    private static final int CODE_REPEAT = 2;  //重新发送
    private static final int SMSDDK_HANDLER = 3;  //短信回调
    private int TIME = 60;//倒计时60s
    private EditText userPhoneText, userPasswordText, userNameText, codeText;
    private TextView loginView;
    private Button registerButton, getCodeButton,back_register;
    private EventHandler eventHandler;
    private String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        initView();//界面初始化
        initSDK();//短信初始化
    }

    //初始化界面
    void initView() {
        back_register = (Button) findViewById(R.id.back_register);//exit register page
        userPhoneText = (EditText) findViewById(R.id.user_phone_input);
        userPasswordText = (EditText) findViewById(R.id.user_password_input);
        registerButton = (Button) findViewById(R.id.register_button);
        getCodeButton = (Button) findViewById(R.id.get_code_button);
        registerButton.setOnClickListener(new OnClickListener());
        getCodeButton.setOnClickListener(new OnClickListener());
        back_register.setOnClickListener(new OnClickListener());
        codeText = (EditText) findViewById(R.id.code_view);
    }
    //初始化SMSSDK
    private void initSDK() {
        SMSSDK.initSDK(this, "19414e46903d1", "67286a76c0ebf8247bc4ab0fec2e183e");
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                msg.what = SMSDDK_HANDLER;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    //监听函数
    private class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            userPhone = userPhoneText.getText().toString();
            switch (v.getId()) {
                case R.id.get_code_button://获取验证码
                    if (judgePhoneNums(userPhone)) {
                        sendMsg();
                    }
                    break;

                case R.id.register_button://注册
                    SMSSDK.submitVerificationCode("86", userPhone, codeText.getText().toString());//对验证码进行验证->回调函数
                    break;
                case R.id.back_register:
                    RegisterActivity.this.finish();
                    break;
            }
        }


        private void sendMsg() {
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("发送短信")
                    .setMessage("我们将把验证码发送到以下号码:\n" + "+86:" + userPhone)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SMSSDK.getVerificationCode("86", userPhone);
                            getCodeButton.setClickable(false);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 60; i > 0; i--) {
                                        handler.sendEmptyMessage(CODE_ING);
                                        if (i <= 0) {
                                            break;
                                        }
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    handler.sendEmptyMessage(CODE_REPEAT);
                                }
                            }).start();
                        }
                    })
                    .create()
                    .show();
        }

        private boolean judgePhoneNums(String userPhone) {

            if (isMatchLength(userPhone, 11)

                    && isMobileNO(userPhone)) {

                return true;

            }

            Toast.makeText(RegisterActivity.this, "手机号码输入有误！", Toast.LENGTH_SHORT).show();

            return false;
        }

        public boolean isMatchLength(String str, int length) {

            if (str.isEmpty()) {

                return false;

            } else {

                return str.length() == length ? true : false;

            }

        }

        public boolean isMobileNO(String mobileNums) {

            String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。

            if (TextUtils.isEmpty(mobileNums))

                return false;

            else

                return mobileNums.matches(telRegex);

        }


    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_ING://已发送,倒计时
                    getCodeButton.setText("重新发送(" + --TIME + "s)");
                    break;
                case CODE_REPEAT://重新发送
                    getCodeButton.setText("获取验证码");
                    getCodeButton.setClickable(true);
                    break;
                case SMSDDK_HANDLER:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    //回调完成
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //验证码验证成功
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            Toast.makeText(RegisterActivity.this, "验证成功", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(RegisterActivity.this,

                                    TestActivity.class);

                            startActivity(intent);

                           /* if (check())//其他合法性的检测
                            {
                                UserModel user = new UserModel();
                                user.setUserId(MyUUID.getUUID());  //id
                                user.setUserPhone(userPhone);
                                user.setUserPassword(MD5.md5(userPassword)); //md5加密
                                user.setUserGender(gender);   //性别
                                user.setUserName(userName);
                                user.setUserBirthday("19920401");   //暂时为空
                                //user.setUserIdCard(userIdCard);
                                //user.setUserImage("");    //暂时为空
                                //注册->服务器
                                UserController.userRegister(user, handler);
                            }*/

                        }
                        //已发送验证码
                        else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            Toast.makeText(getApplicationContext(), "验证码已经发送",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    if (result == SMSSDK.RESULT_ERROR) {
                        try {
                            Throwable throwable = (Throwable) data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码
                            if (status > 0 && !TextUtils.isEmpty(des)) {
                                Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            //do something
                        }
                    }
                    break;
              /*  case R.id.register_status:
                    String result_code = msg.getData().getString("result").toString();
                    if("1".equals(result_code))
                    {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        intent.putExtra("userPhone", userPhone);
                        RegisterActivity.this.setResult(RESULE_CODE, intent);
                        //startActivity(intent);
                        finish();
                    }else
                    {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                    }
                    break;*/
             /*   case R.id.check_phone_exist://手机号是否已存在
                    String result_code_2 = msg.getData().getString("result").toString();
                    if("1".equals(result_code_2))
                    {
                        errPhoneText.setText("手机号码已经注册，请换用其他号码");
                        resultMap.put("phone", false);
                    }
                    else
                    {
                        errPhoneText.setText("");
                        resultMap.put("phone", true);
                    }
                    break;*/
            }
        }

    };


    @Override

    protected void onDestroy() {

        SMSSDK.unregisterAllEventHandler();

        super.onDestroy();

    }
}