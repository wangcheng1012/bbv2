package com.wlj.chuangbabav2.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.wlj.bean.Base;
import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.R.drawable;
import com.wlj.chuangbabav2.bean.LoadingPic;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragmentActivity;
import com.wlj.util.DpAndPx;
import com.wlj.util.ExecutorServices;
import com.wlj.util.GetResourceImage;
import com.wlj.util.UIHelper;
import com.wlj.util.img.LoadImage;
import com.wlj.widget.MyScrollLayout;
import com.wlj.widget.SwitchViewDemoActivity;
import com.wlj.widget.SwitchViewPagerDemoActivity;
/**
 * 
 * @author wlj
 *
 */
public class Loading extends BaseFragmentActivity{

	private CBBContext cbbContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		cbbContext = (CBBContext)getApplicationContext();
		callweb();
	}
	
	protected void addSwitchPage_(MyScrollLayout mScrollLayout,Object obj) {
		if(obj == null){
			
		}else{
			BaseList baselist = (BaseList)obj;
			List<Base> list = baselist.getBaselist();
			
			for (int i = 0; i < list.size(); i++) {
				LoadingPic loadingPic = (LoadingPic)list.get(i);
				if(i == list.size()-1){
					RelativeLayout fl3 = new RelativeLayout(getApplicationContext());
					RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					ImageView fl1 = new ImageView(getApplicationContext());
					
					fl1.setScaleType(ScaleType.FIT_XY);
					LoadImage.getinstall().addTask(URLs.HOST+loadingPic.getPicurl(), fl1);
					LoadImage.getinstall().doTask();
					
					fl3.addView(fl1,layoutParams);
					addLiJiTiYan(fl3);
					mScrollLayout.addView(fl3);
					
				}else{
					ImageView fl1 = new ImageView(getApplicationContext());
					
					fl1.setScaleType(ScaleType.FIT_XY);
					LoadImage.getinstall().addTask(URLs.HOST+loadingPic.getPicurl(), fl1);
					LoadImage.getinstall().doTask();
					mScrollLayout.addView(fl1);
				}
			}
		}
    	mScrollLayout.startAutotPay();
	}
		
	private void GoToMain() {
		
		Intent intent = new Intent(getApplicationContext(), Main.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}
		
	private void addLiJiTiYan(ViewGroup viewgroup){
			
			//立即体验
	    	TextView textView = new TextView(getApplicationContext());
	    	textView.setPadding(DpAndPx.dpToPx(getApplicationContext(), 50), DpAndPx.dpToPx(getApplicationContext(), 8), DpAndPx.dpToPx(getApplicationContext(), 50), DpAndPx.dpToPx(getApplicationContext(), 8));
	    	textView.setBackgroundResource(R.drawable.red);
	    	textView.setText("立即体验");
	    	textView.setTextColor(Color.WHITE);
	    	textView.setTextSize(17f);
	    	textView.setGravity(Gravity.CENTER_HORIZONTAL);
	    	RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    	layoutParams.bottomMargin= DpAndPx.dpToPx(getApplicationContext(), 80);
	    	layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	    	layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
	    	
	    	viewgroup.addView(textView,layoutParams);
	    	
	    	textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//登录跳转
					GoToMain();
				}

			});
			
		}
		
		
	private Handler handle = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				setViewDate(msg.obj);
				break;
			case -1:
				Exception e = (Exception)msg.obj;
				UIHelper.ToastMessage(getApplicationContext(), e.getMessage());
				break;
			
			}
		}

	};
	
	private void setViewDate(final Object obj) {
		View view = new SwitchViewDemoActivity(getApplicationContext(),null) {
			
			@Override
			protected void addSwitchPage(MyScrollLayout mScrollLayout) {
				addSwitchPage_(mScrollLayout,obj);
			}
			
			@Override
			protected void scrollPoint(RelativeLayout layout) {
				setPointResId(R.drawable.point_selector);
				super.scrollPoint(layout);
			}
		}.createview();
		
		setContentView(view); 
	}
	
	private void callweb() {
		
		ExecutorServices.getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				Message message = Message.obtain();
				try {
					message.what = 1;
					message.obj = callWebMethod();
				} catch (Exception e) {
					message.what = -1;
					message.obj = e;
				}
				handle.sendMessage(message);
			}

			
		});
	}
	
	private Object callWebMethod() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", URLs.getPubByFenleiid);
		map.put("bujiami", "");
		map.put("fenleiid", "554c8ac9d6c45923169ce92c");
//		map.put("user_Type", value);
		
		return cbbContext.Request(this, map, new LoadingPic());
	}
}
