package com.wlj.chuangbabav2.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.bean.User;
import com.wlj.chuangbabav2.web.Md5Util;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragmentActivity;
import com.wlj.util.AppException;
import com.wlj.util.ExecutorServices;
import com.wlj.util.UIHelper;

/**
 * 代理商登录
 * @author wlj
 *
 */
public class PSWLogin extends BaseFragmentActivity implements OnClickListener {

	private EditText loginName, logonPassword;
	private CBBContext mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pswlogin);
		mContext = (CBBContext) getApplicationContext();
		init_title();

		initView();
	}

	private void initView() {
		((Button) findViewById(R.id.login)).setOnClickListener(this);
		loginName = (EditText) findViewById(R.id.loginName);
		logonPassword = (EditText) findViewById(R.id.logonPassword);
		
		((Button) findViewById(R.id.duanxinlogin)).setOnClickListener(this);
		
	}

	private void init_title() {

		TextView title = (TextView) findViewById(R.id.title);
		title.setText("登录");

//		TextView left = (TextView) findViewById(R.id.left);

//		Drawable drawableback = getResources().getDrawable(
//				R.drawable.back_white);
//		drawableback.setBounds(0, 0, drawableback.getMinimumWidth(),
//				drawableback.getMinimumHeight());
//		left.setCompoundDrawables(drawableback, null, null, null);

		// right.setOnClickListener(this);
//		left.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left:
			finish();
			break;
		case R.id.duanxinlogin:
			Intent  intent = new Intent(mContext, RandCodeLogin.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		case R.id.login:
			String name = loginName.getText().toString().trim();
			String password = logonPassword.getText().toString().trim();

			if ("".equals(name)) {
				UIHelper.ToastMessage(mContext, "用户名为空");
				return;
			}

			if ("".equals(password)) {
				UIHelper.ToastMessage(mContext, "密码为空");
				return;
			}
			User u = new User();
			u.setName(name);
			u.setPsw(password);
			login(u);
			break;
		
		}

	}

	private void login(final User u) {
		UIHelper.loading("请稍候……", this);
		ExecutorServices.getExecutorService().execute(new Runnable() {

			@Override
			public void run() {
				
				
				try {
					
					mContext.Login(u);
						
					runOnUiThread(new Runnable() {
						public void run() {
							UIHelper.ToastMessage(mContext, "登录成功");
						}
					});
					setResult(55);
					finish();
						
				} catch ( Exception e) {
					AppException.http(e);
					runOnUiThread(new Runnable() {
						public void run() {
							UIHelper.ToastMessage(mContext, "登录失败");
						}
					});
				}
				
				runOnUiThread(new Runnable() {
					public void run() {
						UIHelper.loadingClose();
					}
				});
				
				
			}
		});

	}
	
	@Override
	public void onBackPressed() {

		Intent intent = getIntent();
		String activityname = intent.getStringExtra("activityname");
		try {
			intent.setClass(mContext, Class.forName(activityname));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		setResult(22, intent);
		finish();
	}
	
	
	
}
