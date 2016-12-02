package com.krislq.sliding;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Recognition_speech extends Activity {

	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	Button btn;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recognition_speech);
		Exit.getInstance().addActivity(this);
		btn=(Button)findViewById(R.id.btn);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try{
					//开启Intent 语音识别模式
					Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
					//语言模式和自由模式的语音识别
					/*intent.putExtra(
							RecognizerIntent.EXTRA_LANGUAGE_MODEL, 
							RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);*/
					
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"开始语音");
					//开始识别
					startActivityForResult(intent,VOICE_RECOGNITION_REQUEST_CODE);
				}catch(Exception e){
					Toast.makeText(getApplicationContext(), "您的手机好像不支持语音设备！",Toast.LENGTH_LONG).show();  
					}
			}
		});	
} 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        // TODO Auto-generated method stub  
        //回调获取从谷歌得到的数据   
    	if(requestCode==VOICE_RECOGNITION_REQUEST_CODE && resultCode==RESULT_OK){  
            //取得语音的字符  
            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);  
            Intent intentgraph=new Intent(Recognition_speech.this,MainActivity.class);
            String resultString=""; 
            int i;
            for(i=0;i<10;i++){
                resultString=results.get(i);  
                if(resultString.equals("圆形")||
                		resultString.equals("圆")||
                		resultString.equals("正方形")||
                		resultString.equals("方形")||
                		resultString.equals("长方形")||
                		resultString.equals("矩形")||
                		resultString.equals("椭圆形")||
                		resultString.equals("三角形")||
                		resultString.equals("梯形"))
                {
                	test.gettest().setStr(resultString);
                	startActivity(intentgraph);
                	i=11;
                	break;
                }
           }
            if(i!=11){
            	Toast.makeText(this, "找不到，请重新输入！注意：仅支持基本图形...", Toast.LENGTH_LONG).show();
            }    
       }  
        super.onActivityResult(requestCode, resultCode, data);
   }  
    
}
