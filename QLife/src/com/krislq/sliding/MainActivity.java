package com.krislq.sliding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity implements SensorEventListener{

	float yq=7;
	private int scrollingOffset; // 记录滚动
	// 保存撤销的部分，重做时调出
	public static int i = 0;
	ArrayList arr = new ArrayList();
	private TuYa tuyaView = null;
	String fileName;
	Dialog dialog;
	Color_select select = new Color_select();
	EditText et;
	Button btnsure, btncancel;// 保存文件
	Button camera, local;
	int panduan = 0;
	//传感器页面的传回值
	//控制压力
	public Boolean getBoolpressure(){
		return test.gettest().getboolean()[0];}
	//控制方向
	public Boolean getBoolorientation(){
		return test.gettest().getboolean()[1];}
	public Boolean getBoolvoice(){
		return test.gettest().getboolean()[2];}
	//方向传感器
	private SensorManager mManager;
	int x; //用来空间坐标系的记录数据

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("进入主页");
		System.out.println(test.gettest().getbooleanhide());
		if(test.gettest().getbooleanhide()){
			//隐藏顶端的Action Bar
			ActionBar actionBar = getActionBar();
			actionBar.hide();
			setContentView(R.layout.frame_content);
		}
		Exit.getInstance().addActivity(this);
		//创建一个文件夹对象，赋值为外部存储器的目录
		File sdcardDir = Environment.getExternalStorageDirectory();
		// 得到一个路径，内容是sdcard的文件夹路径和名字
		String path = sdcardDir.getPath() + "/QLifeimg";
		File path1 = new File(path);
		if (!path1.exists()) {
			// 若不存在，创建目录，可以在应用启动的时候创建
			path1.mkdirs();
		}
		// 取屏幕的宽高
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		tuyaView = new TuYa(this, dm.widthPixels, dm.heightPixels);
		setContentView(tuyaView);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setTitle("");
		// set the Behind View(定义且设置菜单（隐藏在右侧的）)
		setBehindContentView(R.layout.frame_menu);
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		MenuFragment menuFragment = new MenuFragment();
		fragmentTransaction.replace(R.id.menu, menuFragment);
		fragmentTransaction.commit();
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.RIGHT);
		sm.setBehindWidth(205);//
		sm.setFadeDegree(0.00f);
		// SldingMenu的取值
		// TOUCHMODE_FULLSCREEN 全屏范围内移动
		// TOUCHMODE_MARGIN 半屏范围内移动
		// TOUCHMODE_NONE 不可以用手移动
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// Inflate the menu; this adds items to the action bar if it is present.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//将网络访问放置在线程中(为了解决异常)
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		//从系统服务中获取传感器
		mManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
	}
	protected void onResume() {
		super.onResume();
		//为加速度传感器注册监听器
		mManager.registerListener(this,
		mManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);	
	}
	protected void onStop() {
		//取消注册
		mManager.unregisterListener(this);
		super.onStop();
	}
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		x=(int) event.values[0];
	}
	
	
	//分享时自动保存
	public void autosave(){
		fileName="QLife_share";
		tuyaView.saveToSDCard();
	}
	
	public void savefiledialog() {
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.save_file_dialog);
		dialog.setTitle("图形文件保存");
		dialog.show();
		et = (EditText) dialog.findViewById(R.id.edit_savename);
		btnsure = (Button) dialog.findViewById(R.id.save_sure);
		btncancel = (Button) dialog.findViewById(R.id.save_cancel);
		btncancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btnsure.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				System.out.println("文件名"+et.getText().toString()+"文件名");
				if (et.getText().toString().length()==0) {
					Toast.makeText(MainActivity.this, "请输入文件名",
							Toast.LENGTH_SHORT).show();
				} else {
					fileName = et.getText().toString();
					tuyaView.saveToSDCard();
					dialog.dismiss();
					Toast.makeText(
							MainActivity.this,
							"文件保存至"
									+ Environment.getExternalStorageDirectory()
											.toString() + "/QLifeimg文件夹,文件名为"
									+ fileName + ".PNG", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	}

	public void channelfiledialog(){
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.channel_file);
		dialog.setTitle("请选择图片位置");
		dialog.show();
		System.out.println("创建了");
		camera = (Button) dialog.findViewById(R.id.camera);
		local = (Button) dialog.findViewById(R.id.local_album);
		camera.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 0);
			}
		});
		local.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				dialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "选择相片"), 1);
			}
		});
	}

	public void printfont(){
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.print_font_dialog);
		dialog.setTitle("字体打印");
		dialog.show();
	}
	
	// 接受并处理收到的图片
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			ContentResolver resolver = this.getContentResolver();

			if (tuyaView.mBitmap != null)// 释放内存
			{
				tuyaView.mBitmap.recycle();
			}
			try {
				// 取屏幕的宽高
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				int screenWidth=dm.widthPixels;
				int screenHeight=dm.heightPixels;
				
				tuyaView.mBitmap=LargeImageUtil.decodeSampledBitmapFromResource(resolver,uri,screenWidth , screenHeight);
				tuyaView.mCanvas = new Canvas(tuyaView.mBitmap);
				//tuyaView.mBitmap = Bitmap.createBitmap(bitmap);
				//tuyaView.mCanvas.setBitmap(tuyaView.mBitmap );
				panduan =  1;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// toggle就是程序自动判断是打开还是关闭
			toggle();
			getSlidingMenu().showMenu();// show menu
			// getSlidingMenu().showContent();//show content
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {// 加音量
			tuyaView.undo();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {// 减音量
			tuyaView.redo();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {// 返回键
			tuyaView.clear();
		}
		return super.onKeyDown(keyCode, event);
	}

	public class TuYa extends View {
		
		private Bitmap mBitmap;
		public Canvas mCanvas;
		private Path mPath; // 底层路径
		private Paint mBitmapPaint;// 画布的画笔
		private Paint mPaint;// 底层真实的画笔
		private float mX = 0, mY = 0;// 临时点坐标
		private static final float TOUCH_TOLERANCE = 4;

		// 保存Path路径的集合,用List集合来模拟栈
		private List<DrawPath> savePath;
		// 记录Path路径的对象
		private DrawPath dp;
		private int screenWidth, screenHeight;
		private DrawPath drp = new DrawPath();
		int red, green, blue; // 传递回来的的背景色
		int red1, green1, blue1; // 传递回来的的画笔色1
		int red2, green2, blue2; // 传递回来的的画笔色2
		String graph;// 传递回来语音页面需要绘制的图形

		// 涂鸦工具
		private class DrawPath {
			public Path path;// 路径
			public Paint paint;// 画笔
		}

		public TuYa(Context context, int w, int h) {

			super(context);
			screenWidth = w;
			screenHeight = h;

			System.out.println("准备");
			// 语音页面跳转回来传回的参数
			graph = test.gettest().getStr();
			System.out.println("string"+graph);
			
			System.out.println("开始");
			// 设置画板的颜色（颜色选择的页面）
			Intent intent = getIntent();// 用于激活它的意图对象，以便接收参数
			red = intent.getIntExtra("red", 0);
			green = intent.getIntExtra("green", 0);
			blue = intent.getIntExtra("blue", 0);
			System.out.println(red);
			System.out.println(green);
			System.out.println(blue);
			// 画笔一的颜色
			red1 = intent.getIntExtra("red1", 0);
			green1 = intent.getIntExtra("green1", 0);
			blue1 = intent.getIntExtra("blue1", 0);
			// 画笔二的颜色
			red2 = intent.getIntExtra("red2", 0);
			green2 = intent.getIntExtra("green2", 0);
			blue2 = intent.getIntExtra("blue2", 0);

			String str = getActionBar().toString();

			if (red != 0 || green != 0 || blue != 0) {// 加载颜色背景,此处不需要清理图层，因为进入颜色选择页面前已经关闭之前一页
				System.out.println("清除之前");
				System.out.println(mBitmap);
				System.out.println("即将加颜色图片");
				if (mBitmap != null)// 释放内存
				{
				mBitmap.recycle();
				}
				mBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
						Bitmap.Config.ARGB_8888);
			} else if (red == 0 && green == 0 && blue == 0 && panduan == 0) {
				// 加载背景图片
				System.out.println("即将加载背景图片");
				mBitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.background).copy(Bitmap.Config.ARGB_8888,
						true);
				System.out.println("已经加载背景图片");
			}
			System.out.println(red);
			System.out.println(green);
			System.out.println(blue);

			// 保存一次一次绘制出来的图形
			mCanvas = new Canvas(mBitmap);
			if(red!=0||green!=0||blue!=0){ 
				if(!getBoolorientation()){
					mCanvas.drawColor(Color.rgb(red, green, blue));
				}			
			}
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeJoin(Paint.Join.BEVEL);// 设置外边缘
			mPaint.setStrokeCap(Paint.Cap.SQUARE);// 形状
			mPaint.setStrokeWidth(yq);// 画笔宽度
			if(red1!=0||green1!=0||blue1!=0){
				mPaint.setColor(Color.rgb(red1, green1, blue1));
			}else{
				mPaint.setColor(Color.CYAN);
			}
			
			mPaint.setAlpha(180);
			System.out.println("全部设置完成");
			savePath = new ArrayList<DrawPath>();
		}
		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.rgb(red, green, blue));
			if(tuyaView.red!=0||tuyaView.green!=0||tuyaView.blue!=0){ 
				if(getBoolorientation()){
				tuyaView.mCanvas.drawColor(Color.rgb((x/20)*(tuyaView.red/20),
						(x/20)*(tuyaView.green/20),(x/20)*(tuyaView.blue/20)));
			}
			}
			
			// 移动屏幕
			// canvas.translate(0, scrollingOffset);
			// super.onDraw(canvas);
			// 放大缩小屏幕
			// canvas.scale(0.8f,0.8f);
			// 旋转画板canvas.rotate(10.0f);
			// 将前面已经画过得显示出来
			//对图像进行大小固定处理
			Rect dst=new Rect();
			dst.left=0;dst.top=0;
			dst.right=screenWidth;dst.bottom=screenHeight;
			canvas.drawBitmap(mBitmap,null,dst, mBitmapPaint);
			
			
			if ( graph != null) {
				System.out.println("string"+graph);
				if (graph.equals("圆形")) {
					canvas.drawCircle(100, 100, 80, mPaint);//四个参数分别是（圆心X轴坐标，圆心Y轴坐标，半径，画笔）
					System.out.println("圆形");
				} else if (graph.equals("正方形")) {
					canvas.drawRect(30, 90, 90, 150, mPaint);//五个参数分别是(左上角X轴坐标，左上角Y轴坐标，右下角X轴坐标，右下角Y轴坐标）
					System.out.println("正方形");
				} else if (graph.equals("长方形")||graph.equals("矩形")) {
					canvas.drawRect(10, 170, 70,200, mPaint);//五个参数分别是(左上角X轴坐标，左上角Y轴坐标，右下角X轴坐标，右下角Y轴坐标）
					System.out.println("长方形或者矩形");
				} else if (graph.equals("椭圆形")) {
					RectF re=new RectF(10,220,70,250); //定义一个矩形
			          canvas.drawOval(re, mPaint);//椭圆为四个顶点与矩形的四条边中点重合
					System.out.println("椭圆形");
				} else if (graph.equals("三角形")) {
					mPath.moveTo(10, 330);//把起点定下
			          mPath.lineTo(70,330);//从起点开始绘制
			          mPath.lineTo(40,270);//绘制
			          mPath.close();//使路径封闭（两个端口用直线连接）
			          canvas.drawPath(mPath, mPaint);//按照路径画
					System.out.println("三角形");
				} else if (graph.equals("梯形")) {
					  mPath.moveTo(10, 410);
			          mPath.lineTo(70,410);
			          mPath.lineTo(55,350);
			          mPath.lineTo(25, 350);
			          mPath.close();
			          canvas.drawPath(mPath, mPaint);
					System.out.println("梯形");
				}
			}
			if (mPath != null) {
				// 实时的显示
				mPaint.setTextSize(80);
				canvas.drawText("Qlife", 10, 100, mPaint);
				canvas.drawPath(mPath, mPaint);
			}
		}


		 //撤销(就是将画布清空， 将保存下来的path路径最后一个移除掉， 重新将路径画在画布上面)
		public void undo() {
			if (savePath != null && savePath.size() > 0) {
				arr.add(savePath.get(savePath.size() - 1));
				savePath.remove(savePath.size() - 1);
				redrawOnBitmap();
			}
		}
		// 重做上一步
		public void redo() {
			if (arr != null && arr.size() > 0) {
				for (i = arr.size() - 1; i >= 0; i--) {
					savePath.add((DrawPath) arr.get(i));
					arr.remove(i);
					redrawOnBitmap();
					break;
				}
			}
		}
		//清屏
		public void clear() {
			if (savePath != null && savePath.size() > 0) {
				savePath.clear();
				arr.clear();
				redrawOnBitmap();
			}
		}
		//重画
		private void redrawOnBitmap() {
			if (mBitmap != null)// 释放内存
			{
				System.out.println("重置清屏");
				System.out.println(mBitmap);
				mBitmap.recycle();
			}
			System.out.println("进入重置");
			if (red == 0 && green == 0 && blue == 0 && panduan == 0) {
				// 加载背景图片
				System.out.println("即将加载背景图片");
				mBitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.background).copy(Bitmap.Config.ARGB_8888,
						true);
				System.out.println("已经加载背景图片");
			} else if (red != 0 || green != 0 || blue != 0) {// 加载颜色背景
				System.out.println("清除之前");
				System.out.println(mBitmap);
				System.out.println("即将加颜色图片");
				mBitmap = Bitmap.createBitmap(screenWidth, screenHeight,
						Bitmap.Config.ARGB_8888);
			}else{
				
			}
			mCanvas.setBitmap(mBitmap);// 重新设置画布，相当于清空画布
			Iterator<DrawPath> iter = savePath.iterator();
			while (iter.hasNext()) {
				DrawPath drawPath = iter.next();
				mCanvas.drawPath(drawPath.path, drawPath.paint);
			}
			invalidate();// 刷新
		}

		// 滚动
		/*
		 * public boolean onScroll(MotionEvent e1, MotionEvent e2, float
		 * distanceX, float distanceY) { scrollingOffset += -distanceY;
		 * invalidate(); //return super.onScroll(e1, e2, distanceX, distanceY);
		 * return true; }
		 */

		public boolean onTouchEvent(MotionEvent event) {

			float x = event.getX();
			float y = event.getY();
			yq = (int) (event.getPressure() * 30);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 每次down下去重新new一个Path
				mPath = new Path();
				// 每一次记录的路径对象是不一样的
				dp = new DrawPath();
				dp.path = mPath;
				dp.paint = mPaint;
				touch_start(x, y);
				// invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				touch_move(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				touch_up();
				invalidate();
				break;
			}
			return true;
		}
		// 保存文件到SDcard
		public void saveToSDCard() {
			mCanvas.save(Canvas.ALL_SAVE_FLAG);
			mCanvas.restore();
			System.out.println("进来了");
			try {
				// 可以保存文件的共享外部存储器
				FileOutputStream fos = new FileOutputStream(
						new File(Environment.getExternalStorageDirectory()
								+ "/QLifeimg", fileName + ".png"));
				mBitmap.compress(CompressFormat.PNG, 100, fos);
				fos.flush(); // 把图片数据输出
				fos.close(); // 关闭
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("出来了");
		}

		// 待连接函数
		private void touch_start(float x, float y) {
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
		}

		private void touch_move(float x, float y) {
			float dx = Math.abs(x - mX);
			float dy = Math.abs(mY - y);
			if(getBoolpressure())
			{
				mPaint.setStrokeWidth(yq);
			}
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				// 从x1,y1到x2,y2画一条贝塞尔曲线，更平滑(直接用mPath.lineTo也是可以的)
				mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
				mX = x;
				mY = y;
				mCanvas.drawPath(mPath, mPaint);
			}
		}

		private void touch_up() {
			mPath.lineTo(mX, mY);
			// 将一条完整的路径保存下来(相当于入栈操作)
			savePath.add(dp);
			mPath = null;// 重新置空
		}

	}

}
