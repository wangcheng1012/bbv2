package com.wlj.chuangbabav2.activity.my.news;

import android.graphics.drawable.Drawable;
import android.os.Message;

import com.wlj.chuangbabav2.R;
import com.wlj.ui.BaseFragmentActivity2;

public class MyNews extends BaseFragmentActivity2 {

	@Override
	protected void beforeTitle() {

		title.setText("消息");
		
		Drawable drawableback = getResources().getDrawable(R.drawable.back);
		drawableback.setBounds(0, 0, drawableback.getMinimumWidth(),drawableback.getMinimumHeight());
		left.setCompoundDrawables(drawableback, null, null, null);
	}

	@Override
	protected int setlayout() {
		return R.layout.my_news;
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

}
