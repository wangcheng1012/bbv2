package com.wlj.chuangbabav2.bean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wlj.bean.Base;
import com.wlj.util.MathUtil;

public class Menchuang extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3828598279833264993L;

	private String pic;
	private String name;
	private String time;
	private double money;
	private int dingzhi;
	private int shoucang;
	private int liulan;
	private String tedian;
	private List<String> colors;
	private String bolicaizhi;
	private String gongyi;
	private String fuwu;
	private String webView1;
	
	
	/*
	 * 案例新增字段
	 */
	private String content;
	private String fenxiang;

	
	public String getFenxiang() {
		return fenxiang;
	}

	public void setFenxiang(String fenxiang) {
		this.fenxiang = fenxiang;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getColors() {
		return colors;
	}

	public void setColors(List<String> colors) {
		this.colors = colors;
	}

	public String getBolicaizhi() {
		return bolicaizhi;
	}

	public void setBolicaizhi(String bolicaizhi) {
		this.bolicaizhi = bolicaizhi;
	}

	public String getGongyi() {
		return gongyi;
	}

	public void setGongyi(String gongyi) {
		this.gongyi = gongyi;
	}

	public String getFuwu() {
		return fuwu;
	}

	public void setFuwu(String fuwu) {
		this.fuwu = fuwu;
	}

	public String getWebView1() {
		return webView1;
	}

	public void setWebView1(String webView1) {
		this.webView1 = webView1;
	}

	public int getDingzhi() {
		return dingzhi;
	}

	public void setDingzhi(int dingzhi) {
		this.dingzhi = dingzhi;
	}

	public int getShoucang() {
		return shoucang;
	}

	public void setShoucang(int shoucang) {
		this.shoucang = shoucang;
	}

	public int getLiulan() {
		return liulan;
	}

	public void setLiulan(int liulan) {
		this.liulan = liulan;
	}

	public String getTedian() {
		return tedian;
	}

	public void setTedian(String tedian) {
		this.tedian = tedian;
	}

	public String getMoney(String danwei) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(money / 100) + danwei;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	// {"id":"55f1032ad105fc1228077b15","name":"客厅门窗","pic":"attachFiles/temp/55f10329d105fc1228077b14","intro":"","time":"1441858346000"}
	@Override
	public Base parse(JSONObject jsonObject) throws JSONException {
		Menchuang menchuang = new Menchuang();
		menchuang.setId(jsonObject.optString("id"));
		menchuang.setPic(jsonObject.optString("pic"));
		menchuang.setName(jsonObject.optString("name"));
		menchuang.setTime(jsonObject.optString("time"));
		menchuang.setMoney(jsonObject.optDouble("pricefen"));
		menchuang.setBolicaizhi(jsonObject.optString("caizhi"));
		menchuang.setDingzhi(jsonObject.optInt("dingzhi"));
		menchuang.setShoucang(jsonObject.optInt("shoucang"));
		menchuang.setLiulan(jsonObject.optInt("liulan"));
		menchuang.setTedian(jsonObject.optString("tedian"));
		menchuang.setGongyi(jsonObject.optString("gongyi"));
		menchuang.setFuwu(jsonObject.optString("fuwu"));
		menchuang.setWebView1(jsonObject.optString("intro"));
		JSONArray colorsarry = jsonObject.optJSONArray("colors");
		if (colorsarry != null) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < colorsarry.length(); i++) {
				JSONObject object = colorsarry.getJSONObject(i);
				list.add(object.optString("color"));
			}
			menchuang.setColors(list);
		}

		menchuang.setContent(jsonObject.optString("content"));
		menchuang.setFenxiang(jsonObject.optString("zhuanfa"));

		return menchuang;
	}

	@Override
	public Base parseBean(JSONObject jsonObject) throws JSONException {
		// {"state":"2","data":{"content":"","id":"55f27723d812a81087eb9e21","time":"1441953571000","viewcount":"","pic":"attachFiles\/temp\/55f27720d812a81087eb9e20","intro":"","name":"门窗搜索图"}}
		return parse(jsonObject.getJSONObject("data"));
	}

}
