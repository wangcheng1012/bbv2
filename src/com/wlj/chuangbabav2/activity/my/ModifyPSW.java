package com.wlj.chuangbabav2.activity.my;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.wlj.chuangbabav2.R;
import com.wlj.ui.BaseFragmentActivity2;

public class ModifyPSW extends BaseFragmentActivity2 implements OnClickListener {

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
		findViewById(R.id.modifypsw).setOnClickListener(this);
		
		
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
			new Intent(getApplicationContext(),ModifyPSW.class);
			break;

		default:
			break;
		}
	}

}
