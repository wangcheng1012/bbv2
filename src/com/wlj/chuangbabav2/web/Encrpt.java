package com.wlj.chuangbabav2.web;



public interface Encrpt {
	
	public String encrypt(String inputStr,String key) throws Exception;
	
	public String decrypt(String inputStr,String key) throws Exception;
}
