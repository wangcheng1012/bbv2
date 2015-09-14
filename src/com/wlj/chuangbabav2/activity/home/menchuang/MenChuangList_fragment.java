package com.wlj.chuangbabav2.activity.home.menchuang;

import static com.wlj.chuangbabav2.web.MsgContext.key_page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.wlj.chuangbabav2.activity.home.dataTransmission;
import com.wlj.chuangbabav2.bean.Menchuang;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseRefreshFragment;
import com.wlj.util.RequestException;
import com.wlj.util.img.LoadImage;

public class MenChuangList_fragment extends BaseRefreshFragment {
	private dataTransmission dt;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		dt = (dataTransmission)activity;
	}

	@Override
	protected void initCommonAdapter(List<Base> listDate2) {

		commonAdapter = new CommonAdapter<Base>(mContext,listDate2,R.layout.item_menchuang_list) {

			@Override
			public View getListItemview(ViewHolder viewHolder, View view,
					Base item, int position, ViewGroup parent) {
				Menchuang wen =  (Menchuang)item;
				
				viewHolder.setText(R.id.name, wen.getName());
				viewHolder.setText(R.id.money, "￥:"+wen.getMoney("/㎡"));
				viewHolder.setText(R.id.tongji, wen.getDingzhi()+"定制·"+wen.getShoucang()+"收藏");
				viewHolder.setText(R.id.tedian, wen.getTedian());
				
				ImageView iv_icon = (ImageView)view.findViewById(R.id.menchuang_list_pic);
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
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(view == listview_footer){
					return;
				}
				Intent intent = new Intent(getActivity(), MenChuangXiangQing.class);
				intent.putExtra("fenleiid", ((Menchuang)arg1.getTag(R.id.tag_first)).getId());
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	@Override
	protected BaseList webMethod(Context mContext, Bundle bundle,
			int pageIndex2, boolean isRefresh) throws Exception {
		Bundle shuxing = dt.transmissionName();
		if(shuxing == null){
			throw new RequestException("分类错误");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key_page, pageIndex2+"");
		map.put("bujiami", "");
		map.put("url", URLs.getProductListByFenleiId);
		String fenleiid = shuxing.getString("fenleiid");
		if("".equals(fenleiid)){
			map.put("shuxing", shuxing.getString("name"));
		}else{
			map.put("fenleiid", fenleiid);
		}
		return ((CBBContext)mContext).getHaveCacheBaseList(getActivity(),new Menchuang(),map,isRefresh);
	}
	
}
