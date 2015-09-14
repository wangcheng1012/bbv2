package com.wlj.chuangbabav2.activity.home.menchuang;

import static com.wlj.chuangbabav2.web.MsgContext.key_page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.wlj.adapter.CommonAdapter;
import com.wlj.adapter.ViewHolder;
import com.wlj.bean.Base;
import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.activity.home.Home2;
import com.wlj.chuangbabav2.bean.Menchuang;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseRefreshFragment;
import com.wlj.util.ExecutorServices;
import com.wlj.util.UIHelper;
import com.wlj.util.img.LoadImage;

public class MenChuang extends BaseRefreshFragment{

	@Override
	protected void initCommonAdapter(List<Base> listDate2) {
	
		commonAdapter = new CommonAdapter<Base>(mContext,listDate2,R.layout.item_imageview) {

			@Override
			public View getListItemview(ViewHolder viewHolder, View view,
					Base item, int position, ViewGroup parent) {
				Menchuang wen =  (Menchuang)item;
				
				ImageView iv_icon = (ImageView)view.findViewById(R.id.imageview);
				
				iv_icon.setVisibility(View.INVISIBLE);
				String picurl = wen.getPic();
				if("".equals(picurl.trim())){
				}else{
					LoadImage.getinstall().addTask(URLs.HOST+picurl,iv_icon );
				}
				
				view.setTag(R.id.tag_first,item);
				return null;
			}
			
		};
		
		lv_center.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if(view == listview_footer){
					return;
				}
				
				Menchuang mc = (Menchuang) view.getTag(R.id.tag_first);
				
				if(!"55f27723d812a81087eb9e21".equals(mc.getId())){
					
					Intent xiangqing = new Intent(mContext, MenChuangList.class);
					xiangqing.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					xiangqing.putExtra("name",mc.getName());
					xiangqing.putExtra("fenleiid","");
					startActivity(xiangqing);
				}else{
					((Home2)getParentFragment()).ShowSearchPopupWindow(R.id.menchuangsearch);
				}
			}
		});
		
		topsearch();
		
	}

	private void topsearch(){
		//frist 
		ExecutorServices.getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("url", URLs.getPubById);
				map.put("id", "55f27723d812a81087eb9e21");
				map.put("bujiami", "");
				
				Message obtain = Message.obtain();
				try {
					BaseList request = ((CBBContext)mContext).Request(getActivity(), map, new Menchuang());
					obtain.obj = request;
					obtain.what =1;
					
				} catch (Exception e) {
					e.printStackTrace();
					obtain.obj = e;
					obtain.what =-1;
				}
				handle2.sendMessage(obtain);
			}
		});
		
	}
	
	private Handler handle2 = new Handler(Looper.getMainLooper()){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				BaseList baselist =   (BaseList) msg.obj;
				if(baselist != null){
					List<Base> baselist2 = baselist.getBaselist();
					if(baselist2 != null){
						for (Base base : baselist2) {
							
							ImageView imageView = new ImageView(getActivity());
							
							String picurl = ((Menchuang)base).getPic();
							imageView.setTag(R.id.tag_first, base);
							LoadImage.getinstall().addTask(URLs.HOST+picurl,imageView );
							LoadImage.getinstall().doTask();
							
							lv_center.addHeaderView(imageView);
						}
					}
				}
				
				break;
			case -1:
				Exception e = (Exception) msg.obj;
				UIHelper.ToastMessage(mContext, e.getMessage());
				break;

			}
		};
	};
	
	@Override
	protected BaseList webMethod(Context mContext, Bundle bundle,
			int pageIndex2, boolean isRefresh) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key_page, pageIndex2+"");
		map.put("url", URLs.getPubByFenleiid);
		map.put("fenleiid", "55f101bdd105fc1228077b11");
		map.put("bujiami", "");
		BaseList cacheBaseList = ((CBBContext)mContext).getHaveCacheBaseList(getActivity(),new Menchuang(),map,isRefresh);
		
		return cacheBaseList;
	}

}
