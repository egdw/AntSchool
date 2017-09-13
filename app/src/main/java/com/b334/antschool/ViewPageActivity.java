package com.b334.antschool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.b334.adapter.ListViewAdapter;
import com.b334.antschool.R;
import com.b334.data.ListViewData;
import com.b334.entities.ImagUrl;
import com.b334.entities.ImageForViewPage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPageActivity extends Activity {

	private final static String URL = "https://hdy.im/AntSchool/";

	private ViewPager mViewPaper;
	private List<ImageView> images;
	private List<View> dots;
	private ScrollView sv;
	private ListView listview;
	private ListViewData lvData;
	private ListViewAdapter lvAdapter;
	private ImageButton btnHome;
    private ImageButton btnCourse;
    private ImageButton btnMy;
	private int currentItem;
	// 记录上一次点的位置
	private int oldPosition = 0;
	// 存放图片的id
	//private int[] imageIds = new int[] { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e };
	// 存放图片的标题
	private String[] titles = new String[] { "巩俐不低俗，我就不能低俗", "扑树又回来啦！再唱经典老歌引万人大合唱", "揭秘北京电影如何升级", "乐视网TV版大派送",
			"热血屌丝的反杀" };
	private TextView title;
	private ViewPagerAdapter viewPagerAdapter;
	private ScheduledExecutorService scheduledExecutorService;
	
	//XUtils下载图片
	public static void downloadImagesByUrl(final Context context, String url, final List<String> filesPath) {  
	       /* 
	       获取保存路径：手机SD卡1存储 storage/sdcard/Android/data/应用的包名/files 
	       Genymotion模拟器的路径：/storage/emulated/0/Android/data/com.atguigu.zhuatutu/files 
	        */  
	       File filesDir = context.getExternalFilesDir(null);  
	       //获取文件名:/february_2016-001.jpg  
	       String fileName = url.substring(url.lastIndexOf("/"));  
	       //存到本地的绝对路径  
	       final String filePath = filesDir + "/AntSchool" + fileName;  
	       File file = new File(filesDir + "/AntSchool");  
	       //如果不存在  
	       if (!file.exists()) {  
	           //创建  
	           file.mkdirs();  
	       }  
	  
	       RequestParams entity = new RequestParams(url);  
	       entity.setSaveFilePath(filePath);  
	       x.http().get(entity, new Callback.CommonCallback<File>() {  
	           @Override  
	           public void onSuccess(File result) {  
	               filesPath.add(result.getAbsolutePath());  
	               LogUtil.e("onSuccess：" + result.getAbsolutePath());  
	           }  
	  
	           @Override  
	           public void onError(Throwable ex, boolean isOnCallback) {  
	               LogUtil.e("onError ");  
	               //Toast.makeText(x.app(),"网络错误，下载失败",Toast.LENGTH_SHORT).show();  
	           }  
	  
	           @Override  
	           public void onCancelled(CancelledException cex) {  
	               LogUtil.e("onCancelled ");  
	           }  
	  
	           @Override  
	           public void onFinished() {  
	               LogUtil.e("onFinished ");  
	              // Toast.makeText(x.app(), "下载成功,保存到：" + filesPath.get(filesPath.size() - 1), Toast.LENGTH_SHORT).show();  
	           }  
	       });  
	   }  
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static Bitmap getBitmapFromServer(String imagePath) {

		HttpGet get = new HttpGet(imagePath);
		HttpClient client = new DefaultHttpClient();
		Bitmap pic = null;
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			pic = BitmapFactory.decodeStream(is); // 关键是这句代码
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pic;
	}

	private void getURLFromNet(final String imagePath) {
		new Thread() {
			public void run() {
				try {
					URL url = new URL(imagePath);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					Log.i("111111111", "2222222222222222222");
					con.setRequestMethod("POST");
					con.setConnectTimeout(5000*2);
					con.connect();
					Log.i("aaaaaaaaaa", "2222222222222222222");
					InputStream is = con.getInputStream();
					byte[] bytes = new byte[512];
					StringBuffer sb = new StringBuffer();
					int len = -1;
					while ((len = is.read(bytes)) != -1) {
						sb.append(new String(bytes, 0, len));
					}
					System.err.println(sb.toString());
					List<ImageForViewPage> list = JSON.parseArray(sb.toString(), ImageForViewPage.class);
					Log.i("aaaaaaaaaa", "33333333333333333");
					System.err.println(list);
					// 显示的图片
					images = new ArrayList<ImageView>();
					System.out.println(images);
					
					Iterator it = list.iterator();
					while (it.hasNext()) {
						ImageForViewPage img = (ImageForViewPage) it.next();
						String u = img.getUrl();// 得到了里面的元素的属性了
						ImageView imageView = new ImageView(getApplication());
						Bitmap bitmapFromServer = getBitmapFromServer(URL + u);
						imageView.setImageBitmap(bitmapFromServer);
						System.err.println(URL + u);
						images.add(imageView);
					}

					is.close();
					netCompleteHandler.sendEmptyMessage(0x1);
					// 把流转换为bitmap
					// Bitmap bitmap=BitmapFactory.decodeStream(is);
					// Message message=new Message();
					// message.what=0;
					// message.obj=bitmap;
					// 把这个bitmap发送到hanlder那里去处理
					// mHandler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpage);
		init();
		sv.smoothScrollTo(0, 0);
		
		getURLFromNet("https://hdy.im/AntSchool/banner_ad/banners");
		
		
		lvData = new ListViewData();
		ArrayList<HashMap<String,Object>> arrayList = lvData.buildData();
		lvAdapter = new ListViewAdapter(getApplication(), arrayList);
		listview.setAdapter(lvAdapter);
		
		
		
	}

	private void init(){
		listview =(ListView) findViewById(R.id.list);
		mViewPaper = (ViewPager) findViewById(R.id.vp);
		sv = (ScrollView) findViewById(R.id.scrollview);
		btnCourse =(ImageButton) findViewById(R.id.ib_course);
		
		
		
	}
	public Handler netCompleteHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 显示的小点
			dots = new ArrayList<View>();
			dots.add(findViewById(R.id.dot_0));
			dots.add(findViewById(R.id.dot_1));
			dots.add(findViewById(R.id.dot_2));
//			dots.add(findViewById(R.id.dot_3));
//			dots.add(findViewById(R.id.dot_4));

			title = (TextView) findViewById(R.id.title);
			title.setText(titles[0]);

			viewPagerAdapter = new ViewPagerAdapter();
			mViewPaper.setAdapter(viewPagerAdapter);

			mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

				@Override
				public void onPageSelected(int position) {
					title.setText(titles[position]);
					dots.get(position).setBackgroundResource(R.drawable.dot_focused);
					dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);

					oldPosition = position;
					currentItem = position;
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {

				}
			});
		};
	};

	private class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return images.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup view, int position, Object object) {
			view.removeView(images.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			view.addView(images.get(position));
			return images.get(position);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 利用线程池定时执行动画轮播
	 */
	@Override
	protected void onStart() {
		super.onStart();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleWithFixedDelay(new ViewPageTask(), 2, 2, TimeUnit.SECONDS);
	}

	private class ViewPageTask implements Runnable {

		@Override
		public void run() {
			currentItem = (currentItem + 1) % images.size();
			mHandler.sendEmptyMessage(0);
		}
	}

	/**
	 * 接收子线程传递过来的数据
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mViewPaper.setCurrentItem(currentItem);
		};
	};

	@Override
	protected void onStop() {
		super.onStop();
	}

	
	
	

	
	
	
	
}
