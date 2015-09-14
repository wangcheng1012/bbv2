package com.wlj.chuangbabav2.activity.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlj.adapter.CommonAdapter;
import com.wlj.adapter.ViewHolder;
import com.wlj.bean.Base;
import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.activity.home.anli.AnLi;
import com.wlj.chuangbabav2.activity.home.anli.AnLiList;
import com.wlj.chuangbabav2.activity.home.gongyue.GongLue;
import com.wlj.chuangbabav2.activity.home.gongyue.GongLueList;
import com.wlj.chuangbabav2.activity.home.menchuang.MenChuang;
import com.wlj.chuangbabav2.activity.home.menchuang.MenChuangList;
import com.wlj.chuangbabav2.bean.FenLei;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.chuangbabav2.widget.ScllorTabView;
import com.wlj.ui.BaseFragment;
import com.wlj.util.DpAndPx;
import com.wlj.util.ExecutorServices;
import com.wlj.util.UIHelper;

public class Home2 extends BaseFragment implements OnPageChangeListener, OnClickListener, OnItemClickListener {

	private ViewPager  viewpager;
	private List<Fragment> list;
	
	private  RelativeLayout home_toplayout;
	private TextView menchuang,anli,gongyue;
	private ScllorTabView cursor;
	private ImageView jiaohao ;
	private ListView  listView1,listView2;
	
	@Override
	protected int getlayout() {
		return R.layout.fragment_main_home2_mc;
	}

	@Override
	public void onResume() {
		super.onResume();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
	}

	@Override
	protected void initView(View view) {
		//cursor
		cursor = (ScllorTabView)view.findViewById(R.id.cursor);
		cursor.setTabNum(3);
		cursor.setCurrentNum(0);
		cursor.setmTabWidth(DpAndPx.dpToPx(getActivity(), 46));
		cursor.setSelectedColor(getResources().getColor(R.color.color_home_cursor), getResources().getColor(R.color.color_home_cursor));
		//tab
		home_toplayout = (RelativeLayout)view.findViewById(R.id.home_toplayout);
		menchuang = (TextView)view.findViewById(R.id.menchuang);
		anli = (TextView)view.findViewById(R.id.anli);
		gongyue = (TextView)view.findViewById(R.id.gongyue);
		jiaohao = (ImageView)view.findViewById(R.id.jiaohao);
		
		menchuang.setOnClickListener(this);
		anli.setOnClickListener(this);
		gongyue.setOnClickListener(this);
		jiaohao.setOnClickListener(this);
		//context
		list = new ArrayList<Fragment>();
		list.add(new MenChuang());
		list.add(new AnLi());
		list.add(new GongLue());
		
		viewpager = (ViewPager)view.findViewById(R.id.viewpager);
		viewpager.setAdapter(new MyFragmentStatePagerAdapter(getChildFragmentManager(),list) );
		
		viewpager.addOnPageChangeListener(this);
		
		popupcontext = LayoutInflater.from(mContext).inflate(R.layout.fragment_main_home2_jiahao, null);
		(popupcontext.findViewById(R.id.menchuangsearch)).setOnClickListener(this);
		(popupcontext.findViewById(R.id.anlisearch)).setOnClickListener(this);
		(popupcontext.findViewById(R.id.gongluesearch)).setOnClickListener(this);
		(popupcontext.findViewById(R.id.erweima)).setOnClickListener(this);
		
		SearchPopupContext = LayoutInflater.from(mContext).inflate(R.layout.fragment_main_home2_sousuo, null);
		listView1 = (ListView)SearchPopupContext.findViewById(R.id.listView1);
		listView2 = (ListView)SearchPopupContext.findViewById(R.id.listView2);
		
		listView1.setOnItemClickListener(this);
		listView2.setOnItemClickListener(this);
		
		drawableright = getResources().getDrawable(R.drawable.pre);
		drawableright.setBounds(0, 0, drawableright.getMinimumWidth(),drawableright.getMinimumHeight());
		
		drawable = getResources().getDrawable(R.drawable.cursor_s);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
		
	}
	private Drawable drawable,drawableright;
	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		cursor.setOffset(position, positionOffset);
	}

	@Override
	public void onPageSelected(int position) {
	}

	private PopupWindow popupWindow = null;
	private View  popupcontext,SearchPopupContext;

	private void showPopupWindow() {
		if (popupWindow == null) {
			popupWindow = new PopupWindow(popupcontext,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable(
					0x00000000));
		}
		popupWindow.showAsDropDown(jiaohao, 0, DpAndPx.dpToPx(mContext, 20));
	}

	private PopupWindow popupSearchWindow = null;
	
	public void ShowSearchPopupWindow(int index){
		if (popupSearchWindow == null) {
			popupSearchWindow = new PopupWindow(SearchPopupContext,
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
			popupSearchWindow.setOutsideTouchable(true);
			popupSearchWindow.setBackgroundDrawable(new ColorDrawable(
					0x00000000));
		}
		popupSearchWindow.showAsDropDown(home_toplayout, 0, DpAndPx.dpToPx(mContext, 2));
//		popupSearchWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		callweb(index);
		
	}
	private int index_;
	private void callweb(int index) {
		index_ = index;
		ExecutorServices.getExecutorService().execute(new Runnable() {
			
			@Override
			public void run() {
				Map<String, Object> map = new HashMap<String, Object>(); 
				switch (index_) {
				case R.id.menchuangsearch:
					map.put("url", URLs.getProductFenleiList);
					break;
				case R.id.anlisearch:
					
					map.put("url", URLs.getAnliFenleiList);
					break;
				case R.id.gongluesearch:
					
					map.put("url", URLs.getGonglueFenlei);
					
					break;
				}
				map.put("bujiami", "");
				
				Message obtain = Message.obtain();
				try {
					BaseList request = ((CBBContext)mContext).Request(getActivity(), map, new FenLei());
					obtain.what = 1;
					obtain.obj = request;
					
				} catch (Exception e) {
					e.printStackTrace();
					obtain.what = -1;
					obtain.obj = e;
				}
				handle.sendMessage(obtain);
			}
		});
		
		
	}

	private Handler handle= new Handler(Looper.getMainLooper()){
		
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				BaseList baselist = (BaseList) msg.obj;
				if(baselist != null){
					List<Base> baselist2 = baselist.getBaselist();
					
					listView1.setAdapter(new CommonAdapter<Base>(mContext, baselist2, R.layout.item_spinner) {

						@Override
						public View getListItemview(ViewHolder viewHolder, View view,
								Base item, int position, ViewGroup parent) {
							FenLei mc =(FenLei)item;
							viewHolder.setText(R.id.item_title, mc.getName());
							
							TextView textview = (TextView)view;
							textview.setBackgroundResource(R.color.black);
							textview.setTextColor(Color.WHITE);
							textview.setPadding(10, 10, 0, 10);
							
							view.setTag(R.id.tag_first,item);
							return null;
						}

					});
				}
				break;
			case -1:
				Exception e = (Exception)msg.obj;
				UIHelper.ToastMessage(getActivity(), e.getMessage());
				break;
			}
			
			
		};
	};
	private TextView lasttextview;
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		switch (arg0.getId()) {
		case R.id.listView1:
			if(lasttextview != null){
				lasttextview.setBackgroundResource(R.color.black);
				lasttextview.setTextColor(Color.WHITE);
				lasttextview.setCompoundDrawables(null, null, null, null);
			}
			TextView textview = (TextView)arg1;
			textview.setBackgroundResource(R.color.hui_f0f0f0);
			textview.setTextColor(Color.RED);
			
			textview.setCompoundDrawables(null, null, drawable, null);
			lasttextview = textview; 
			
			if(R.id.gongluesearch == index_){
				
				FenLei fl2 = (FenLei) arg1.getTag(R.id.tag_first);
				Intent intent = new Intent(mContext, GongLueList.class);
				intent.putExtra("name",fl2.getName());
				intent.putExtra("fenleiid",fl2.getId());
				
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
//				popupSearchWindow.dismiss();
			}else{
				FenLei fl = (FenLei) arg1.getTag(R.id.tag_first);
				List<FenLei> child = fl.getChild();
	
				listView2.setAdapter(new CommonAdapter<FenLei>(mContext, child,
						R.layout.item_spinner) {
	
					@Override
					public View getListItemview(ViewHolder viewHolder, View view,
							FenLei item, int position, ViewGroup parent) {
						
						viewHolder.setText(R.id.item_title, item.getName());
						
						TextView textview = (TextView)view;
						textview.setBackgroundColor(Color.TRANSPARENT);
						textview.setTextColor(Color.BLACK);
						textview.setPadding(10, 10, 100, 10);
						
						textview.setCompoundDrawables(null, null, drawableright, null);
						
						view.setTag(R.id.tag_first, item);
						return null;
					}
	
				});
				
			}
			break;
		case R.id.listView2:
			FenLei fl2 = (FenLei) arg1.getTag(R.id.tag_first);
			Class cal = MenChuangList.class;;
			switch (index_) {
			case R.id.menchuangsearch:
				cal = MenChuangList.class;
				break;
			case R.id.anlisearch:
				cal = AnLiList.class;
				break;
			}
			Intent intent = new Intent(mContext, cal);
			intent.putExtra("name",fl2.getName());
			intent.putExtra("fenleiid",fl2.getId());
			
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
//			popupSearchWindow.dismiss();
			break;

		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menchuang:
			viewpager.setCurrentItem(0,true);
			break;
		case R.id.anli:
			viewpager.setCurrentItem(1,true);
			break;
		case R.id.gongyue:
			viewpager.setCurrentItem(2,true);
			break;
		case R.id.jiaohao:
			showPopupWindow();
			break;
		case R.id.menchuangsearch:
			popupWindow.dismiss();
			 
			ShowSearchPopupWindow(R.id.menchuangsearch);
			break;
		case R.id.anlisearch:
			popupWindow.dismiss();
			
			ShowSearchPopupWindow(R.id.anlisearch);
			break;
			
		case R.id.gongluesearch:
			popupWindow.dismiss();
			
			ShowSearchPopupWindow(R.id.gongluesearch);
			break;
		}
	}

}
