package com.arrayList.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.arrayList.mobilesafe.R;

/**
 * 第四个设置向导页
 * @author arrayList
 *
 */
public class SetupFourActivity extends BaseSetupActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_four);
	}

	@Override
	public void showPreviousPage() {
		startActivity(new Intent(this, SetupOneActivity.class));
		finish();
		//两个界面切换的动画
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out); //进入动画和退出动画
	}

	@Override
	public void showNextPage() {
		startActivity(new Intent(this, LostFindActivity.class));
		finish();
		//更改配置文件
		mPref.edit().putBoolean("configed", true).commit();
		//两个界面切换的动画
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out); //进入动画和退出动画
	}
		
}
