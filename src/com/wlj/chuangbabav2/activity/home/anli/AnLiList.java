package com.wlj.chuangbabav2.activity.home.anli;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;

import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.activity.home.dataTransmission;
import com.wlj.ui.BaseFragmentActivity2;

public class AnLiList extends BaseFragmentActivity2 implements dataTransmission{
	@Override
	protected void beforeTitle() {
		Intent intent2 = getIntent();
		
		title.setText(intent2.getStringExtra("name"));
		
		Drawable drawable = getResources().getDrawable(R.drawable.jiahao);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
		right.setCompoundDrawables(drawable, null, null, null);

		Drawable drawableback = getResources().getDrawable(R.drawable.back);
		drawableback.setBounds(0, 0, drawableback.getMinimumWidth(),drawableback.getMinimumHeight());
		left.setCompoundDrawables(drawableback, null, null, null);
	}

	@Override
	protected int setlayout() {
		return R.layout.fragment_main_home2_al_list;
	}

	@Override
	protected void initView() {
		
	}

	@Override
	protected void Switch(Message msg) {
		
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

	@Override
	public Bundle transmissionName() {
		Intent intent2 = getIntent();
		return intent2.getExtras();
	}


}
