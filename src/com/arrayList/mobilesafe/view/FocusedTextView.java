package com.arrayList.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 获取焦点的TextView
 * @author arrayList
 *
 */
public class FocusedTextView extends TextView {

	//用代码new对象时，使用此构造方法
	public FocusedTextView(Context context) {
		super(context);
	}
	
	//有属性时使用此构造方法
	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//有style样式的话，使用此构造方法
	public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/** 
	 * 表示有没有获取到焦点
	 * 	跑马灯要运行，首先会调用此方法判断TextView是否有焦点，若为true，跑马灯才会运行
	 * 		故不管TextView组件是否获取焦点，都强制返回true
	 */
	@Override
	public boolean isFocused() {
		return true;
	}
	
}
