package com.arrayList.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arrayList.mobilesafe.R;

/**
 * 第二个设置向导也
 * @author arrayList
 *
 */
public class SetupTwoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_two);
	}
	
	/**
	 * 跳转到下一个界面
	 * @param v view对象
	 */
	public void next(View v){
		startActivity(new Intent(this, SetupThreeActivity.class));
		finish();
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
