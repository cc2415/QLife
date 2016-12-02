package com.krislq.sliding;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krislq.sliding.R;

/**
 * 
 * @author <a href="mailto:kris@krislq.com">Kris.lee</a>
 * @since Mar 12, 2013
 * @version 1.0.0
 */
public class ContentFragment extends Fragment {
	String text = null;

	public ContentFragment() {
	}

	/*
	 * public ContentFragment(String text) { }
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return container;
		/*
		 * Log.e("Krislq", "onCreateView:"+ text); //inflater the layout View
		 * view = inflater.inflate(R.layout.fragment_text, null); TextView
		 * textView =(TextView)view.findViewById(R.id.textView);
		 * if(!TextUtils.isEmpty(text)) { textView.setText(text); } return view;
		 */
	}

	// 生命周期
	// 销毁一个易操作的菜单选项
	public void onDestroy() {
		super.onDestroy();
	}

	// 分离一操作过后菜单选项
	public void onDetach() {
		super.onDetach();
	}

	// 暂停一个菜单选项的操作
	public void onPause() {
		super.onPause();
	}

	// 一个新的菜单选项开始操作
	public void onResume() {
		super.onResume();
	}

	// 开始一个菜单选项
	public void onStart() {
		super.onStart();
	}

	// 停止一个菜单选项
	public void onStop() {

		super.onStop();
	}

}
