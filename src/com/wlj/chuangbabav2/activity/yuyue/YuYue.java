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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.wlj.chuangbabav2.activity.yuyue.YuYue_Yes.dsds;
import com.wlj.chuangbabav2.bean.User;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragment;
import com.wlj.util.ExecutorServices;
import com.wlj.util.Log;
import com.wlj.util.MathUtil;
import com.wlj.util.StringUtils;
import com.wlj.util.UIHelper;

public class YuYue extends Fragment implements OnClickListener,dsds {
	
	protected Context mContext;
	protected View view;// inflater的布局
	
	protected Intent intent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity().getApplicationContext();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			callweb();
			getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		Log.e("dd", "YuYue  onCreateView");
		return inflater.inflate(R.layout.yuyue_, null);
	}
	
	protected void callweb() {
		UIHelper.loading("加载中……", getActivity());
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
				handle2.sendMessage(message);
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
	
	protected Handler handle2 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				UIHelper.loadingClose();
				BaseList baselist = (BaseList) msg.obj;
				if(baselist != null){
					List<Base> baselist2 = baselist.getBaselist();
					
					if(baselist2.size() > 0){
						//you
						Fragment yuyue_yes = new YuYue_Yes();
						Bundle bundle = new Bundle();
						bundle.putSerializable("base", baselist2.get(0));
						yuyue_yes.setArguments(bundle);
						changeFragment(yuyue_yes, 0);
					}else{
						
						YuYue_No yuyue_no = new YuYue_No();
						changeFragment(yuyue_no, 1);
					}
					
				}
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
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.edityuyue:
			
			break;
		case R.id.delyuyue:
			
			break;
		}
	}
	
	protected int oldposition = -1;
	protected Fragment findFragment = null;
	protected Fragment center_Fragment;

	protected void changeFragment(Fragment fragment,int position) {
		
		String fragmentName = fragment.getClass().getSimpleName();

		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
//		Fragment findfragment = fm.findFragmentByTag(fragmentName+position);
		if (center_Fragment != null) {
			ft.detach(center_Fragment);
		}
//
//		if (findfragment != null) {
//			ft.attach(findfragment);
//			center_Fragment = findfragment;
//		} else {
			center_Fragment = fragment;
			ft.add(R.id.framelay_center, center_Fragment, fragmentName+position);
//		}
		ft.commitAllowingStateLoss();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == 11){
			//去登录
//			if(resultCode == 22){
				//失败或者返回  就什么都不做
				((Main)getActivity()).mTabHost.setCurrentTab(0);
//			}else if(resultCode == 55){
				//成功
				((Main)getActivity()).mTabHost.setCurrentTab(0);
//			}
			
		}
		
	}

	@Override
	public void tset(com.wlj.chuangbabav2.bean.YuYue yuyue) {
		
		YuYue_No yuyue_no = new YuYue_No();
		Bundle bundle = new Bundle();
		bundle.putSerializable("base", yuyue);
		yuyue_no.setArguments(bundle);
		changeFragment(yuyue_no, 1);
	}

}
