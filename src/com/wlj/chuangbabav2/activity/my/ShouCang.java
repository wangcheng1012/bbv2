package com.wlj.chuangbabav2.activity.my;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;

import com.wlj.bean.Base;
import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.activity.home.dataTransmission;
import com.wlj.chuangbabav2.bean.Menchuang;
import com.wlj.chuangbabav2.bean.User;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragmentActivity2;

public class ShouCang extends BaseFragmentActivity2 {

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
		return null;
	}

}
