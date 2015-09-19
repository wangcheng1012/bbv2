package com.wlj.chuangbabav2.activity.my.set;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.wlj.bean.BaseList;
import com.wlj.bean.Result;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.activity.Main;
import com.wlj.chuangbabav2.bean.User;
import com.wlj.chuangbabav2.web.Md5Util;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragmentActivity2;
import com.wlj.util.UIHelper;

public class ModifyPSW extends BaseFragmentActivity2 implements OnClickListener {
private EditText oldpsw;
private EditText newpsw;
private EditText newpswsur;

	@Override
	protected void beforeTitle() {
		title.setText("密码修改");
		
//		Drawable drawableback = getResources().getDrawable(R.drawable.back);
//		drawableback.setBounds(0, 0, drawableback.getMinimumWidth(),drawableback.getMinimumHeight());
//		left.setCompoundDrawables(drawableback, null, null, null);
	}

	@Override
	protected int setlayout() {
		return R.layout.my_setup_modifypsw;
	}

	@Override
	protected void initView() {
		oldpsw = (EditText)findViewById(R.id.oldpsw);
		newpsw = (EditText)findViewById(R.id.newpsw);
		newpswsur = (EditText)findViewById(R.id.newpswsur);
		findViewById(R.id.save).setOnClickListener(this);
	}

	@Override
	protected void Switch(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setViewDate(Object obj) {
		
		((CBBContext)getApplicationContext()).loginOut();
		Intent intent = new Intent(getApplicationContext(), Main.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		
		String description = ((Result)((BaseList)obj).getBaselist().get(0)).getErrorMessage();
		UIHelper.ToastMessage(getApplicationContext(), description);
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
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("url", URLs.resetPasswd); 
		map.put("bujiami", ""); 
		map.put("user_Type", "");
		map.put("oldpwd", Md5Util.MD5Normal(oldpsw.getText()+""));
		map.put("newpwd", newpswsur.getText()+"");
		
		return ((CBBContext)getApplicationContext()).Request(this, map, new Result());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:
			String oldp  =  oldpsw.getText()+"";
			String newp  =  newpsw.getText()+"";
			String newpsur  =  newpswsur.getText()+"";
			
			if("".equals(oldp.trim())){
				UIHelper.ToastMessage(getApplicationContext(), "原密码为空");
				return;
			}
			if("".equals(newp.trim())){
				UIHelper.ToastMessage(getApplicationContext(), "新密码不能为空");
				return;
			}
			if(!newpsur.equals(newp.trim())){
				UIHelper.ToastMessage(getApplicationContext(), "确认密码和新密码不同");
				return;
			}
			
			callweb();
			
			break;

		}
	}

}
