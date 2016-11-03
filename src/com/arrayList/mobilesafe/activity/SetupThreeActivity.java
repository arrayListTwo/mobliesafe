package com.arrayList.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;

import com.arrayList.mobilesafe.R;

/**
 * 第三个设置向导页
 * @author arrayList
 *
 */
public class SetupThreeActivity extends BaseSetupActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_three);
	}

	@Override
	public void showPreviousPage() {
		startActivity(new Intent(this, SetupTwoActivity.class));
		finish();
		//两个界面切换的动画
		overridePendingTransition(R.anim.tran_previous_in, R.anim.tran_previous_out); //进入动画和退出动画
	}

	@Override
	public void showNextPage() {
		startActivity(new Intent(this, SetupFourActivity.class));
		finish();
		//两个界面切换的动画
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out); //进入动画和退出动画
	}
	
}
