package com.krislq.sliding;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Sensor_setting extends Activity{

	ToggleButton pressure_toggle,orientation_toggle,voice_toggle;
	LinearLayout pressure_linear,orientation_linear,voice_linear;
	TextView introduce_text;
	SharedPreferences sharesensor;
	SharedPreferences.Editor editsensor;
	Boolean pressurecheck=false,orientationcheck=false,voicecheck=false;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_setting);
		Exit.getInstance().addActivity(this);
		//传值设定
		sharesensor=getSharedPreferences("sensor", MODE_PRIVATE);
		editsensor=sharesensor.edit();
		
		pressure_linear=(LinearLayout)findViewById(R.id.pressure_linear);
		orientation_linear=(LinearLayout)findViewById(R.id.orientation_linear);
		voice_linear=(LinearLayout)findViewById(R.id.voice_linear);
		introduce_text=(TextView)findViewById(R.id.introduce_text);
		pressure_toggle=(ToggleButton)findViewById(R.id.pressure_toggle);
		orientation_toggle=(ToggleButton)findViewById(R.id.orientation_toggle);
		voice_toggle=(ToggleButton)findViewById(R.id.voice_toggle);
		
		pressure_toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					pressurecheck=true;
				}else{
					pressurecheck=false;
				}
				System.out.println(pressurecheck);
			}
		});
		orientation_toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					orientationcheck=true;
				}else{
					orientationcheck=false;
				}
				System.out.println(orientationcheck);
			}
		});
		voice_toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					voicecheck=true;
				}else{
					voicecheck=false;
				}
				System.out.println(voicecheck);
			}
		});
		//设置点击后,下方文本出现传感器相应的解释
		pressure_linear.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				introduce_text.setText(R.string.pressure_introduce);
			}
		});
		orientation_linear.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				introduce_text.setText(R.string.orientation_introduce);
			}
		});
		voice_linear.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				introduce_text.setText(R.string.voice_introduce);
			}
		});	
	}
	//重新开始（重新回到这个页面时触发）
	protected void onResume() {
		super.onResume();
		pressure_toggle.setChecked(sharesensor.getBoolean("pressurecheck", false));
		orientation_toggle.setChecked(sharesensor.getBoolean("orientationcheck", false));
		voice_toggle.setChecked(sharesensor.getBoolean("voicecheck", false));
		System.out.println("重新开始");
	}
	//暂停（设置之后转回主页面时触发）
	protected void onPause() {
		super.onPause();
		System.out.println("暂停");
		editsensor.putBoolean("pressurecheck", pressurecheck);
		editsensor.putBoolean("orientationcheck", orientationcheck);
		editsensor.putBoolean("voicecheck", voicecheck);
		editsensor.commit();
		System.out.println("传值 "+pressurecheck+","+orientationcheck+","+voicecheck);
		//传值回主页面
		test.gettest().setboolean(pressurecheck, orientationcheck, voicecheck);
		System.out.println("传值成功");
	}
}
