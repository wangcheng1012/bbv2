package com.wlj.chuangbabav2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.activity.home.Home2;
import com.wlj.chuangbabav2.activity.kefu.KeFu;
import com.wlj.chuangbabav2.activity.my.My;
import com.wlj.chuangbabav2.activity.yuyue.YuYue;
import com.wlj.ui.BaseFragmentActivity;
import com.wlj.util.AppConfig;
//import com.wlj.chuangbabav2.FragmentTabHost;

public class Main extends BaseFragmentActivity   {

	/**
	 * FragmentTabhost
	 */
	public FragmentTabHost mTabHost;

	/**
	 * 布局填充器
	 */
	private LayoutInflater mLayoutInflater;

	/**
	 * Fragment数组界面
	 */
	private Class mFragmentArray[] = { Home2.class, YuYue.class, KeFu.class,
			My.class };
	/**
	 * 存放图片数组
	 */
	private int mImageArray[] = { R.drawable.tab_home_btn,
			R.drawable.tab_yuyue_btn, R.drawable.tab_kefu_btn,
			R.drawable.tab_my_btn };

	/**
	 * 选修卡文字
	 */
	private String mTextArray[] = { "首页", "预约", "客服", "我的" };

	/**
	 * 
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		mLayoutInflater = LayoutInflater.from(this);

		// 找到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		// 内容
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		// 得到fragment的个数
		int count = mFragmentArray.length;
		for (int i = 0; i < count; i++) {
			// 给每个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i]).setIndicator(
					getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, mFragmentArray[i], null);
			// 设置Tab按钮的背景
			// mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}
		mTabHost.getTabWidget().getChildAt(1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mTabHost.getCurrentTab() != 1) {//一定要判断这个是为了防止阻碍切换事件  
					String key = ((CBBContext)getApplicationContext()).getProperty(AppConfig.CONF_KEY);
					if(key == null || "".equals(key) || "null".equals(key)){//这个判断 不能确认，其实没多大用
						//登录
						Intent intent = new Intent(getApplicationContext(), PSWLogin.class);
						intent.putExtra("activityname","com.wlj.chuangbabav2.activity.Main") ;
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivityForResult(intent, 11);
					}else{
						
						 mTabHost.setCurrentTab(1);  
					}
                   
                }else{  

                //做你要做的事情  
                  }  				
			}
		});
		mTabHost.getTabWidget().getChildAt(3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mTabHost.getCurrentTab() != 3) {//一定要判断这个是为了防止阻碍切换事件  
					String key = ((CBBContext)getApplicationContext()).getProperty(AppConfig.CONF_KEY);
					if(key == null || "".equals(key) || "null".equals(key)){//这个判断 不能确认，其实没多大用
						//登录
						Intent intent = new Intent(getApplicationContext(), PSWLogin.class);
						intent.putExtra("activityname","com.wlj.chuangbabav2.activity.Main") ;
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivityForResult(intent, 33);
					}else{
						
						mTabHost.setCurrentTab(3);  
					}
					
				}else{  
					
					//做你要做的事情  
				}  				
			}
		});
		
	}

	/**
	 * 
	 * 给每个Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = mLayoutInflater.inflate(R.layout.item_tab_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageArray[index]);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextArray[index]);

		return view;
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		
		if(arg0 == 11){
			//去登录
			if(arg1 == 22){
				//失败或者返回  就什么都不做
				
			}else if(arg1 == 55){
				//成功
				mTabHost.setCurrentTab(1);
			}
			
		}
		if(arg0 == 33){
			//去登录
			if(arg1 == 22){
				//失败或者返回  就什么都不做
				
			}else if(arg1 == 55){
				//成功
				mTabHost.setCurrentTab(3);
			}
			
		}
	}

}
