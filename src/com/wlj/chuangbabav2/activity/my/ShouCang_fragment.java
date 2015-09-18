package com.wlj.chuangbabav2.activity.my;

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
import com.wlj.chuangbabav2.activity.home.gonglue.GongLueXiangQing;
import com.wlj.chuangbabav2.activity.home.menchuang.MenChuangXiangQing;
import com.wlj.chuangbabav2.bean.FenLei;
import com.wlj.chuangbabav2.bean.Menchuang;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseRefreshFragment;
import com.wlj.util.RequestException;
import com.wlj.util.UIHelper;
import com.wlj.util.img.LoadImage;

public class ShouCang_fragment extends BaseRefreshFragment {

	private dataTransmission dt;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		dt = (dataTransmission) activity;
	}

	@Override
	protected void initCommonAdapter(List<Base> listDate2) {
		final Bundle bundle = dt.transmissionName();
		commonAdapter = new CommonAdapter<Base>(mContext, listDate2,R.layout.item_gonglue_list) {

			@Override
			public View getListItemview(ViewHolder viewHolder, View view,
					Base item, int position, ViewGroup parent) {
				Menchuang wen = (Menchuang) item;

				viewHolder.setText(R.id.glname, wen.getName());
				viewHolder.setText(R.id.glshuxing, "#"+bundle.getString("name")+"#");
				viewHolder.setText(R.id.gltongji,wen.getLiulan() + "次阅读 ");

				ImageView iv_icon = (ImageView) view
						.findViewById(R.id.menchuang_list_pic);
				iv_icon.setVisibility(View.INVISIBLE);
				String picurl = wen.getPic();
				if ("".equals(picurl.trim())) {
				} else {
					LoadImage.getinstall().addTask(URLs.HOST + picurl, iv_icon);
				}
				view.setTag(R.id.tag_first, item);
				return null;
			}
		};

		lv_center.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg1 == listview_footer) {
					return;
				}
				Intent intent = new Intent(getActivity(),GongLueXiangQing.class);
				intent.putExtra("fenleiid",((Menchuang) arg1.getTag(R.id.tag_first)).getId());
				intent.putExtra("name",dt.transmissionName().getString("name"));
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	@Override
	protected BaseList webMethod(Context mContext, Bundle bundle,
			int pageIndex2, boolean isRefresh) throws Exception {
		Bundle shuxing = dt.transmissionName();
		if (shuxing == null) {
			throw new RequestException("分类错误");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key_page, pageIndex2 + "");
		map.put("bujiami", "");
		map.put("url", URLs.getPubByFenleiid);
		String fenleiid = shuxing.getString("fenleiid");
		
		map.put("fenleiid", fenleiid);
		
		return ((CBBContext) mContext).getHaveCacheBaseList(getActivity(),
				new Menchuang(), map, isRefresh);
	}

}
