package com.damily.pkds;

import android.app.Activity;
import android.app.Application;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

import cn.smssdk.SMSSDK;

public class MyApplication extends Application {

	private List<Activity> activityList=new ArrayList<Activity>();
	private static MyApplication instance;
		public MyApplication() {
		}
		public static MyApplication getInstance(){
			if (null==instance) {
				instance=new MyApplication();
			}
			return instance;
			
		}
	public void addActivity(Activity activity){
			activityList.add(activity);
	}
	public void exit(){
		System.exit(0);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SMSSDK.initSDK(this, "19414e46903d1", "67286a76c0ebf8247bc4ab0fec2e183e");
		ZXingLibrary.initDisplayOpinion(this);
	}

}
