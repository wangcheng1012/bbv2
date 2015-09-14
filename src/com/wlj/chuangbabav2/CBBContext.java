package com.wlj.chuangbabav2;

import static com.wlj.chuangbabav2.web.MsgContext.key_page;
import static com.wlj.chuangbabav2.web.MsgContext.key_pageSize;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;

import com.wlj.bean.Base;
import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.activity.RandCodeLogin;
import com.wlj.chuangbabav2.activity.PSWLogin;
import com.wlj.chuangbabav2.bean.User;
import com.wlj.chuangbabav2.web.RequestWebClient;
import com.wlj.util.AppConfig;
import com.wlj.util.AppContext;
import com.wlj.util.AppException;
import com.wlj.util.Log;
import com.wlj.util.RequestException;

public class CBBContext extends AppContext {

	
	public void Login(User u) throws Exception {
		User user = null;
		if (isNetworkConnected()) {
			try {
				user = RequestWebClient.getInstall().Login(u);

				if (user != null) {
					user.setName(u.getName());
					setProperty(AppConfig.CONF_NAME, user.getName());
					setProperty(AppConfig.CONF_TYPT, user.getType());
					setProperty(AppConfig.CONF_KEY, user.getKey());
				}
			} catch (AppException e) {
				throw e;
			}
		}else{
			throw new RequestException("网络异常");
		}
	}
	
	/**
	 * 验证码
	 * 
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public String getLoginRand(String phone) throws Exception {

		if (isNetworkConnected()) {

			String pub = RequestWebClient.getInstall().getLoginRand(phone);
			return pub;
		}
		throw new RequestException("网络异常");

	}
	/**
	 * 需要缓存
	 * 
	 * @param base
	 * @param map
	 *            cachekey
	 * @param isRefresh
	 * @return
	 * @throws Exception
	 */
	public BaseList getHaveCacheBaseList(Activity activity, Base cla,
			Map<String, Object> map, boolean isRefresh) throws Exception {

		BaseList list = null;
		String key = getProperty(AppConfig.CONF_TYPT) + "_"
				+ getProperty(AppConfig.CONF_NAME) + "_"
				+ cla.getClass().getSimpleName() + "_" + map.get("cachekey")
				+ "_" + map.get(key_page) + "_" + map.get(key_pageSize);
		Log.w("key", key);
		if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
			try {
				list = Request(activity, map, cla);
				if (list != null) {
					saveObject(list, key);
				}
			} catch (AppException e) {
				list = (BaseList) readObject(key);
				if (list == null)
					throw e;
			}
		} else {
			list = (BaseList) readObject(key);
			if (list == null)
				throw new RequestException("数据为空");
		}
		return list;
	}

	/**
	 * 
	 * <li>Map<String, Object> map = new HashMap<String, Object>();</li>
	 * <li>map.put("url", ""); 必须</li>
	 * <li>map.put("bujiami", ""); 可选</li>
	 * <li>map.put("user_Type", value); 可选</li>
	 * 
	 */
	public BaseList Request(Activity activity, Map<String, Object> map,
			Base cla) throws Exception {
		Log.w("map", map.toString());
		if (isNetworkConnected()) {
			if (map.containsKey("user_Type")) {
				map.remove("user_Type");
				String KEY = getProperty(AppConfig.CONF_KEY);
				if (!"".equals(KEY)&& !"null".equals(KEY)) {
					return RequestWebClient.getInstall().Request(map, cla,
							getProperty(AppConfig.CONF_KEY),
							getProperty(AppConfig.CONF_NAME));
				} else {
//					if ((map.get("user_Type") + "").equals(User.type_huiyuan)) {
					Intent intent = new Intent(this,PSWLogin.class);
					String packageName = activity.getPackageName();
					String localClassName = activity.getLocalClassName();
					intent.putExtra("activityname", packageName+localClassName);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					activity.startActivityForResult(intent, 11);
//					} else {
//						activity.startActivityForResult(new Intent(this,
//								RandCodeLogin.class), 11);
//					}
					throw new RequestException("请先登录");
				}
			} else {
				return RequestWebClient.getInstall().Request(map, cla,
						getProperty(AppConfig.CONF_KEY),
						getProperty(AppConfig.CONF_NAME));
			}
		}
		throw new RequestException("网络异常");
	}
}
