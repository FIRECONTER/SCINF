package cn.scinf.services;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
//执行后台的获取数据的操作。
public class Myservice extends Service {
	private Thread mythread;
	
	public Myservice() {
		// TODO Auto-generated constructor stub
		mythread = new Thread(new Myrun());
		mythread.setDaemon(true);//设置为守护线程
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		mythread = new Thread(new Myrun());
		mythread.setDaemon(true);//设置为守护线程
		
		mythread.start();
		return START_NOT_STICKY;
	}
	//操作数据库。插入数据。
	class Myrun implements Runnable{
		@Override
		public void run() {
			while(true)
			{
				
				try {
					Thread.sleep(1000*20);
					System.out.println("抓取");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mythread.interrupt();//终止运行
	}
	
	
}
