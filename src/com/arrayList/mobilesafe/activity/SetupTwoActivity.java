package com.arrayList.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.arrayList.mobilesafe.R;
import com.arrayList.mobilesafe.utils.LogUtils;

/**
 * 第二个设置向导也
 * @author arrayList
 *
 */
public class SetupTwoActivity extends BaseSetupActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_two);
		
		final SettingItemView siv_sim = (SettingItemView) findViewById(R.id.siv_sim);
		String sim = mPref.getString("sim", null);
		if (!TextUtils.isEmpty(sim)) {
			siv_sim.setCheckedStatus(true);
		}else {
			siv_sim.setCheckedStatus(false);
		}
		//设置自定义控件可点击
		siv_sim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (siv_sim.isChecked()) {
					siv_sim.setCheckedStatus(false);
					mPref.edit().remove("sim").commit();
				}else {
					siv_sim.setCheckedStatus(true);
					/*
					 * 获取手机卡唯一性标识，SIM卡序列号
					 */
					TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					String simSerialNumber = telephonyManager.getSimSerialNumber();
					mPref.edit().putString("sim", simSerialNumber).commit();
					LogUtils.d("MainActivity", simSerialNumber);
				}
			}
		});
	}
	
	/**
	 * 展示上一页
	 */
	@Override
	public void showPreviousPage(){
		startActivity(new Intent(this, SetupOneActivity.class));
		finish();
		//两个界面切换的动画
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out); //进入动画和退出动画
	}
	
	/**
	 * 展示下一页
	 */
	@Override
	public void showNextPage(){
		startActivity(new Intent(this, SetupThreeActivity.class));
		finish();
		//两个界面切换的动画
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out); //进入动画和退出动画
	}
	
}
