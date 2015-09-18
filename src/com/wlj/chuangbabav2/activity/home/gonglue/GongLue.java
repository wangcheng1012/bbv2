package com.wlj.chuangbabav2.activity.home.gonglue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.wlj.adapter.CommonAdapter;
import com.wlj.adapter.ViewHolder;
import com.wlj.bean.Base;
import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.MyBaseFragment;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.bean.FenLei;
import com.wlj.chuangbabav2.bean.Menchuang;
import com.wlj.chuangbabav2.web.MsgContext;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragment;
import com.wlj.util.ExecutorServices;
import com.wlj.util.img.LoadImage;

public class GongLue extends MyBaseFragment {

	private GridView gridview1;
	private ListView listView1;

	@Override
	protected int getlayout() {
		return R.layout.home2_gonglue;
	}

	@Override
	protected void initView(View view) {
		gridview1 = (GridView) view.findViewById(R.id.gridview1);
		listView1 = (ListView) view.findViewById(R.id.listView1);
		callweb();
		callwebList();
	}

	@Override
	protected void Switch(Message msg) {
		
		BaseList baselist = (BaseList)msg.obj;
		List<Base> baselist2 = baselist.getBaselist();
		if(baselist2 == null || baselist2.size() <= 0 ){
			return;
		}
		listView1.setAdapter(new CommonAdapter<Base>(getActivity(),baselist2,R.layout.item_gonglue_listview) {

			@Override
			public View getListItemview(ViewHolder viewHolder, View view,
					Base item, int position, ViewGroup parent) {
				
				FenLei menchuang = (FenLei) item;
				viewHolder.setText(R.id.textView1, menchuang.getName());
				
//				Drawable drawable = getResources().getDrawable(rightDrawable);
//				drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
//				right.setCompoundDrawables(drawable, null, null, null);
				
				view.setTag(R.id.tag_first, item);
				return null;
			}
			
		});
		listView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				FenLei fl2 = (FenLei) arg1.getTag(R.id.tag_first);
				Intent intent = new Intent(getActivity(),GongLueList.class);
				intent.putExtra("fenleiid", fl2.getId());
				intent.putExtra("name", fl2.getName());
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
	}

	@Override
	protected void setViewDate(Object obj) {

		BaseList baselist = (BaseList) obj;
		List<Base> baselist2 = baselist.getBaselist();
		if (baselist2 == null || baselist2.size() <= 0) {
			return;
		}
		gridview1.setAdapter(new CommonAdapter<Base>(getActivity(), baselist2,
				R.layout.item_gonglue_gridview) {

			@Override
			public View getListItemview(ViewHolder viewHolder, View view,
					Base item, int position, ViewGroup parent) {
				Menchuang menchuang = (Menchuang) item;
				viewHolder.setText(R.id.textView1, menchuang.getName());

				ImageView imageview = (ImageView) view
						.findViewById(R.id.imageview);
				String pic = menchuang.getPic();

				if (!"".equals(pic)) {
					LoadImage.getinstall().addTask(URLs.HOST + pic, imageview);
					LoadImage.getinstall().doTask();
				}
				view.setTag(R.id.tag_first, item);

				return null;
			}
		});

		gridview1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Menchuang menchuang = (Menchuang) arg1.getTag(R.id.tag_first);
				Intent intent = new Intent(getActivity(),
						GongLueXiangQing.class);
				intent.putExtra("fenleiid", menchuang.getId());
				intent.putExtra("name", menchuang.getName());
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

	}

	public static final Integer tuijian_yes = 1;

	@Override
	protected Object callWebMethod() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", URLs.getPubByFenleiid);
		map.put("tuijian", tuijian_yes + "");
		map.put(MsgContext.key_pageSize, "6");
		map.put(MsgContext.key_page, "1");

		map.put("bujiami", "");

		return ((CBBContext) mContext).Request(getActivity(), map,
				new Menchuang());
	}

	private void callwebList() {

		ExecutorServices.getExecutorService().execute(new Runnable() {

			@Override
			public void run() {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("url", URLs.getGonglueFenlei);
				map.put("bujiami", "");
				Message message = Message.obtain();

				try {
					message.obj = ((CBBContext) mContext).Request(
							getActivity(), map, new FenLei());
					message.what = 2;

				} catch (Exception e) {
					e.printStackTrace();

					message.obj = e;
					message.what = -1;
				}

				handle.sendMessage(message);
			}
		});
	}

}
