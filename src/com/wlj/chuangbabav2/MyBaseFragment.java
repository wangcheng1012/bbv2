package com.wlj.chuangbabav2;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.wlj.R;
import com.wlj.chuangbabav2.activity.PSWLogin;
import com.wlj.ui.BaseFragment;
import com.wlj.ui.BaseFragmentActivity;
import com.wlj.util.AppContext;
import com.wlj.util.ExecutorServices;
import com.wlj.util.UIHelper;

/**
 * 网络访问
 * 
 * @author wlj
 */
public abstract class MyBaseFragment extends BaseFragment {

	protected Intent intent;

	protected Handler handle = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				setViewDate(msg.obj);        
				UIHelper.loadingClose();
				break;
			case -1:
				Exception e = (Exception)msg.obj;
				UIHelper.ToastMessage(getActivity(), e.getMessage());
				UIHelper.loadingClose();
				
				if("登录信息过期,请重新登录!".equals(e.getMessage())){
					Intent intent = new Intent(mContext, PSWLogin.class);
					intent.putExtra("activityname","com.wlj.chuangbabav2.activity.Main") ;
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, 11);
				}
				break;
			default:
				Switch(msg);
				break;
			}
		}
	};
	
	protected void callweb() {
		UIHelper.loading("提交中……", getActivity());
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
	/**
	 * handle 返回 msg处理
	 * @param msg
	 */
	protected abstract void Switch(Message msg);
	/**
	 * 初始化访问网后的返回
	 * @param obj
	 */
	protected abstract void setViewDate(Object obj);
	/**
	 * 初始化访问网
	 * 
	 */
	protected abstract Object callWebMethod( )throws Exception;
}
