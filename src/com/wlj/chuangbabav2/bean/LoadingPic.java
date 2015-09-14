package com.wlj.chuangbabav2.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.wlj.bean.Base;

public class LoadingPic extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4387294680454948424L;
	
	
	private String picurl;

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	@Override
	public Base parse(JSONObject jsonObject) throws JSONException {
		
		LoadingPic loadingPic = new LoadingPic();
		
		loadingPic.setPicurl(jsonObject.optString("pic"));
		
		return loadingPic;
	}

}
