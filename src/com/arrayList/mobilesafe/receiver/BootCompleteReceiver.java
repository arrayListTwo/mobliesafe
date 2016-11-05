package com.arrayList.mobilesafe.receiver;

import com.arrayList.mobilesafe.utils.LogUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class BootCompleteReceiver extends BroadcastReceiver {

	private String simSerialNumber;

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences mPref = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		/*
		 * 获取手机SIM序列号
		 */
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		simSerialNumber = telephonyManager.getSimSerialNumber();
		if (!TextUtils.isEmpty(mPref.getString("sim", null))) {
			if (simSerialNumber.equals(mPref.getString("sim", null))) {
				LogUtils.d("MainActivity", "手机安全");
			}else {
				LogUtils.d("MainActivity", "手机SIM卡已更换");
			}
		}else {
			return;
		}
	}

}
