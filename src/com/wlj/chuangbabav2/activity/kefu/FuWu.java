package com.wlj.chuangbabav2.activity.kefu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.wlj.adapter.CommonAdapter;
import com.wlj.adapter.ViewHolder;
import com.wlj.bean.Base;
import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.bean.Menchuang;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragmentActivity2;
import com.wlj.util.img.LoadImage;

public class FuWu extends BaseFragmentActivity2 {

	private ImageView fuwubanner;
	private ListView listView1,listView2;
	CBBContext  cbbContext ;
	
	@Override
	protected void beforeTitle() {
		title.setText("服务与保障");
	}

	@Override
	protected int setlayout() {
		
		return R.layout.kefu_fuwu;
	}
	
	@Override
	protected void initView() {
		cbbContext = (CBBContext)getApplicationContext();
		
		fuwubanner  = (ImageView)findViewById(R.id.imageView1);
		listView1  = (ListView)findViewById(R.id.listView1);
		listView2  = (ListView)findViewById(R.id.listView2);
		/*
		 * 顶部图片
		 */
		callweb();
	}

	@Override
	protected void Switch(Message msg) {
		Object obj = msg.obj;
		if (obj == null) {
			return;
		}
		BaseList baseList = (BaseList) obj;
		List<Base> baselist2 = baseList.getBaselist();
		if (baselist2.get(0) == null || baselist2.get(0).equals("null")) {
			return;
		}
		ListView templist = null;
		switch (msg.what) {
		case 2:
			templist = listView1;
			break;
		case 3:
			templist = listView2;
			break;
		}
		
		if (templist != null) {
			templist.setAdapter(new CommonAdapter<Base>(this, baselist2,
					R.layout.item_gonglue_listview) {

				@Override
				public View getListItemview(ViewHolder viewHolder, View view,
						Base item, int position, ViewGroup parent) {
					Menchuang menchuang = (Menchuang) item;

					viewHolder.setText(R.id.textView1, menchuang.getName());

					view.setTag(R.id.tag_first, item);
					return null;
				}

			});

			templist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent intent = new Intent(getApplicationContext(),FuWuXiangQing.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("id",((Base) arg1.getTag(R.id.tag_first)).getId());
					startActivity(intent);
				}
			});

		}
	}

	@Override
	protected void setViewDate(Object obj) {
		if(obj == null){
			return;
		}
		BaseList baseList = (BaseList)obj;
		List<Base> baselist2 = baseList.getBaselist();
		if(baselist2.get(0) == null||baselist2.get(0).equals("null")){
			return;
		}
		Menchuang menchuang = (Menchuang) baselist2.get(0);
		
		LoadImage.getinstall().addTask(URLs.HOST+menchuang.getPic(), fuwubanner);
		LoadImage.getinstall().doTask();
	}

	@Override
	protected void rightOnClick() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void liftOnClick() {
		// TODO Auto-generated method stub

	}

	@Override
	protected Object callWebMethod() throws Exception {

		// cbb
		Map<String, Object> cbbmap = new HashMap<String, Object>();
		cbbmap.put("url", URLs.getPubByFenleiid);
		cbbmap.put("bujiami", "");
		cbbmap.put("fenleiid", "55f793b2d812a80d22d45866");

		Message obtain = Message.obtain();
		obtain.what = 2;
		obtain.obj = cbbContext.Request(this, cbbmap, new Menchuang());
		handle.sendMessage(obtain);
		// fuwu
		Map<String, Object> fuwumap = new HashMap<String, Object>();
		fuwumap.put("url", URLs.getPubByFenleiid);
		fuwumap.put("bujiami", "");
		fuwumap.put("fenleiid", "55f793c0d812a80d22d45867");

		Message fuwumessage = Message.obtain();
		fuwumessage.what = 3;
		fuwumessage.obj = cbbContext.Request(this, fuwumap, new Menchuang());
		handle.sendMessage(fuwumessage);

		//banner
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", URLs.getPubById);
		map.put("bujiami", "");
		map.put("id", "55f7b131d812a80d22d4586b");
		return cbbContext.Request(this, map, new Menchuang());
	}

}
