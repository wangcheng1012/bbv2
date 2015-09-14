package com.wlj.chuangbabav2;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.wlj.ui.BaseFragmentActivity2;

public abstract class MybaseFramentActivity extends BaseFragmentActivity2 {

	@Override
	protected int setlayout() {
		
		return R.layout.activity_frame;
	}

	protected void appendMainBody(int resId) {
		LinearLayout mainBody = (LinearLayout) findViewById(R.id.layMainBody);
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(resId, null);
		LinearLayout.LayoutParams layoutParams = new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		mainBody.addView(view, layoutParams);
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
		
	}

	@Override
	protected Object callWebMethod() throws Exception {
		return null;
	}

}
