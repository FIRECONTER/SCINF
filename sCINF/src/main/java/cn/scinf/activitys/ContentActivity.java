package cn.scinf.activitys;



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;







import com.example.scinf.R;

import cn.myfragment.ItemFragment;
import cn.scinf.services.Myservice;
import cn.scinf.views.CategoryTabStrip;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
//显示内容的Activity 
//接收来自于服务器的url 以及title 列表显示内容即可。
//访问服务器获取内容。
//没有网的时候加载本地的数据库数据。
//一个fragment中间内容切换。
import android.widget.Toast;
public class ContentActivity extends FragmentActivity {

	private CategoryTabStrip tabs;//tab 
	private ViewPager mViewPager;//viewpager 实现切换
	private FragmentPagerAdapter mAdapter;//fragment适配
	private List<ItemFragment> list = new LinkedList<ItemFragment>();
	private Intent in;
	private String[] schools=new String[]{
			"qinghua",
			"beida",
			"fudan",
			"shangjiao",
			"keda",
			"renda",
			"beiyou",
			"beiwai",
			"beijiao"	
	};
	public ContentActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contentactivity);
		in = new Intent(this,Myservice.class);
		startService(in);
		tabs =(CategoryTabStrip)findViewById(R.id.category_strip);//
		
		mViewPager = (ViewPager)findViewById(R.id.id_viewpager);//
		mAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		tabs.setViewPager(mViewPager);
		
		
		}
		
	private class MyPagerAdapter extends FragmentPagerAdapter
	{
		 private final List<String> catalogs = new ArrayList<String>();
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
			//
			 	catalogs.add(getString(R.string.qinghua));
			 	catalogs.add(getString(R.string.beida));
	            catalogs.add(getString(R.string.fudan));
	            catalogs.add(getString(R.string.shangjiao));
	            catalogs.add(getString(R.string.keda));
	            catalogs.add(getString(R.string.renda));
	            catalogs.add(getString(R.string.beiyou));
	            catalogs.add(getString(R.string.beiwai));
	            catalogs.add(getString(R.string.beijiao));
	           

		}

		@Override
		public android.support.v4.app.Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			ItemFragment item = null;
			
			return new ItemFragment(schools[arg0]);//传递学校的参数
			//根据选择的id决定使用的fragment.
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return catalogs.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return catalogs.get(position);
		}

		
		
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("关闭content");
		stopService(in);//关闭service.
		System.out.println("关闭service");
	}
	
	}


	
