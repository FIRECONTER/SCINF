package cn.myfragment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

import org.apache.http.protocol.ResponseConnControl;
import org.json.JSONException;
import org.json.JSONObject;

import cn.scinf.activitys.WebActivity;
import cn.scinf.application.Myapplication;
import cn.scinf.beans.ContentBean;
import cn.scinf.dao.ServerDao;
import cn.scinf.utils.SCHOOLUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scinf.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;


























import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
//最基本的单元，实现刷新时获取网络数据，
public class ItemFragment extends Fragment {

	private PullToRefreshListView mPullRefreshListView;
	private ListView lvShow = null;
	private SimpleAdapter adapter;
	private List<Map<String,String>> list;//数据源。
	private RequestQueue queue;
	private String myurl;
	private String myschool;//存储学校信息。
	private ServerDao dao;
	private boolean state = false;
	public ItemFragment(String myschool) {
		// TODO Auto-generated constructor stub
		this.myschool = myschool;
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dao = new ServerDao(getActivity());//数据库操作。
		System.out.println(myschool+"页面被创建了");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.items, container, false);//填充显示内容。
		mPullRefreshListView = (PullToRefreshListView) (view.findViewById(R.id.pull_refresh_list));
		lvShow = mPullRefreshListView.getRefreshableView();//获取内部包含的listview
		list = new LinkedList<Map<String,String>>();
		adapter = new SimpleAdapter(getActivity(),list,R.layout.item,new String[]{"title","ping","school"},new int[]{R.id.title,R.id.ping,R.id.school});//绑定adapter
		lvShow.setAdapter(adapter);
		queue = Volley.newRequestQueue(getActivity());
		myurl = ((Myapplication)(getActivity().getApplication())).URL+"/"+"GetListServlet";//
		Log.i("listurl",myurl);//打印url
		
		//设置下拉上拉刷新的组件的属性。
		mPullRefreshListView.setMode(Mode.BOTH);
		// 下拉刷新时的提示文本设置//false true 在proxy 中设置决定是调用下拉刷新还是加载以前的数据。
		mPullRefreshListView.getLoadingLayoutProxy(true, false).setLastUpdatedLabel("下拉刷新");
		mPullRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel("");
		mPullRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新");
		mPullRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("放开以刷新");
		// 上拉加载更多时的提示文本设置
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setLastUpdatedLabel("上拉加载");
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("");
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("放开以加载");
		
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				if(refreshView.isHeaderShown())
				{
					//下拉刷新。
					new GetHeaderDataTask().execute();
				}
				else
				{
					//上拉加载。
					new GetBottomDataTask().execute();
				}
			}
		});
		lvShow.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Map<String,String> map = list.get(position-1);
				System.out.println("条目的位置"+position);
				String myurl = map.get("textcontent");//点击的url
				Intent iny = new Intent(getActivity(),WebActivity.class);
				iny.putExtra("url",myurl);
				startActivity(iny);//启动浏览器
			}
		});
		
		return view;
	}
	
	private class GetHeaderDataTask extends AsyncTask<Void, Void, List<ContentBean>>{

		@Override
		protected List<ContentBean> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			System.out.println("进入下拉过程");	
			final List<ContentBean> mylist = new LinkedList<ContentBean>();
			
			StringRequest req = new StringRequest(Method.POST,myurl,new Response.Listener<String>() {
				
				@Override
				public void onResponse(String response) {
					// TODO Auto-generated method stub
					
					String[] lines = response.split("endflag");
					
					for(String str:lines)
					{
						System.out.println("网络数据"+str);
						ContentBean bean = new ContentBean();
						String[] temp = str.split("datapos");
						bean.setTitle(temp[0]);
						bean.setTextcontent(temp[1]);
						bean.setZan(Integer.valueOf(temp[2]));
						bean.setCai(Integer.valueOf(temp[3]));
						bean.setSchool(temp[4]);
						mylist.add(bean);
						
					}
					state = true;
					
					System.out.println("网络数据接收完毕");
				}
			},new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
					// TODO Auto-generated method stub
					System.out.println(error.toString());
				}
			})
			{

				@Override
				protected Map<String, String> getParams()
						throws AuthFailureError {
					// TODO Auto-generated method stub
					
					HashMap<String,String> map = new HashMap<String, String>();
					map.put("school",myschool);
					return map;
				}
				
			};
			queue.add(req);
			//这个地方需要等待时间。等待数据接收完毕后才进行传递
			while(!state)
			{
				//直到为true的时候表示数据更新完毕了。
				//但是如果超时那么就break。
				int i=0;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
				if(i>10) break;
			}
			state = false;
			dao.insertAll(mylist);//插入数据库
			//目前更换为从数据库读取数据更新。
			//存储数据库。
			System.out.println("数据库更新完毕");
			return mylist;//json 数据封装成list。
		}
		

		@Override
		protected void onPostExecute(List<ContentBean> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//更新界面数据
			if(result.size()!=0)
			{
			list.clear();
			//更新数据。
			for(ContentBean bean:result)
			{
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("title",bean.getTitle());
				map.put("textcontent",bean.getTextcontent());
				map.put("ping","(赞"+bean.getZan()+")|(踩"+bean.getCai()+")");
				map.put("school",SCHOOLUtils.parseSchool(bean.getSchool()));
				list.add(map);
			}
			adapter.notifyDataSetChanged();
			System.out.println("界面数据更新完毕");
			mPullRefreshListView.onRefreshComplete();
			}
			else {System.out.println("数据为空");mPullRefreshListView.onRefreshComplete();}
			
		}
		
		}
	
	
	/*
	 * 功能：　上拉加载更多时的事件处理
	 */
	private class GetBottomDataTask extends AsyncTask<Void, Void, List<ContentBean>> {

		@Override
		protected List<ContentBean> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//数据库读取数据。
			System.out.println("上推的过程");
			List<ContentBean> mylist = dao.get(myschool);
			return mylist;
		}
		//上拉刷新底部显示
		
		@Override
		protected void onPostExecute(List<ContentBean> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//下拉加载过程
			//更新界面
			if(result.size()!=0)
			{
			list.clear();
			//更新数据。
			for(ContentBean bean:result)
			{
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("title",bean.getTitle());
				map.put("textcontent",bean.getTextcontent());
				map.put("ping","(赞"+bean.getZan()+")|(踩"+bean.getCai()+")");
				map.put("school",SCHOOLUtils.parseSchool(bean.getSchool()));
				list.add(map);
			}
			adapter.notifyDataSetChanged();
			mPullRefreshListView.onRefreshComplete();
			System.out.println("上推数据更新完毕");
			}
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		
	}
	
}
