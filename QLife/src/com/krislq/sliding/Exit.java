package com.krislq.sliding;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Exit {
	private List<Activity> activityList = new LinkedList<Activity>();
	private static Exit instance;

	private Exit() {
	}

	// 单例模式中获取唯一的MyApplication实例
	public static Exit getInstance() {
		if (null == instance) {
			instance = new Exit();
		}
		return instance;
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity并finish()
	public void exit() {
		System.out.println("exit");
		//销毁保存数据（传感器）
		SharedPreferences sharesensor=activityList.get(0).getSharedPreferences("sensor", Sensor_setting.MODE_PRIVATE);
		Editor editsensor=sharesensor.edit();
		editsensor.clear();
		editsensor.apply();
		//颜色选择
		SharedPreferences sharepf=activityList.get(1).getSharedPreferences("colorselect", Color_select.MODE_PRIVATE);
		Editor editor=sharepf.edit();
		editor.clear();
		editor.apply();
		//设置
		SharedPreferences sharehide=activityList.get(2).getSharedPreferences("hide", Setting.MODE_PRIVATE);
		Editor edithide=sharehide.edit();
		edithide.clear();
		edithide.apply();
		for (Activity activity : activityList) {
			activity.finish();
		}
		
		System.exit(0);
	}

}
