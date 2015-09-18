package com.wlj.chuangbabav2.activity.kefu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wlj.adapter.CommonAdapter;
import com.wlj.adapter.ViewHolder;
import com.wlj.bean.Base;
import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.MyBaseFragment;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.bean.FenLei;
import com.wlj.chuangbabav2.bean.LoadingPic;
import com.wlj.chuangbabav2.bean.Menchuang;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragment;
import com.wlj.util.img.LoadImage;

public class KeFu extends MyBaseFragment implements OnClickListener {
	private Animation translate_Animation;
	private ImageView kefubanner;
	@Override
	protected int getlayout() {
		return R.layout.kefu;
	}

	@Override
	protected void initView(View view) {
		
		TextView title = (TextView)view.findViewById(R.id.title);
		title.setText("客服");
		
		translate_Animation = AnimationUtils.loadAnimation(getActivity(),R.anim.translate);

		view.findViewById(R.id.call1).setOnClickListener(this);
		view.findViewById(R.id.fuwuandbaozhang).setOnClickListener(this);
		kefubanner = (ImageView)view.findViewById(R.id.kefubanner);
		phonebaselist = new ArrayList<Base>();
		
		callweb();
	}

	private void showdialog() {

		final AlertDialog dlg = new AlertDialog.Builder(getActivity()).create();
		Window window = dlg.getWindow();
		dlg.show();
		window.setContentView(R.layout.kefu_phone);
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
		
		ListView phonelistView = (ListView)window.findViewById(R.id.phonelistView);
		
		CommonAdapter = new CommonAdapter<Base>(getActivity(),phonebaselist,R.layout.item_kefu_phonelist) {

			@Override
			public View getListItemview(ViewHolder viewHolder, View view,
					Base item, int position, ViewGroup parent) {
				Menchuang fenlei = (Menchuang)item;
				viewHolder.setText(R.id.phone400, fenlei.getName());
				
				view.setTag(R.id.tag_first, item);
				return null;
			}
		};
		phonelistView.setAdapter(CommonAdapter);
		
		phonelistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String name = ((Menchuang)arg1.getTag(R.id.tag_first)).getName();
				name = name.replace("-", "");
				Intent intent = new Intent(Intent.ACTION_CALL);  
			    intent.setData(Uri.parse("tel:"+name.trim()));  
			    startActivity(intent);  
			}
		});
		
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
		}
	}
	private List<Base> phonebaselist;
	private CommonAdapter<Base>  CommonAdapter;
	
	
	@Override
	protected void Switch(Message msg) {
		if(msg.what == 2){//phone list
			BaseList baseList = (BaseList)msg.obj;
			phonebaselist = baseList.getBaselist();
//			CommonAdapter.notifyDataSetChanged();
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
		
		LoadImage.getinstall().addTask(URLs.HOST+menchuang.getPic(), kefubanner);
		LoadImage.getinstall().doTask();
	}

	@Override
	protected Object callWebMethod() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", URLs.getPubById);
		map.put("bujiami", "");
		map.put("id", "55f7b121d812a80d22d4586a");
		BaseList baseList = ((CBBContext)mContext).Request(getActivity(), map, new Menchuang());
		
		Map<String, Object> phonemap = new HashMap<String, Object>();
		phonemap.put("url", URLs.getPubByFenleiid);
		phonemap.put("bujiami", "");
		phonemap.put("fenleiid", "55f79337d812a80d22d45864");
		BaseList phonemapbaseList = ((CBBContext)mContext).Request(getActivity(), phonemap, new Menchuang());
		
		Message obtain = Message.obtain();
		obtain.what = 2;
		obtain.obj = phonemapbaseList;
		handle.sendMessage(obtain);
		
		return baseList;
	}

}
