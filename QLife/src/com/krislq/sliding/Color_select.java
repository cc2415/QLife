package com.krislq.sliding;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class Color_select extends Activity {
	private ViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private ImageView cursor;// 动画图片
	private TextView t1, t2, t3;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	MyPagerAdapter adapter;
	LayoutInflater mInflater;
	RelativeLayout rel;

	// 定义三个变量(v1)
	public int fr = 0, fg = 0, fb = 0;
	public int fr1 = 0, fg1 = 0, fb1 = 0;
	// 声明view对象//得到View对象
	View fview;
	// 声明三个seekbar对象和三个EditText
	public SeekBar fseekbar1;
	public SeekBar fseekbar2;
	public SeekBar fseekbar3;
	public EditText frt, fgt, fbt;

	// 定义三个变量(v2)
	public int sr = 0, sg = 0, sb = 0;
	public int sr1 = 0, sg1 = 0, sb1 = 0;
	// 声明view对象//得到View对象
	View sview;
	// 声明三个seekbar对象和三个EditText
	public SeekBar sseekbar1;
	public SeekBar sseekbar2;
	public SeekBar sseekbar3;
	public EditText srt, sgt, sbt;

	// 定义三个变量(v3)
	public int tr = 0, tg = 0, tb = 0;
	public int tr1 = 0, tg1 = 0, tb1 = 0;
	// 声明view对象//得到View对象
	View tview;
	// 声明三个seekbar对象和三个EditText
	public SeekBar tseekbar1;
	public SeekBar tseekbar2;
	public SeekBar tseekbar3;
	public EditText trt, tgt, tbt;
	//记录页面的数据，以便恢复
	SharedPreferences sharepf;
	SharedPreferences.Editor editor;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("创建");
		setContentView(R.layout.color_select_main);
		Exit.getInstance().addActivity(this);
		sharepf=getSharedPreferences("colorselect", Color_select.MODE_PRIVATE);
		initImageView();
		initTextView();
		initPageView();
		System.out.println("创建完毕");
	}
	//重新开始（重新回到这个页面时触发）
		protected void onResume() {
			super.onResume();
			//取出数据
			//画笔一页面的三个seekbar
			fseekbar1.setProgress(sharepf.getInt("vf_seekbarf", 0));
			fseekbar2.setProgress(sharepf.getInt("vf_seekbars", 0));
			fseekbar3.setProgress(sharepf.getInt("vf_seekbart", 0));
			//画笔二页面的三个seekbar
			sseekbar1.setProgress(sharepf.getInt("vs_seekbarf", 0));
			sseekbar2.setProgress(sharepf.getInt("vs_seekbars", 0));
			sseekbar3.setProgress(sharepf.getInt("vs_seekbart", 0));
			//画板页面的三个seekbar
			tseekbar1.setProgress(sharepf.getInt("vt_seekbarf", 0));
			tseekbar2.setProgress(sharepf.getInt("vt_seekbars", 0));
			tseekbar3.setProgress(sharepf.getInt("vt_seekbart", 0));
			
			System.out.println("重新开始");
		}
		//暂停（设置之后转回主页面时触发）
		protected void onPause() {
			super.onPause();
			editor=sharepf.edit();//获得edit对象
			//记录数据
			//画笔一页面的三个seekbar
			editor.putInt("vf_seekbarf", fr);
			editor.putInt("vf_seekbars", fg);
			editor.putInt("vf_seekbart", fb);
			//画笔二页面的三个seekbar
			editor.putInt("vs_seekbarf", sr);
			editor.putInt("vs_seekbars", sg);
			editor.putInt("vs_seekbart", sb);
			//画板页面的三个seekbar
			editor.putInt("vt_seekbarf", tr);
			editor.putInt("vt_seekbars", tg);
			editor.putInt("vt_seekbart", tb);
			editor.commit();
			System.out.println("暂停");
		}
		
	private void initPageView() {
		mInflater = getLayoutInflater();
		listViews = new ArrayList<View>();
		listViews.add(mInflater.inflate(R.layout.color_brush, null));
		listViews.add(mInflater.inflate(R.layout.color_brush_second, null));
		listViews.add(mInflater.inflate(R.layout.color_board, null));
		adapter = new MyPagerAdapter(listViews);
		mPager = (ViewPager) findViewById(R.id.page);
		mPager.setAdapter(adapter);
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void initTextView() {
		t1 = (TextView) findViewById(R.id.tab1);
		t2 = (TextView) findViewById(R.id.tab2);
		t3 = (TextView) findViewById(R.id.tab3);
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
	}

	private void initImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		rel = (RelativeLayout) findViewById(R.id.layout);

		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.buoy)
				.getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / 4 - bmpW) / 3;
		// Matrix matrix = new Matrix();
		// matrix.postTranslate(offset, 0);
		cursor.setBackgroundResource(R.drawable.buoy);
		// cursor.setScaleType(ScaleType.MATRIX);
		// cursor.setImageMatrix(matrix);
		rel.setPadding(offset, 0, 0, 0);

	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			// TODO Auto-generated method stub
			mPager.setCurrentItem(index);
		}
	}

	public class MyPagerAdapter extends PagerAdapter implements
			SeekBar.OnSeekBarChangeListener {
		public List<View> mListViews;
		public View v1;
		public View v2;
		public View v3;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
			getViewClickListener(mListViews);
		}

		public void getViewClickListener(List<View> listview) {
			v1 = listview.get(0);
			v2 = listview.get(1);
			v3 = listview.get(2);
			// 得到v1的控件
			fview = (View) v1.findViewById(R.id.view1);
			fseekbar1 = (SeekBar) v1.findViewById(R.id.seek1);
			fseekbar2 = (SeekBar) v1.findViewById(R.id.seek2);
			fseekbar3 = (SeekBar) v1.findViewById(R.id.seek3);
			frt = (EditText) v1.findViewById(R.id.rtext);
			fgt = (EditText) v1.findViewById(R.id.gtext);
			fbt = (EditText) v1.findViewById(R.id.btext);
			// 分别对三个seek绑定监听器
			fseekbar1
					.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);
			fseekbar2
					.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);
			fseekbar3
					.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);

			sview = (View) v2.findViewById(R.id.view1);
			sseekbar1 = (SeekBar) v2.findViewById(R.id.seek1);
			sseekbar2 = (SeekBar) v2.findViewById(R.id.seek2);
			sseekbar3 = (SeekBar) v2.findViewById(R.id.seek3);
			srt = (EditText) v2.findViewById(R.id.rtext);
			sgt = (EditText) v2.findViewById(R.id.gtext);
			sbt = (EditText) v2.findViewById(R.id.btext);
			// 分别对三个seek绑定监听器
			sseekbar1
					.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);
			sseekbar2
					.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);
			sseekbar3
					.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);

			tview = (View) v3.findViewById(R.id.view1);
			tseekbar1 = (SeekBar) v3.findViewById(R.id.seek1);
			tseekbar2 = (SeekBar) v3.findViewById(R.id.seek2);
			tseekbar3 = (SeekBar) v3.findViewById(R.id.seek3);
			trt = (EditText) v3.findViewById(R.id.rtext);
			tgt = (EditText) v3.findViewById(R.id.gtext);
			tbt = (EditText) v3.findViewById(R.id.btext);
			// 分别对三个seek绑定监听器
			tseekbar1
					.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);
			tseekbar2
					.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);
			tseekbar3
					.setOnSeekBarChangeListener((OnSeekBarChangeListener) this);
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		public void finishUpdate(View arg0) {
		}

		public int getCount() {
			return mListViews.size();
		}

		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		public Parcelable saveState() {
			return null;
		}

		public void startUpdate(View arg0) {
		}

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// v1
			fr = fseekbar1.getProgress();
			fg = fseekbar2.getProgress();
			fb = fseekbar3.getProgress();
			frt.setText("" + fr);
			fgt.setText("" + fg);
			fbt.setText("" + fb);
			// v2
			sr = sseekbar1.getProgress();
			sg = sseekbar2.getProgress();
			sb = sseekbar3.getProgress();
			srt.setText("" + sr);
			sgt.setText("" + sg);
			sbt.setText("" + sb);
			// v3
			tr = tseekbar1.getProgress();
			tg = tseekbar2.getProgress();
			tb = tseekbar3.getProgress();
			trt.setText("" + tr);
			tgt.setText("" + tg);
			tbt.setText("" + tb);
			// v1
			fview.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					fr1 = Integer.parseInt(frt.getText().toString());
					fg1 = Integer.parseInt(fgt.getText().toString());
					fb1 = Integer.parseInt(fbt.getText().toString());
					System.out.println(frt.getText().toString());
					System.out.println(fgt.getText().toString());
					System.out.println(fbt.getText().toString());
					System.out.println("有点击了");
					fseekbar1.setProgress(fr1);
					fseekbar2.setProgress(fg1);
					fseekbar3.setProgress(fb1);
				}
			});
			// 给view显示背景颜色
			fview.setBackgroundColor(Color.rgb(fr, fg, fb));

			// v2
			sview.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					sr1 = Integer.parseInt(srt.getText().toString());
					sg1 = Integer.parseInt(sgt.getText().toString());
					sb1 = Integer.parseInt(sbt.getText().toString());
					System.out.println(srt.getText().toString());
					System.out.println(sgt.getText().toString());
					System.out.println(sbt.getText().toString());
					System.out.println("有点击了");
					sseekbar1.setProgress(sr1);
					sseekbar2.setProgress(sg1);
					sseekbar3.setProgress(sb1);
				}
			});
			// 给view显示背景颜色
			sview.setBackgroundColor(Color.rgb(sr, sg, sb));

			// v3
			tview.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					tr1 = Integer.parseInt(trt.getText().toString());
					tg1 = Integer.parseInt(tgt.getText().toString());
					tb1 = Integer.parseInt(tbt.getText().toString());
					System.out.println(trt.getText().toString());
					System.out.println(tgt.getText().toString());
					System.out.println(tbt.getText().toString());
					System.out.println("有点击了");
					tseekbar1.setProgress(tr1);
					tseekbar2.setProgress(tg1);
					tseekbar3.setProgress(tb1);
				}
			});
			// 给view显示背景颜色
			tview.setBackgroundColor(Color.rgb(tr, tg, tb));

			Button sure = (Button) findViewById(R.id.sureback);
			sure.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(Color_select.this,
							MainActivity.class);
					// 传回画板中三个Seekbar的值
					intent.putExtra("red", tr);
					intent.putExtra("green", tg);
					intent.putExtra("blue", tb);
					
					intent.putExtra("red1", fr);
					intent.putExtra("green1", fg);
					intent.putExtra("blue1", fb);
					
					intent.putExtra("red2", sr);
					intent.putExtra("green2", sg);
					intent.putExtra("blue2", sb);
					startActivity(intent);
					finish();
				}
			});
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW; // 页卡1 -> 页卡2 偏移量
		int two = one * 2; // 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			rel.startAnimation(animation);
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	}
}
