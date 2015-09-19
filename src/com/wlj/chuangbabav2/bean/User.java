package com.wlj.chuangbabav2.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.wlj.bean.Base;

public class User extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7279892778087218222L;

	public final static String type_dailishang = "3";
	public final static String type_huiyuan = "1";

	private String name;
	private String psw;
	private String randCode;

	private String phone;
	/**
	 * 会员类型
	 */
	private String type;

	private String addr;

	private long regtime;

	private String pic;

	private int shoucang_count;

	private int message_count;

	public int getShoucang_count() {
		return shoucang_count;
	}

	public void setShoucang_count(int shoucang_count) {
		this.shoucang_count = shoucang_count;
	}

	public int getMessage_count() {
		return message_count;
	}

	public void setMessage_count(int message_count) {
		this.message_count = message_count;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public long getRegtime() {
		return regtime;
	}

	public void setRegtime(long regtime) {
		this.regtime = regtime;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getRandCode() {
		return randCode;
	}

	public void setRandCode(String randCode) {
		this.randCode = randCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	
	// {"state":"2","type":"3","user":{"gongshi":"","phone":"18225315935","idCard":"","jiguan":"","addr":"重庆市九龙坡区滩子口正街101号","daozhang":"0","id":"55ae0a0bd6c45940a7575491","mingzu":"","email":"","yuyue":"0","birthDate":"","hetongcount":"0","realname":"重庆滩子口专卖店","linkman":"梁旭东","picname":"attachFiles\/temp\/55b1d2bad6c459501b581c1b"},"key":"b2215b97aef74969bb35eef7"}
	@Override
	public Base parse(JSONObject jsonObject) throws JSONException {
		User user = new User();
		user.setKey(jsonObject.optString("key"));
		user.setType(jsonObject.optString("type"));
		user.setPsw(jsonObject.optString("password"));
		user.setRandCode(jsonObject.optString("randCode"));
		user.setPic(jsonObject.optString("pic"));
		user.setMessage_count(jsonObject.optInt("message_count"));
		user.setShoucang_count(jsonObject.optInt("shoucang_count"));
		
		user.setName(jsonObject.optString("realname"));
		user.setPhone(jsonObject.optString("phone"));
		user.setAddr(jsonObject.optString("addr"));
		user.setRegtime(jsonObject.optLong("regTime"));
		
		return user;
	}
	//{"id":"556c5289d6c45933b258ffa2","gongshi":"","phone":"18983261955","idCard":"","regTime":"1433162377000","mingzu":"","jiguan":"","email":"","birthDate":"","addr":"","realname":"321321321","linkman":"","picname":""}
	// {"data":{"shoucang_count":"3","message_count":"0"},"state":2,"description":"获取成功"}
	@Override
	public Base parseBean(JSONObject jsonObject) throws JSONException {
		
		JSONObject optJSONObject = jsonObject.optJSONObject("data");
		JSONObject user = jsonObject.optJSONObject("user");
		if(optJSONObject != null){
			
			return parse(optJSONObject);
		}else if(user != null){
			return parse(user);
		}
		return null;
	}
	

}
