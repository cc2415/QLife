package com.krislq.sliding;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class LeadFile extends Activity {

	public ViewPager viewpager;
	public View[] pages = new View[3];

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐藏标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		setContentView(R.layout.lead_activity);
		// 隐藏顶端的Action Bar
		Exit.getInstance().addActivity(this);

		viewpager = (ViewPager) findViewById(R.id.viewpager);
		pages[0] = getLayoutInflater().inflate(R.layout.page1, null);
		pages[1] = getLayoutInflater().inflate(R.layout.page2, null);
		pages[2] = getLayoutInflater().inflate(R.layout.page3, null);
		ViewPagerAdapter adapter = new ViewPagerAdapter();// 创建适配器对象
		viewpager.setAdapter(adapter);

		ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				if (arg0 == 2) {
					Button button = (Button) findViewById(R.id.begin);
					System.out.println(button.toString());
					button.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							System.out.println("有点击了");
							Intent intent = new Intent(LeadFile.this,
									MainActivity.class);
							startActivity(intent);
							finish();
						}
					});
				}
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}
		};
		viewpager.setOnPageChangeListener(listener);

	}

	// 用适配器来填充试图
	class ViewPagerAdapter extends PagerAdapter {
		public int getCount() {
			return pages.length;
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		// 移除原先的填充
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(pages[position]);
		}

		// 加入填充
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(pages[position]);
			return pages[position];
		}
	}
}
