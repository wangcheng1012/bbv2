package com.wlj.chuangbabav2.activity.yuyue;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.activity.Main;
import com.wlj.chuangbabav2.activity.PSWLogin;
import com.wlj.chuangbabav2.bean.YuYue;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.util.ExecutorServices;
import com.wlj.util.Log;
import com.wlj.util.MathUtil;
import com.wlj.util.StringUtils;
import com.wlj.util.UIHelper;

public class YuYue_Yes extends Fragment implements OnClickListener {
	
	protected Context mContext;
	protected View view;// infalte的布局
	protected Intent intent;
	
	private TextView yuyuContext;
	private Button edityuyue,delyuyue;
	private com.wlj.chuangbabav2.bean.YuYue yuyuebean;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mContext = getActivity().getApplicationContext();
		Log.w("dd", "onCreate");
	}
	/**
	 * 每次加载 都会调用onCreateView
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
			Bundle arguments = getArguments();
			yuyuebean = (YuYue) arguments.getSerializable("base");
		
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
			
		return view;
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.edityuyue:
			
			((dsds)getParentFragment()).tset(yuyuebean);
			break;
		case R.id.delyuyue:
			delYuYue();
			break;
		}
	}

	private void delYuYue() {
		
		UIHelper.loading("提交中……", getActivity());
		ExecutorServices.getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>(); 
				map.put("url", URLs.deleteYuyue); 
				map.put("user_Type", ""); 
				map.put("yuyueId", yuyuebean.getId());  
				
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
		
	}

	protected Handler handle2 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:
				UIHelper.loadingClose();
				UIHelper.ToastMessage(getActivity(), "删除成功");
				((Main)getActivity()).mTabHost.setCurrentTab(0);
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
	
	protected int oldposition = -1;
	protected Fragment findFragment = null;
	protected Fragment center_Fragment;

	protected void changeFragment(Fragment fragment,int position) {
		
		String fragmentName = fragment.getClass().getSimpleName();

		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment findfragment = fm.findFragmentByTag(fragmentName+position);
		if (center_Fragment != null) {
			ft.detach(center_Fragment);
		}

		if (findfragment != null) {
			ft.attach(findfragment);
			center_Fragment = findfragment;
		} else {
			center_Fragment = fragment;
			ft.add(R.id.framelay_center, center_Fragment, fragmentName+position);
		}
		ft.commitAllowingStateLoss();
	}
	
	interface dsds{
		void tset(YuYue yuyue);
	}
	
}
