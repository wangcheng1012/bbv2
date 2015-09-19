package com.wlj.chuangbabav2.activity.my;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlj.bean.Base;
import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.bean.User;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragmentActivity;
import com.wlj.util.ExecutorServices;
import com.wlj.util.MathUtil;
import com.wlj.util.StringUtils;
import com.wlj.util.UIHelper;

public class PersionInfo extends BaseFragmentActivity implements
		OnClickListener {

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

		persionimageView = (ImageView) findViewById(R.id.persionimageView);
		name = (TextView) findViewById(R.id.name);
		phone = (TextView) findViewById(R.id.phone);
		addr = (TextView) findViewById(R.id.addr);
		regtime = (TextView) findViewById(R.id.regtime);

		callweb();
	}

	private void callweb() {
		ExecutorServices.getExecutorService().execute(new Runnable() {

			@Override
			public void run() {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("url", URLs.getUserInfo);
				map.put("user_Type", "");
				Message message = Message.obtain();
				try {
					message.what = 1;
					message.obj = ((CBBContext) getApplicationContext()).Request(PersionInfo.this, map, new User());

				} catch (Exception e) {
					e.printStackTrace();
					message.what = -1;
					message.obj = e;
				}
				handle.sendMessage(message);
			}
		});
	}

	private Handler handle = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				setViewDate(msg.obj);
				break;
			case -1:
				Exception e = (Exception) msg.obj;
				UIHelper.ToastMessage(getApplicationContext(), e.getMessage());
				break;
			}

		};

	};

	private void setViewDate(Object obj) {

		if (obj == null)return;
		BaseList baseList = (BaseList) obj;
		List<Base> baselist2 = baseList.getBaselist();
		if (baselist2.get(0) == null || baselist2.get(0).equals("null"))return;
		User menchuang = (User) baselist2.get(0);
		
		name.setText(menchuang.getName());
		phone.setText(menchuang.getPhone());
		addr.setText(menchuang.getAddr());
		regtime.setText(StringUtils.getTime(menchuang.getRegtime(), "yyyy-MM-dd"));
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
