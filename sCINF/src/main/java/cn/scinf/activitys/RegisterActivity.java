package cn.scinf.activitys;

import com.example.scinf.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
//注册activity
public class RegisterActivity extends Activity {
	private WebView webview;
	public RegisterActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		webview = (WebView)findViewById(R.id.webregister);
		webview.getSettings().setJavaScriptEnabled(true);//支持javascript
		webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//
		webview.getSettings().setBuiltInZoomControls(true);//
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				
				webview.loadUrl(url);//任何连接在本浏览器加载。
				return true;
			}

			
			
			
		});
		String url = getIntent().getStringExtra("url");
		webview.loadUrl(url);//加载内容
		//注册的时候需要与js进行交互实现。
		
	}
	
}
