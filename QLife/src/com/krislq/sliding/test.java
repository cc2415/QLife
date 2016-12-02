package com.krislq.sliding;

public class test {
	private String str;
	Boolean pressurevalue=false, gravityvalue=false,voicevalue=false;
	Boolean hidevalue=false;
	private Boolean[] setting={pressurevalue, gravityvalue,voicevalue};
	private test(){
		//语音回调值
		str = null;
	}
	//语音界面的传值
	public void setStr(String str){
		this.str = str;
	}
	public String getStr(){
		return str;
	}
	
	//设置中的隐藏标题栏
	public void setbooleanhide(Boolean hidevalue){
		this.hidevalue=hidevalue;
	}
	public Boolean getbooleanhide(){
		return hidevalue;
	}
	
	
	//传感器设置中的三个值
	public void setboolean(Boolean pressurevalue,Boolean gravityvalue,Boolean voicevalue){
		setting[0]=pressurevalue;
		setting[1]=gravityvalue;
		setting[2]=voicevalue;
		this.pressurevalue=setting[0];
		this.gravityvalue=setting[1];
		this.voicevalue=setting[2];
	}
	public Boolean[] getboolean(){
		return setting;
	}
	
	private static test te = new test();
	public static test gettest(){
		return te;
	}
}
