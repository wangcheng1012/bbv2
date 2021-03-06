package com.wlj.chuangbabav2.activity.my.set;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.activity.Loading;
import com.wlj.chuangbabav2.activity.Main;
import com.wlj.ui.BaseFragmentActivity2;
import com.wlj.util.UIHelper;
import com.wlj.util.img.ImageFileCache;

public class SetUp extends BaseFragmentActivity2 implements OnClickListener {

	private CBBContext mContext;
	@Override
	protected void beforeTitle() {
		title.setText("设置");
		
		Drawable drawableback = getResources().getDrawable(R.drawable.back);
		drawableback.setBounds(0, 0, drawableback.getMinimumWidth(),drawableback.getMinimumHeight());
		left.setCompoundDrawables(drawableback, null, null, null);
		
	}

	@Override
	protected int setlayout() {
		return R.layout.my_setup;
	}

	@Override
	protected void initView() {
		mContext = (CBBContext) getApplicationContext();
		findViewById(R.id.modifypsw).setOnClickListener(this);
		findViewById(R.id.clean).setOnClickListener(this);
		findViewById(R.id.update).setOnClickListener(this);
		findViewById(R.id.loadingpageset).setOnClickListener(this);
		findViewById(R.id.exitapp).setOnClickListener(this);
		
		
	}

	@Override
	protected void Switch(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViewDate(Object obj) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void rightOnClick() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void liftOnClick() {
		finish();
	}

	@Override
	protected Object callWebMethod() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.modifypsw:
			Intent intent2 = new Intent(getApplicationContext(),ModifyPSW.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent2);
			break;
		case R.id.clean:
			ImageFileCache imageFileCache = new ImageFileCache();
			UIHelper.ToastMessage(mContext, imageFileCache.removeAllCache());
			break;
		case R.id.update:
			mContext.setConfigCheckUp(true);
			break;
		case R.id.loadingpageset:
			
			Intent intent_loading = new Intent(mContext, Loading.class);
			intent_loading.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent_loading);
			break;
		case R.id.exitapp:
			mContext.loginOut();
			Intent intent = new Intent(mContext, Main.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		}
	}

}
