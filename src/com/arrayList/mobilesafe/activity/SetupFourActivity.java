package com.arrayList.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.arrayList.mobilesafe.R;

/**
 * 第四个设置向导页
 * @author arrayList
 *
 */
public class SetupFourActivity extends Activity {

	private SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_four);
		mPref = getSharedPreferences("config", MODE_PRIVATE);
	}
	
	/**
	 * 向导完成
	 * @param v view对象
	 */
	public void next(View v){
		startActivity(new Intent(this, LostFindActivity.class));
		finish();
		//更改配置文件
		mPref.edit().putBoolean("configed", true).commit();
	}
	
	/**
	 * 跳转到下一个界面
	 * @param v view对象
	 */
	public void previous(View v){
		startActivity(new Intent(this, SetupOneActivity.class));
		finish();
	}
		
}
