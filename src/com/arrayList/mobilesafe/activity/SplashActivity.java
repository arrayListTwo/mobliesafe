package com.arrayList.mobilesafe.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arrayList.mobilesafe.R;
import com.arrayList.mobilesafe.utils.LogUtils;
import com.arrayList.mobilesafe.utils.StreamUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;


/**
 * Splash界面
 * @author arrayList
 *
 */
public class SplashActivity extends Activity {
	
	/**
	 * 本地获取的软件版本名称
	 */
	private TextView tvVersion;
	
	/**
	 * 下载进度
	 */
	private TextView tvProgress;
	
	/**
	 * 网络获取的最新软件版本的版本名称
	 */
	private String mVersionName;
	
	/**
	 * 网络获取的最新软件版本的版本号
	 */
	private int mVersionCode;

	/**
	 * 网络获取的最新软件版本的版本描述
	 */
	private String mDesc;
	
	/**
	 * 网络获取的最新软件版本的下载地址
	 */
	private String mDownloadUrl;
	
	/**
	 * 弹出是否升级选择框的消息值
	 */
	protected static final int CODE_UPDATE_DIALOT = 0;

	/**
	 * URL错误时消息值
	 */
	protected static final int CODE_URL_ERROR = 1;
	
	/**
	 * 网络异常时消息值
	 */
	protected static final int CODE_NET_ERROR = 2;
	
	/**
	 * JSON错误时消息值
	 */
	protected static final int CODE_JSON_ERROR = 3;
	
	/**
	 * 进入主界面的消息值
	 */
	protected static final int CODE_ENTER_HOME = 4;
	
	/**
	 * 启动系统安装界面所传递的请求码
	 */
	private static final int INSTALL_REQUEST_CODE = 5;
	
	/**
	 * SharedPreferences设置中心的配置文件
	 */
	private SharedPreferences mPref;
	
	/**
	 * 根布局
	 */
	private RelativeLayout rlRoot;
	
	private Handler mHandler = new Handler(){
		
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case CODE_UPDATE_DIALOT:
				//弹出升级对话框
				showUpdateDialog();
				break;
			case CODE_URL_ERROR:
				Toast.makeText(SplashActivity.this, "url错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_NET_ERROR:
				Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_JSON_ERROR:
				Toast.makeText(SplashActivity.this, "数据解析错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case CODE_ENTER_HOME:
				enterHome();
				break;

			default:
				break;
			}
		}
		
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setText("版本号：" + getVersionName());
        tvProgress = (TextView) findViewById(R.id.tv_progress); //默认隐藏
        
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        //判断是否自动更新
        boolean autoUpdate = mPref.getBoolean("auto_update", true);
        if (autoUpdate) {
        	//检查软件最新的版本号
        	checkVersion();
		}else {
			//延时两秒后发送消息
			mHandler.sendEmptyMessageDelayed(CODE_ENTER_HOME, 2000);
		}
        //渐变的动画效果
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1f);
        animation.setDuration(2000);
        rlRoot.startAnimation(animation);
    }
    
    /**
     * 获取本地软件版本名称
     * @return 本地软件版本名称
     */
    private String getVersionName(){
    	//获取包管理器
    	PackageManager packageManager = getPackageManager();
    	try {
    		//获取包的相关信息对象,getPackageName()方法获取包名
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			String versionName = packageInfo.versionName;
			return versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     *  获取本地软件版本号
     * @return 本地软件版本号
     */
    private int getVersionCode(){
    	PackageManager packageManager = getPackageManager();
    	try {
    		PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
    		int versionCode = packageInfo.versionCode;
    		return versionCode;
    	} catch (NameNotFoundException e) {
    		e.printStackTrace();
    	}
    	return -1;
    }
    
    /**
     * 获取软件最新版本版本号
     * @return 软件最新版本版本号
     */
    private String checkVersion(){
    	final long startTime = System.currentTimeMillis();
    	new Thread(){

			@Override
    		public void run() {
				//获取一个消息
				Message message = Message.obtain();
				HttpURLConnection openConnection = null;
    			try {
        			/*
        			 * 本机地址用localhost，但是如果用模拟器加载本机的IP地址时，可以用IP（10.0.2.2）来替换 
        			 */
    				URL url = new URL("http://192.168.1.100:8080/update.json");
        			openConnection = (HttpURLConnection) url.openConnection();
        			openConnection.setRequestMethod("GET"); //设置请求方法
        			openConnection.setConnectTimeout(5000); //设置连接超时
        			openConnection.setReadTimeout(5000); //设置相应超时，表示连接上了，但服务器迟迟不给相应
        			openConnection.connect(); //连接服务器
        			int responseCode = openConnection.getResponseCode(); //获取响应码
        			if (responseCode == 200) {
						InputStream inputStream = openConnection.getInputStream();
						String result = StreamUtils.readFromStream(inputStream);
						LogUtils.d("MainActivity", "网络返回：" + result);
						//使用JSONObject对象解析json数据
						JSONObject jsonObject = new JSONObject(result);
						mVersionName = jsonObject.getString("versionName");
						mVersionCode = jsonObject.getInt("versionCode");
						mDesc = jsonObject.getString("description");
						mDownloadUrl = jsonObject.getString("downloadUrl");
						LogUtils.d("MainActivity", mVersionName + mVersionCode + mDesc + mDownloadUrl);
						if (mVersionCode > getVersionCode()) { //判断是否有更新
							//有版本更新，设置版本更新消息值
							message.what = CODE_UPDATE_DIALOT;
						}else {
							//没有版本更新
							message.what = CODE_ENTER_HOME;
						}
        			}
        			
        		} catch (MalformedURLException e) {
        			//url错误的异常
        			message.what = CODE_URL_ERROR;
        			e.printStackTrace();
        		} catch (IOException e) {
        			//网络错误异常
        			message.what = CODE_NET_ERROR;
        			e.printStackTrace();
        		} catch (JSONException e) {
					// JSON异常
        			message.what = CODE_JSON_ERROR;
					e.printStackTrace();
				}finally{
					long endTime = System.currentTimeMillis();
					long timeUsed = endTime - startTime; //访问网络花费的时间
					if (timeUsed < 2000) {
						//强制休眠一段时间，保证闪屏页展示两秒钟
						try {
							Thread.sleep(2000 - timeUsed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					mHandler.sendMessage(message);
					
					if (openConnection != null) {
						openConnection.disconnect(); //关闭网络连接
					}
				}
    		};
    		
    	}.start();
    	
		return null;
		
    }

	/**
	 * 弹出升级对话框
	 */
	protected void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setCancelable(false); 设置返回键无效，降低客户体验质量
		builder.setTitle("最新版本：" + mVersionName);
		builder.setMessage(mDesc);
		builder.setPositiveButton("立即更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LogUtils.d("MainActivity", "立即更新");
				//下载apk文件
				download();
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//进入主界面
				enterHome();
			}
		});
		//设置用户点击返回键触发事件
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				//进入主界面
				enterHome();
			}
		});
		//将弹窗显示出来
		builder.show();
	}

	/**
	 * 使用第三方jar包xUtils下载apk文件
	 */
	protected void download() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { //判断SD卡状态是否可用
			tvProgress.setVisibility(View.VISIBLE); //显示进度
			String target = Environment.getExternalStorageDirectory().getAbsolutePath() + "/update.apk";
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.download(mDownloadUrl, target, new RequestCallBack<File>( ) {
				
				//文件的下载进度，实时回调
				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					super.onLoading(total, current, isUploading);
					LogUtils.d("MainActivity", "下载进度：" + current + " / " + total);
					tvProgress.setText("下载进度：" + current * 100 / total + "%");
				}
				
				//下载成功时回调此方法
				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					LogUtils.d("MainActivity", "下载成功");
					//跳转到系统下载页面，引导用户安装并覆盖原软件
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.setDataAndType(Uri.fromFile(arg0.result), 
							"application/vnd.android.package-archive");
					//如果用户点击取消安装的话，会返回一个处理结果，回调方法：onActivityResult
					startActivityForResult(intent, INSTALL_REQUEST_CODE);
				}
				
				//下载失败是回调此方法
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
				}
			});
		}else {
			Toast.makeText(SplashActivity.this, "未检测到您的SD卡", Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		enterHome();
	}
	
	/**
	 * 进入主界面
	 */
	private void enterHome(){
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}
	
}
