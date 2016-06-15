package cn.scinf.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.scinf.R;
//第一Activity 启动时可以实现加载头像。以及注册等等。
public class FirstActivity extends Activity {

	public FirstActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);//加载第一xml
		//首启动的activity。检测是否有用户的注册信息。如果有，那么直接跳跃到ContentActivity
		//如果没有进入到load界面。
		SharedPreferences pre = getSharedPreferences("loadinf",0);
		if(pre.contains("userid"))
		{
			//用户已经注册过
			Intent in = new Intent(this,ContentActivity.class);
			startActivity(in);
			finish();
		}
		else
		{
			//尚未注册
//			Intent in = new Intent(this,LoadActivity.class);
//			startActivity(in);
			Intent in = new Intent(this,ContentActivity.class);//直接转换到内容activity.
			startActivity(in);
			finish();
			
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
	}
	
}
