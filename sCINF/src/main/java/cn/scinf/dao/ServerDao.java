package cn.scinf.dao;

import java.util.LinkedList;
import java.util.List;

import cn.scinf.beans.ContentBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class ServerDao extends SQLiteOpenHelper {
	
	public static final String DBNAME="header.db";
	public static final int DBVERSION = 1;
	private SQLiteDatabase myDatabase;//
	private static final String content="content";
	private static final String title="title";
	private static final String zan="zan";
	private static final String cai="cai";
	private static final String textcontent = "textcontent";
	private static final String school="school";
	
	public ServerDao(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DBNAME, null,DBVERSION);
		// TODO Auto-generated constructor stub
	}

	public ServerDao(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
		// TODO Auto-generated constructor stub
	}
	
	public ServerDao(Context context)
	{
		super(context, DBNAME, null,DBVERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
			this.myDatabase = db;
			String sql = "create table "+content+" ("+title+" varchar(40) primary key,"+textcontent+" text,";
			sql+=zan+" int,"+cai+" int,"+school+" varchar(40));";
			myDatabase.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	public synchronized void insertAll(List<ContentBean> list)
	{
		//抓取的数据进行插入
		//只是插入数据。
		//每一次只是执行10条。
		myDatabase = getWritableDatabase();
		
		int count=-1;
		
			for(ContentBean bean : list)
			{
				String mytitle = bean.getTitle();
			String sql = "title = "+"\""+mytitle+"\"";
			Cursor c = myDatabase.query(content,null,sql,null,null,null,null);
			count = c.getCount();
			Log.i("count",count+"");
			
			
			
			
				if(count==0)
				{
				ContentValues value = new ContentValues();
				value.put("title",bean.getTitle());
				value.put("textcontent",bean.getTextcontent());
				value.put("zan",bean.getZan());
				value.put("cai",bean.getCai());
				value.put("school",bean.getSchool());
				myDatabase.insert(content,null, value);
				}
				else
				{
					ContentValues value = new ContentValues();
					value.put("title",bean.getTitle());
					value.put("textcontent",bean.getTextcontent());
					value.put("zan",bean.getZan());
					value.put("cai",bean.getCai());
					value.put("school",bean.getSchool());
					myDatabase.update(content, value,sql,null);
				}
				
				
			
			}
			
		
	}
	
	public synchronized List<ContentBean> get(String myschool)
	{
		//获取数据库中的数据。
		myDatabase = getWritableDatabase();
		List<ContentBean> list = new LinkedList<ContentBean>();
		String sql = school+" = "+"\""+myschool+"\";";
		Cursor c = myDatabase.query(content,null,sql,null,null,null,null);
		
			while(c.moveToNext())
			{
				ContentBean bean = new ContentBean();
				bean.setTitle(c.getString(0));
				bean.setTextcontent(c.getString(1));
				
				bean.setZan(c.getInt(2));
				bean.setCai(c.getInt(3));
				bean.setSchool(c.getString(4));
				list.add(bean);
			}
			
		myDatabase.close();
		return list;
	}

}
