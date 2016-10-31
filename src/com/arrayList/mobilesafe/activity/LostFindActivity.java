package com.arrayList.mobilesafe.activity;

import com.arrayList.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * 手机防盗界面
 * @author lei  
 *
 */
public class LostFindActivity extends Activity {
	
	private SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		boolean configed = mPref.getBoolean("configed", false);
		//判断是否进入过设置向导
		if (configed) {
			setContentView(R.layout.activity_lost_find);
		}else {
			//跳转设置向导页
			startActivity(new Intent(this, SetupOneActivity.class));
			finish();
		}
		
	}
	
}
