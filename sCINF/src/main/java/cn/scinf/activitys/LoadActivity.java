package cn.scinf.activitys;

import java.util.HashMap;
import java.util.Map;

import cn.scinf.application.Myapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scinf.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
//登陆界面，没有注册时使用
public class LoadActivity extends Activity {
	private EditText userid;
	private EditText password;
	private Button load;
	private Button register;
	private ImageView loadimage;
	//点击load访问服务器,如果返回为loadok证明账号存在，同时在Sharedpreference 中写入登录状态。
			//如果不存在提示使用注册账号实现。让其选择注册账号。
			//注册账号成功之后，那么写入sharedpreference 保存值。
	public LoadActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);//加载登录页面。
		userid = (EditText)findViewById(R.id.userid);
		password = (EditText)findViewById(R.id.password);
		load = (Button)findViewById(R.id.load);
		register = (Button)findViewById(R.id.register);
		loadimage = (ImageView)findViewById(R.id.load_image);
		RequestQueue queue = Volley.newRequestQueue(this);//创建队列
		final Myapplication my = (Myapplication)getApplication();//获取application 中的全局变量。
		final String url = my.URL+"/LoadServlet";
		
		load.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String useridstring = userid.getText().toString();
				final String passwordstring = password.getText().toString();
				//调用volley框架访问服务器。
				StringRequest strre = new StringRequest(Method.POST,url,new Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Toast.makeText(LoadActivity.this, response, Toast.LENGTH_LONG).show();
						if(response.equals("loadok"))
						{
							//登陆成功
							SharedPreferences pre = getSharedPreferences("loadinf",0);
							SharedPreferences.Editor edit =   pre.edit();
							edit.putString("userid",useridstring);
							edit.apply();//提交更改。
							Intent in = new Intent(LoadActivity.this,ContentActivity.class);
							startActivity(in);//启动activity.
							finish();
						}
						else
						{
							//登陆失败。
							Toast.makeText(LoadActivity.this,"登陆失败",Toast.LENGTH_LONG).show();
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						
					}
				}){

					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						// TODO Auto-generated method stub
						Map<String,String> map = new HashMap<String,String>();
						map.put("userid",useridstring);
						map.put("password",passwordstring);
						return map;
					}
					
				};
				
				
				
				
			}
		});
		
		//注册选项
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(LoadActivity.this,RegisterActivity.class);
				//启动注册的Activity。
				String value = my.URL+"/RegisterInterface";
				in.putExtra("url", value);//传递数据
				startActivity(in);
				finish();
			}
		});
		
		
	}
	
}
