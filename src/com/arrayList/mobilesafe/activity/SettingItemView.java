package com.arrayList.mobilesafe.activity;

import com.arrayList.mobilesafe.R;
import com.arrayList.mobilesafe.utils.LogUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 设置中心的自定义控件
 * @author lei
 *
 */
public class SettingItemView extends RelativeLayout {

	private TextView tv_title;
	
	private TextView tv_desc;

	private CheckBox cbStatus;

	/**
	 * 命名空间
	 */
	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.arrayList.mobilesafe";

	private String mTitle;

	private String mDescOn;

	private String mDescOff;

	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//根据属性名称获取属性值
		mTitle = attrs.getAttributeValue(NAMESPACE, "item_title");
		mDescOn = attrs.getAttributeValue(NAMESPACE, "desc_on");
		mDescOff = attrs.getAttributeValue(NAMESPACE, "desc_off");
		//初始化布局
		initView();
	}

	public SettingItemView(Context context) {
		super(context);
		initView();
	}
	
	/**
	 * 初始化布局
	 */
	private void initView(){
		//将自定义布局文件view_setting_item设置给当前的SettingItemView
		View.inflate(getContext(), R.layout.view_setting_item, this);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_desc = (TextView)findViewById(R.id.tv_desc);
		cbStatus = (CheckBox)findViewById(R.id.status);
		//设置标题
		setTitle(mTitle);
	}
	
	/**
	 * 设置设置子项标题
	 * @param title 子项标题
	 */
	public void setTitle(String title){
		tv_title.setText(title);
	}
	
	/**
	 * 设置子项描述
	 * @param desc 子项描述
	 */
	public void setDesc(String desc){
		tv_desc.setText(desc);
	}
	
	/**
	 * 返回勾选框的状态
	 * @return 勾选框状态
	 */
	public boolean isChecked(){
		return cbStatus.isChecked();
	}
	
	/**
	 * 设置勾选框状态
	 * @param status 勾选框状态
	 */
	public void setCheckedStatus(boolean check){
		cbStatus.setChecked(check);
		//根据选择的状态，更新文本显示
		if (check) {
			setDesc(mDescOn);
		}else {
			setDesc(mDescOff);
		}
	}
	
}
