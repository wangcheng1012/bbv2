package com.wlj.chuangbabav2.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.wlj.bean.Base;

public class YuYue extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6053956908200672674L;

	public final static int state_ing = 1;//预约中
	public final static int state_comple = 2;//完成
	
	private String yuyuePhone;
	private String yuyueTime;
	private String tuijianrenPhone;
	private String message;
	private String orderId;
	private String userid;
	private String sheng;
	private String shi;
	private String qu;
	private String name;

	public String getYuyuePhone() {
		return yuyuePhone;
	}

	public void setYuyuePhone(String yuyuePhone) {
		this.yuyuePhone = yuyuePhone;
	}

	public String getYuyueTime() {
		return yuyueTime;
	}

	public void setYuyueTime(String yuyueTime) {
		this.yuyueTime = yuyueTime;
	}

	public String getTuijianrenPhone() {
		return tuijianrenPhone;
	}

	public void setTuijianrenPhone(String tuijianrenPhone) {
		this.tuijianrenPhone = tuijianrenPhone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSheng() {
		return sheng;
	}

	public void setSheng(String sheng) {
		this.sheng = sheng;
	}

	public String getShi() {
		return shi;
	}

	public void setShi(String shi) {
		this.shi = shi;
	}

	public String getQu() {
		return qu;
	}

	public void setQu(String qu) {
		this.qu = qu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Base parse(JSONObject object) throws JSONException {
		YuYue yuyue = new YuYue();
		yuyue.setId(object.optString("id"));
		yuyue.setYuyuePhone(object.optString("yuyuePhone"));
		yuyue.setYuyueTime(object.optString("yuyueTime"));
		yuyue.setTuijianrenPhone(object.optString("tuijianrenPhone"));
		yuyue.setSheng(object.optString("sheng"));
		yuyue.setShi(object.optString("shi"));
		yuyue.setQu(object.optString("qu"));
		yuyue.setMessage(object.optString("message"));
		yuyue.setOrderId(object.optString("orderId"));
		yuyue.setName(object.optString("name"));
		return yuyue;
	}

}
