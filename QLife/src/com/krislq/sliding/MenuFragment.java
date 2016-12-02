package com.krislq.sliding;

import java.io.File;

import com.slidingmenu.lib.SlidingMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class MenuFragment extends PreferenceFragment implements
		OnPreferenceClickListener {
	int index = -1;
	MainActivity mainactivity;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		// set the preference xml to the content view
		addPreferencesFromResource(R.xml.menu);
		// add listener
		findPreference("a").setOnPreferenceClickListener(this);
		findPreference("a").setLayoutResource(R.layout.menucolorselect);
		findPreference("b").setOnPreferenceClickListener(this);
		findPreference("b").setLayoutResource(R.layout.menusensor);
		findPreference("c").setOnPreferenceClickListener(this);
		findPreference("c").setLayoutResource(R.layout.menushare);
		findPreference("d").setOnPreferenceClickListener(this);
		findPreference("d").setLayoutResource(R.layout.menusavefile);
		findPreference("e").setOnPreferenceClickListener(this);
		findPreference("e").setLayoutResource(R.layout.menugraph);
		findPreference("f").setOnPreferenceClickListener(this);
		findPreference("f").setLayoutResource(R.layout.menubrushstyle);
		findPreference("g").setOnPreferenceClickListener(this);
		findPreference("g").setLayoutResource(R.layout.menuprintfont);
		findPreference("h").setOnPreferenceClickListener(this);
		findPreference("h").setLayoutResource(R.layout.menuchannefile);
		findPreference("i").setOnPreferenceClickListener(this);
		findPreference("i").setLayoutResource(R.layout.menusetting);
		findPreference("j").setOnPreferenceClickListener(this);
		findPreference("j").setLayoutResource(R.layout.menuexit);

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mainactivity = ((MainActivity) getActivity());
	}

	public boolean onPreferenceClick(Preference preference) {
		String key = preference.getKey();
		// 颜色选择
		if ("a".equals(key)) {
			((MainActivity) getActivity()).getSlidingMenu().toggle();
			Intent intent = new Intent(mainactivity, Color_select.class);
			startActivity(intent);
			System.out.println("颜色选择成功");
		}
		// 开启传感器
		else if ("b".equals(key)) {
			((MainActivity) getActivity()).getSlidingMenu().toggle();
			Intent intent=new Intent(mainactivity,Sensor_setting.class);
			startActivity(intent);
			System.out.println("传感器启动成功");
		}
		// 分享
		else if ("c".equals(key)){
			((MainActivity) getActivity()).getSlidingMenu().toggle();
				mainactivity.autosave();
				File url=new File(Environment.getExternalStorageDirectory()
						+ "/QLifeimg/QLife_share.png");
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.putExtra(intent.EXTRA_STREAM,Uri.fromFile(url));
				intent.setType("image/png");
				startActivity(Intent.createChooser(intent, "请选择分享方式"));
			System.out.println("分享成功");
		}
		// 保存文件
		else if ("d".equals(key)) {
			((MainActivity) getActivity()).getSlidingMenu().toggle();
			mainactivity.savefiledialog();
			System.out.println("保存成功");
		}
		//2D图形绘制
				else if ("e".equals(key)) {
					((MainActivity) getActivity()).getSlidingMenu().toggle();
					System.out.println("主页传回值："+mainactivity.getBoolvoice());
					if(!mainactivity.getBoolvoice()){
						Toast.makeText(mainactivity, "请先打开传感器中的语音输入！", Toast.LENGTH_LONG).show();
					}
					else{
						if(mainactivity.getBoolvoice()){
							Intent intent = new Intent(mainactivity,Recognition_speech.class);
							startActivity(intent);
							System.out.println("2D图形绘制成功");
						}
						else{
							Toast.makeText(mainactivity, "请先打开传感器中的语音输入！", Toast.LENGTH_LONG).show();
						}
					}
				}
		// 画笔样式
		else if ("f".equals(key)) {
			((MainActivity) getActivity()).getSlidingMenu().toggle();
			System.out.println("画笔样式成功");
		}
		// 打印字体
		else if ("g".equals(key)) {
			((MainActivity) getActivity()).getSlidingMenu().toggle();
			mainactivity.printfont();
			System.out.println("打印字体成功");
		}
		// 导入图片
		else if ("h".equals(key)) {
			((MainActivity) getActivity()).getSlidingMenu().toggle();
			mainactivity.channelfiledialog();
			System.out.println("导入成功");
		}
		// 设置
		else if ("i".equals(key)) {
			((MainActivity) getActivity()).getSlidingMenu().toggle();
			Intent intent=new Intent(mainactivity,Setting.class);
			startActivity(intent);
			System.out.println("设置");
		}
		// 退出
		else if ("j".equals(key)) {
			((MainActivity) getActivity()).getSlidingMenu().toggle();
			System.out.println("退出");
			Exit.getInstance().exit();
		}
		// anyway , show the sliding menu
		((MainActivity) getActivity()).getSlidingMenu().toggle();
		return false;
	}

}
