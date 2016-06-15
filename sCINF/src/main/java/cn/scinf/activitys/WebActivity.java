package cn.scinf.activitys;

import com.example.scinf.R;

import android.app.Activity;
import android.os.Bundle;
//访问由ContentActivity 传递的url访问服务器的内容。
//可与服务器的js 进行交互，实现基本的回复以及评论等等。
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
public class WebActivity extends Activity {
	private WebView webview;
	public WebActivity() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browser);
		webview = (WebView)findViewById(R.id.id_webView);
		String url = getIntent().getStringExtra("url");
		System.out.println(url);
		String title = getIntent().getStringExtra("title");
		webview.getSettings().setJavaScriptEnabled(true);//支持javascript
		webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//
		webview.getSettings().setBuiltInZoomControls(true);//
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.setWebViewClient(new WebViewClient(){
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				webview.loadUrl(url);
				return true;
			}
			
			
			
		});
		webview.loadUrl(url);
	}
	

}
