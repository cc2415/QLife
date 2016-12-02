package com.krislq.sliding;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class CommingActivity extends Activity {

	Timer timer;
	LinearLayout linearlayout;
	//记录页面的数据(开启程序的次数)，判断是否第一次进入程序
	SharedPreferences frequency_share;
	SharedPreferences.Editor frequency_editor;
	Boolean isfristcomming=false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐藏标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 隐藏顶端的Action Bar
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		setContentView(R.layout.comming_activity);
		Exit.getInstance().addActivity(this);
		
		frequency_share=getSharedPreferences("CommingActivity", CommingActivity.MODE_PRIVATE);
		frequency_editor=frequency_share.edit();
		//取值（如果此处内有值传入（为空），则默认为true）
		isfristcomming=frequency_share.getBoolean("isfristcomming", true);
		
		linearlayout=(LinearLayout)findViewById(R.id.commingLinear);
		linearlayout.setBackgroundResource(R.drawable.commingbackground);
		timer = new Timer(true);
		TimerTask task = new TimerTask() {
			public void run() {
				System.out.println("五秒输出了");
				if(isfristcomming){
					frequency_editor.putBoolean("isfristcomming", false);
					frequency_editor.commit();
					Intent intent = new Intent(CommingActivity.this,LeadFile.class);
					startActivity(intent);
					finish();
				}else{
					Intent intent = new Intent(CommingActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
		};
		timer.schedule(task,3000);
	}

}
