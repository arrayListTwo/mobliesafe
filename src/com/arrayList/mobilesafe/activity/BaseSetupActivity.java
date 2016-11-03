package com.arrayList.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 设置引导页的基类，不展示界面故不需要在清单文件注册
 * @author arrayList
 *
 */
public abstract class BaseSetupActivity extends Activity {

	/**
	 * 手势识别器
	 */
	private GestureDetector mDetector;
	
	/**
	 * 配置文件对象
	 */
	public SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDetector = new GestureDetector(this, new SimpleOnGestureListener(){
			/**
			 * 监听手势滑动
			 * e1：表示滑动的起点
			 * e2：表示滑动的终点
			 * velocityX：表示水平速度
			 * velocityY：表示垂直速度
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				//RawX：坐标系是手机屏幕   X：坐标系是以触摸到的控件为坐标系
				if (Math.abs(e2.getRawY() - e1.getRawY()) > 100) {
					//纵向滑动幅度过大，屏幕不做变化
					Toast.makeText(BaseSetupActivity.this, "纵向幅度过大", Toast.LENGTH_SHORT).show();
				}else if (Math.abs(velocityX) < 100) {
					Toast.makeText(BaseSetupActivity.this, "滑动速度过慢", Toast.LENGTH_SHORT).show();
					//滑动速度过慢，屏幕不做变化
				}else if (e2.getRawX() - e1.getRawX() > 100) { //向右滑，上一页
					showPreviousPage();
				}else if (e1.getRawX() - e2.getRawX() > 100) { //向左滑，下一页
					showNextPage();
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
		mPref = getSharedPreferences("config", MODE_PRIVATE);
	}
	
	/**
	 * 展示上一页
	 */
	public abstract void showPreviousPage();
	
	/**
	 * 展示下一页
	 */
	public abstract void showNextPage();
	
	/**
	 * 跳转到下一个界面
	 * @param v view对象
	 */
	public void next(View v){
		showNextPage();
	}
	
	/**
	 * 跳转到下一个界面
	 * @param v view对象
	 */
	public void previous(View v){
		showPreviousPage();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);//委托手势识别器处理触摸事件
		return super.onTouchEvent(event);
	}
	
}
