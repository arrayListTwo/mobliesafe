package com.arrayList.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

	/**
	 * SharedPreferences对象
	 */
	private SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		gvHome = (GridView) findViewById(R.id.gv_home);
		//设置GridView的适配器
		gvHome.setAdapter(new HomeAdapter());
		//GridView子组件设置点击事件
		gvHome.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				switch (position) {
				case 0:
					//手机防盗
					showPasswordDialog();
					break;
				case 8:
					//设置中心
					startActivity(new Intent(HomeActivity.this, SettingActivity.class));
					break;

				default:
					break;
				}
			}
		});
	}
	
	/**
	 * 密码弹窗
	 */
	protected void showPasswordDialog() {
		String savePassword = mPref.getString("password", null);
		if (!TextUtils.isEmpty(savePassword)) {
			showPasswordInputDialog();
		}else {
			//若没有设置过，弹出设置密码的弹窗
			showPasswordSetDialog();
		}
	}

	/**
	 * 输入密码弹窗
	 */
	private void showPasswordInputDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog alertDialog = builder.create();
		View view = View.inflate(this, R.layout.dialog_input_password, null);
		alertDialog.setView(view);
		final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
		Button btnOk = (Button) view.findViewById(R.id.btn_ok);
		Button btnCancle = (Button) view.findViewById(R.id.btn_cancle);
		//给确认按钮设置监听事件
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String password = etPassword.getText().toString();
				if (!TextUtils.isEmpty(password)) {
					String savePassword = mPref.getString("password", null);
					if (password.equals(savePassword)) {
						Toast.makeText(HomeActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
						alertDialog.dismiss();
					}else {
						Toast.makeText(HomeActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(HomeActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//给取消按钮设置监听事件
		btnCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//弹窗消失
				alertDialog.dismiss();
			}
		});
		alertDialog.show();
	}

	/**
	 * 设置密码的弹窗
	 */
	private void showPasswordSetDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog alertDialog = builder.create();
		View view = View.inflate(this, R.layout.dialog_set_password, null);
		alertDialog.setView(view);
		final EditText etPassword = (EditText) view.findViewById(R.id.et_password);
		final EditText etPasswordConfrim = (EditText) view.findViewById(R.id.et_password_confirm);
		Button btnOk = (Button) view.findViewById(R.id.btn_ok);
		Button btnCancle = (Button) view.findViewById(R.id.btn_cancle);
		//给确认按钮设置监听事件
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String password = etPassword.getText().toString();
				String passwordConfirm = etPasswordConfrim.getText().toString();
				if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm)) {
					if (password.equals(passwordConfirm)) {
						Toast.makeText(HomeActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
						//将密码保存至SharedPreferences对象中
						mPref.edit().putString("password", password).commit();
						alertDialog.dismiss();
					}else {
						Toast.makeText(HomeActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
					}
				}else {
					Toast.makeText(HomeActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//给取消按钮设置监听事件
		btnCancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//弹窗消失
				alertDialog.dismiss();
			}
		});
		alertDialog.show();
	}

	/**
	 * GridView的适配器类
	 * @author arrayList
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
