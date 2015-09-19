package com.wlj.chuangbabav2.activity.my.news;

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
import com.wlj.util.MathUtil;
import com.wlj.util.RequestException;
import com.wlj.util.StringUtils;
import com.wlj.util.UIHelper;
import com.wlj.util.img.LoadImage;

public class news_fragment extends BaseRefreshFragment {

	@Override
	protected void initCommonAdapter(List<Base> listDate2) {
		
		commonAdapter = new CommonAdapter<Base>(mContext, listDate2,R.layout.item_my_news) {

			@Override
			public View getListItemview(ViewHolder viewHolder, View view,
					Base item, int position, ViewGroup parent) {
				Menchuang wen = (Menchuang) item;

				viewHolder.setText(R.id.newstype, wen.getName());
				viewHolder.setText(R.id.time, StringUtils.getTime(MathUtil.parseLong(wen.getTime()), "yyyy-MM-dd") );
				viewHolder.setText(R.id.newcontext,wen.getContent());

				view.setTag(R.id.tag_first, item);
				return null;
			}
		};

	}

	@Override
	protected BaseList webMethod(Context mContext, Bundle bundle,
			int pageIndex2, boolean isRefresh) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key_page, pageIndex2 + "");
		map.put("url", URLs.messageList);
		
		return ((CBBContext) mContext).getHaveCacheBaseList(getActivity(),new Menchuang(), map, isRefresh);
	}

}
