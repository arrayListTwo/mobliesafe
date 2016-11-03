package com.arrayList.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;

import com.arrayList.mobilesafe.R;

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
