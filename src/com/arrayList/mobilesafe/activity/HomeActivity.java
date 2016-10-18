package com.arrayList.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.arrayList.mobilesafe.R;

/**
 * 安全卫士软件展示九大功能的主界面
 * @author arrayList
 *
 */
public class HomeActivity extends Activity {

	/**
	 * 九宫格显示九大功能组件
	 */
	private GridView gvHome;
	
	/**
	 * 九大功能字符串数组
	 */
	private String[] mItems = new String[]{"手机防盗","通信卫士","软件管理","进程管理",
			"流量统计","手机杀毒","缓存工具","高级管理","设置中心"};
	
	/**
	 * 九大功能对应的图标
	 */
	private int[] mPics = new int[]{R.drawable.home_safe, R.drawable.home_callmsgsafe,
			R.drawable.home_apps, R.drawable.home_taskmanager, R.drawable.home_netmanager,
			R.drawable.home_trojan, R.drawable.home_sysoptimize, R.drawable.home_tools,
			R.drawable.home_settings};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gvHome = (GridView) findViewById(R.id.gv_home);
		//设置GridView的适配器
		gvHome.setAdapter(new HomeAdapter());
	}
	
	/**
	 * GridView的适配器类
	 * @author arrayList
	 *
	 */
	class HomeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mItems.length;
		}

		@Override
		public Object getItem(int position) {
			return mItems[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			//动态加载布局文件
			View view = View.inflate(HomeActivity.this, R.layout.home_list_item, null);
			ImageView ivItem = (ImageView) view.findViewById(R.id.iv_item);
			TextView tvItem = (TextView) view.findViewById(R.id.tv_item);
			ivItem.setImageResource(mPics[position]);
			tvItem.setText(mItems[position]);
			//返回视图View对象
			return view;
		}
		
	}

}
