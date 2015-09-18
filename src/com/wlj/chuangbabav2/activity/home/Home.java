package com.wlj.chuangbabav2.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlj.chuangbabav2.R;
import com.wlj.ui.BaseFragment;
import com.wlj.util.DpAndPx;

public class Home extends BaseFragment implements OnClickListener {

	private ImageView cursor;
	protected int oldposition = -1;
	protected Fragment findFragment = null;
	protected Fragment center_Fragment;
	
	private TextView menchuang,anli,gongyue;
	
	protected void changeFragment(Fragment fragment,int position) {
	
		String fragmentName = fragment.getClass().getSimpleName();

		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment findfragment = fm.findFragmentByTag(fragmentName+position);
		if (center_Fragment != null) {
			ft.detach(center_Fragment);
		}

		if (findfragment != null) {
			ft.attach(findfragment);
			center_Fragment = findfragment;
		} else {
			center_Fragment = fragment;
			ft.add(R.id.framelay_center, center_Fragment, fragmentName+position);
		}
		ft.commitAllowingStateLoss();
	}
	
	protected void movecursor(int position){
		TranslateAnimation animation = new TranslateAnimation(arrPosition[oldposition== -1?0:oldposition], arrPosition[position], 0, 0);
		if (null != animation) {
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}
		oldposition = position;
	}
	
	private int[] arrPosition;
	protected void initWidth() {
		int cursorLenth = DpAndPx.dpToPx(getActivity(), 46);
		int count = 3;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cursorLenth, DpAndPx.dpToPx(getActivity(), 3));
		cursor.setLayoutParams(params);
		arrPosition = new int[count];
		for (int i = 0; i < count; i++) {
			arrPosition[i]= cursorLenth*i;
		}
	}

	@Override
	protected int getlayout() {
		return R.layout.home;
	}
	
	@Override
	protected void initView(View view) {
		cursor = (ImageView)view.findViewById(R.id.cursor);
		initWidth();
		
		menchuang = (TextView)view.findViewById(R.id.menchuang);
		anli = (TextView)view.findViewById(R.id.anli);
		gongyue = (TextView)view.findViewById(R.id.gongyue);
		
		menchuang.setOnClickListener(this);
		anli.setOnClickListener(this);
		gongyue.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menchuang:
			movecursor(0);
			
			
			break;
		case R.id.anli:
			movecursor(1);
			break;
			
		case R.id.gongyue:
			movecursor(2);
			break;

		}
	}


	

}
