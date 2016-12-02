package com.krislq.sliding;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Setting extends Activity {

	LinearLayout restore_background,help,copyright;
	ToggleButton hide_title;
	//保存值
	SharedPreferences sharehide;
	SharedPreferences.Editor edithide;
	Boolean hide=false;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		Exit.getInstance().addActivity(this);
		//传值设定
		sharehide=getSharedPreferences("hide", MODE_PRIVATE);
		edithide=sharehide.edit();
		
		hide_title=(ToggleButton)findViewById(R.id.hide_title);
		hide_title.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					hide=true;
				}else{
					hide=false;
				}
				System.out.println(hide);
			}
		});
	}
		//重新开始（重新回到这个页面时触发）
		protected void onResume() {
			super.onResume();
			hide_title.setChecked(sharehide.getBoolean("hidecheck", false));
			System.out.println("重新开始");
		}
		//暂停（设置之后转回主页面时触发）
		protected void onPause() {
			super.onPause();
			System.out.println("暂停");
			//保存值
			edithide.putBoolean("hidecheck", hide);
			edithide.commit();
			System.out.println("传值 "+hide);
			//传值回主页面
			test.gettest().setbooleanhide(hide);
			System.out.println("传值成功");
		}
	
}
