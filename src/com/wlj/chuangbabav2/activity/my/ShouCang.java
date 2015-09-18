package com.wlj.chuangbabav2.activity.my;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;

import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.activity.home.dataTransmission;
import com.wlj.ui.BaseFragmentActivity2;

public class ShouCang extends BaseFragmentActivity2 implements dataTransmission {

	@Override
	protected void beforeTitle() {
		title.setText("收藏");
		
		Drawable drawableback = getResources().getDrawable(R.drawable.back);
		drawableback.setBounds(0, 0, drawableback.getMinimumWidth(),drawableback.getMinimumHeight());
		left.setCompoundDrawables(drawableback, null, null, null);
	}

	@Override
	protected int setlayout() {
		return R.layout.my_sc;
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

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
	public Bundle transmissionName() {
		Bundle bundle = new Bundle();
		bundle.putString("fenleiid", "");
		bundle.putString("name", "");
		return bundle;
	}

}
