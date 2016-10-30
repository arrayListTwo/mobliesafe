package com.arrayList.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.arrayList.mobilesafe.R;

/**
 * 设置中心
 * @author lei
 *
 */
public class SettingActivity extends Activity {
	
	/**
	 * 设置升级
	 */
	private SettingItemView sivUpdate;
	
	/**
	 * SharedPreferences对象，存储配置数据
	 */
	private SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
//		sivUpdate.setTitle("自动更新设置");
		boolean sutoUpdate = mPref.getBoolean("auto_update", true);
		if (sutoUpdate) {
//			sivUpdate.setDesc("自动更新已开启");
			sivUpdate.setCheckedStatus(true);
		}else {
//			sivUpdate.setDesc("自动更新已关闭");
			sivUpdate.setCheckedStatus(false);
		}
		//设置监听
		sivUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//判断当前的勾选状态
				if (sivUpdate.isChecked()) {
					//设置不勾选
					sivUpdate.setCheckedStatus(false);
//					sivUpdate.setDesc("自动更新已关闭");
					//更新SharedPreferences对象
					mPref.edit().putBoolean("auto_update", false).commit();
				}else {
					sivUpdate.setCheckedStatus(true);
//					sivUpdate.setDesc("自动更新已开启");
					mPref.edit().putBoolean("auto_update", true).commit();
				}
			}
		});
		
	}
	
}
