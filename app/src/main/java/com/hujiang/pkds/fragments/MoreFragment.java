package com.hujiang.pkds.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hujiang.pkds.R;
import com.hujiang.pkds.view.LoginActivity;
import com.hujiang.pkds.view.RegisterActivity;

public class MoreFragment extends Fragment implements View.OnClickListener {
	private Button login_button,back_button;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		System.out.println("MeFragment");
		View view=inflater.inflate(R.layout.main_more,null);
		login_button= (Button) view.findViewById(R.id.login_button);
		back_button= (Button) view.findViewById(R.id.back_button);
		login_button.setOnClickListener(this);
		back_button.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.login_button:
				Intent loginIintent=new Intent(getActivity(),LoginActivity.class);
				startActivity(loginIintent);

				break;
			case R.id.back_button:
				Intent registerIntent=new Intent(getActivity(),RegisterActivity.class);
				startActivity(registerIntent);

				break;
		}
	}
}
