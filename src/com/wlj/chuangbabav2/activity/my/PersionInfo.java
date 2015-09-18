package com.wlj.chuangbabav2.activity.my;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.bean.User;
import com.wlj.ui.BaseFragmentActivity;

public class PersionInfo extends BaseFragmentActivity implements OnClickListener {

	private TextView name;
	private TextView phone;
	private TextView addr;
	private TextView regtime;
	private ImageView persionimageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.my_persioninfo);
		
		initView();
	}

	private void initView() {
		findViewById(R.id.back).setOnClickListener(this);
		
		persionimageView = (ImageView)findViewById(R.id.persionimageView);
		name = (TextView)findViewById(R.id.name);
		phone = (TextView)findViewById(R.id.phone);
		addr = (TextView)findViewById(R.id.addr);
		regtime = (TextView)findViewById(R.id.regtime);
		
		setViewDate();
		
	}

	private void setViewDate() {
		
		User stringExtra = (User)getIntent().getSerializableExtra("base");
		
		name.setText(stringExtra.getName());
		phone.setText(stringExtra.getPhone());
		addr.setText(stringExtra.getAddr());
		regtime.setText(stringExtra.getAddr());
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		}
	}

	

}
