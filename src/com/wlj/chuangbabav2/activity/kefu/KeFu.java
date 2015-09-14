package com.wlj.chuangbabav2.activity.kefu;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.wlj.chuangbabav2.MyBaseFragment;
import com.wlj.chuangbabav2.R;
import com.wlj.ui.BaseFragment;

public class KeFu extends MyBaseFragment implements OnClickListener {
	private Animation translate_Animation;

	@Override
	protected int getlayout() {
		return R.layout.fragment_main_kefu;
	}

	@Override
	protected void initView(View view) {
		
		TextView title = (TextView)view.findViewById(R.id.title);
		title.setText("客服");
		
		translate_Animation = AnimationUtils.loadAnimation(getActivity(),R.anim.translate);

		view.findViewById(R.id.call1).setOnClickListener(this);
		view.findViewById(R.id.fuwuandbaozhang).setOnClickListener(this);

	}

	private void showdialog() {

		final AlertDialog dlg = new AlertDialog.Builder(getActivity()).create();
		Window window = dlg.getWindow();
		dlg.show();
		window.setContentView(R.layout.fragment_main_kefu_phone);
		window.findViewById(R.id.nulltr).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		});
		window.findViewById(R.id.space).setAnimation(translate_Animation);
		window.findViewById(R.id.space).startAnimation(translate_Animation);
		window.findViewById(R.id.canl).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dlg.dismiss();
					}
				});
		window.findViewById(R.id.phone400).setOnClickListener(this);
		window.findViewById(R.id.tel).setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.call1:
			showdialog();
			break;
		case R.id.fuwuandbaozhang:
			Intent fuwuandbaozhang = new Intent(getActivity(), FuWu.class);
			fuwuandbaozhang.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(fuwuandbaozhang);
			break;
		case R.id.phone400:
		    Intent intent = new Intent(Intent.ACTION_CALL);  
		    intent.setData(Uri.parse("tel:4001-023-768"));  
		    startActivity(intent);  
			break;
		case R.id.tel:
			Intent intent2 = new Intent(Intent.ACTION_CALL);  
			intent2.setData(Uri.parse("tel:023-45587702"));  
			startActivity(intent2);  
			break;
		}
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
	protected Object callWebMethod() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
