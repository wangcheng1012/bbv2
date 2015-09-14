package com.wlj.chuangbabav2.activity.yuyue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wlj.bean.Base;
import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.MyBaseFragment;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.activity.Main;
import com.wlj.chuangbabav2.activity.PSWLogin;
import com.wlj.chuangbabav2.bean.User;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragment;
import com.wlj.util.ExecutorServices;
import com.wlj.util.Log;
import com.wlj.util.MathUtil;
import com.wlj.util.StringUtils;
import com.wlj.util.UIHelper;

public class YuYue_No___ extends Fragment implements OnClickListener {
	
	protected Context mContext;
	protected View view;// infalte的布局
	private EditText name,phone,shuoming;
	private Spinner sheng,shi;
	private Button submintyuyue;
	
	protected Intent intent;
	
	private int state = state_unknow;
	
	private final static int state_unknow = 0;
	private final static int state_have = 1;
	private final static int state_null = 2;
	private final static int state_back = 3;
	
	private TextView yuyuContext;
	private Button edityuyue,delyuyue;
	private com.wlj.chuangbabav2.bean.YuYue yuyuebean;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity().getApplicationContext();
		Log.w("dd", "onCreate");
	}
	/**
	 * 每次加载 都会调用onCreateView
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		state = state_unknow;
		callweb();
		int i = 0;
		while(state != state_back  && i < 1000){
			i++;
			Log.w("dd", i+"");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Log.e("state", state+"");
		
		BaseList baselist = (BaseList) callWebMethod;
		if(baselist == null){
			((Main)getActivity()).mTabHost.setCurrentTab(0);
			return null;
		}
		List<Base> baselist2 = baselist.getBaselist();
		if(baselist2.size() > 0){
			state = state_have;
			yuyuebean = (com.wlj.chuangbabav2.bean.YuYue)baselist2.get(0);
		}else{
			state = state_null;
		}
		
		if(state == state_have){
			view = inflater.inflate(R.layout.fragment_main_yuyue_xiangqing, null);
			yuyuContext = (TextView)view.findViewById(R.id.yuyuContext);
			edityuyue = (Button)view.findViewById(R.id.edityuyue);
			delyuyue = (Button)view.findViewById(R.id.delyuyue);
			
			edityuyue.setOnClickListener(this);
			delyuyue.setOnClickListener(this);
			
			ForegroundColorSpan redSpan1 = new ForegroundColorSpan(Color.parseColor("#FF9966"));
			ForegroundColorSpan redSpan2 = new ForegroundColorSpan(Color.parseColor("#FF9966"));
			ForegroundColorSpan redSpan3 = new ForegroundColorSpan(Color.parseColor("#FF9966"));
			
			String text = yuyuebean.getName()
			+ "：您于"
			+ StringUtils.getTime(MathUtil.parseLong(yuyuebean.getYuyueTime()),"yyyy/MM/dd")+ "在 " 
			+  yuyuebean.getSheng() + " "
			+ yuyuebean.getShi() + " 使用了" 
			+ yuyuebean.getYuyuePhone()
			+ "的联系电话向我们预约了订制门窗服务，我们会在1个工作日内和您联系，请保持电话畅通！";
			
			SpannableStringBuilder ssb = new SpannableStringBuilder(text);
			ssb.setSpan(redSpan1, 0,text.indexOf("：您于") , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			ssb.setSpan(redSpan2, text.indexOf("在")+1,text.indexOf("使用了") , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			ssb.setSpan(redSpan3, text.indexOf("使用了")+3,text.indexOf("的联系电话") , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			yuyuContext.setText(ssb);
			
			((TextView)view.findViewById(R.id.title)).setText("快速预约");
			
		}else if(state == state_null){
			view = inflater.inflate(R.layout.fragment_main_yuyue, null);
			name = (EditText)view.findViewById(R.id.name);
			phone = (EditText)view.findViewById(R.id.phone);
			shuoming = (EditText)view.findViewById(R.id.shuoming);
			sheng = (Spinner)view.findViewById(R.id.sheng);
			shi = (Spinner)view.findViewById(R.id.shi);
			submintyuyue = (Button)view.findViewById(R.id.submintyuyue);
			submintyuyue.setOnClickListener(this);
			((TextView)view.findViewById(R.id.title)).setText("快速预约");
		}
		
		return view;
	}
	

	protected Handler handle2 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:
				UIHelper.loadingClose();
				UIHelper.ToastMessage(getActivity(), "预约成功");
				((Main)getActivity()).mTabHost.setCurrentTab(0);
				break;
			case 1:
				UIHelper.loadingClose();
				break;
			case -1:
				Exception e = (Exception)msg.obj;
				UIHelper.ToastMessage(getActivity(), e.getMessage());
				UIHelper.loadingClose();
//				{"state":1,"description":"登录信息过期,请重新登录!"}
				if("登录信息过期,请重新登录!".equals(e.getMessage())){
					Intent intent = new Intent(mContext, PSWLogin.class);
					intent.putExtra("activityname","com.wlj.chuangbabav2.activity.Main") ;
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, 11);
				}
				
				break;
			}
		}
	};
	private Object callWebMethod;
	protected void callweb() {
		UIHelper.loading("加载中……", getActivity());
		ExecutorServices.getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				Message message = Message.obtain();
				try {
					callWebMethod = callWebMethod();
					message.what = 1;
				} catch (Exception e) {
					message.what = -1;
					message.obj = e;
				}
				handle2.sendMessage(message);
				state = state_back;
			}
			
		});
	}

	protected Object callWebMethod() throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", URLs.getYuyueList);
		map.put("user_Type", User.type_huiyuan);
		map.put("state", com.wlj.chuangbabav2.bean.YuYue.state_ing);
		
		return ((CBBContext)mContext).Request(getActivity(), map, new com.wlj.chuangbabav2.bean.YuYue());
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.submintyuyue:
			UIHelper.loading("提交中……", getActivity());
			ExecutorServices.getExecutorService().execute(new Runnable() {
				
				@Override
				public void run() {
					Map<String, Object> map = new HashMap<String, Object>(); 
					map.put("url", URLs.yuyue); 
//					map.put("bujiami", ""); 
					
					map.put("yuyuePhone", phone.getText()+"");  
					map.put("sheng", sheng.getSelectedItem());  
					map.put("shi", shi.getSelectedItem());  
					map.put("message", shuoming.getText()+"");  
					map.put("name", name.getText()+"");  
					map.put("orderId", (System.currentTimeMillis()+"").substring(1));
					
					Message message = Message.obtain();
					try {
						((CBBContext)mContext).Request(getActivity(), map, null);
						message.what = 2;
					} catch (Exception e) {
						e.printStackTrace();
						message.what = -1;
						message.obj = e;
					}
					handle2.sendMessage(message);
				}
			});
			
			
			
			break;
		case R.id.edityuyue:
			
			break;
		case R.id.delyuyue:
			
			break;
		}
	}


	
}
