package com.wlj.chuangbabav2.activity.home.gonglue;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.bean.Menchuang;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragmentActivity2;
import com.wlj.util.img.LoadImage;

public class GongLueXiangQing extends BaseFragmentActivity2 {

	private ImageView gonglue_list_pic;
	private TextView name,shuoming,yudu,fenxiang;
	private WebView webView1;
	@Override
	protected void beforeTitle() {
		title.setText("攻略详情");
	}

	@Override
	protected int setlayout() {
		return R.layout.home2_gl_xq;
	}

	@Override
	protected void initView() {

		gonglue_list_pic = (ImageView)findViewById(R.id.gonglue_list_pic);
		name = (TextView)findViewById(R.id.name);
		shuoming = (TextView)findViewById(R.id.shuoming);
		yudu = (TextView)findViewById(R.id.yudu);
		fenxiang = (TextView)findViewById(R.id.fenxiang);
		webView1 = (WebView)findViewById(R.id.webView1);
		
		callweb();
	}

	@Override
	protected void Switch(Message msg) {

	}

	@Override
	protected void setViewDate(Object obj) {
		BaseList baseList = (BaseList)obj;
		
		Menchuang menchuang = (Menchuang)baseList.getBaselist().get(0);
		
		name.setText(menchuang.getName());
		
		gonglue_list_pic.setVisibility(View.INVISIBLE);
		String picurl = menchuang.getPic();
		if(!"".equals(picurl.trim())){
			LoadImage.getinstall().addTask(URLs.HOST+picurl,gonglue_list_pic);
			LoadImage.getinstall().doTask();
		}
		
		shuoming.setText("#"+getIntent().getStringExtra("name")+"#");
		yudu.setText(menchuang.getDingzhi()+ "次浏览 ");
		fenxiang.setText(menchuang.getFenxiang()+ "次分享 ");
		
		WebSettings settings = webView1.getSettings();
		settings.setDefaultTextEncodingName("UTF-8");
//		settings.setAllowFileAccess(true);
//		settings.setBuiltInZoomControls(true);//会出现放大缩小的按钮
//		settings.setDisplayZoomControls(true);
		//手势放大网页以后，自动缩小回了原来的尺寸的解决
		settings.setUseWideViewPort(true);//可任意比例缩放。但会造成下部很大空白，"<meta name=\"viewport\" 控制
//		settings.setLoadWithOverviewMode(true);//初始大小为屏幕宽，会导致文字缩小
//		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		
		byte[] byteIcon2= Base64.decode(menchuang.getContent(),Base64.DEFAULT);
		String data2 = new String(byteIcon2);
		webView1.setWebViewClient(new WebViewClient(){
			
			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url,
					Bitmap favicon) {
				view.getSettings().setBlockNetworkImage(false);
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				view.getSettings().setBlockNetworkImage(true);
				super.onPageFinished(view, url);
			}
			
		});
		webView1.loadData(getHtmlData(data2), "text/html; charset=UTF-8", null);
	}

	@Override
	protected void rightOnClick() {

	}

	@Override
	protected void liftOnClick() {

	}

	@Override
	protected Object callWebMethod() throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("url", URLs.getAnliById);
		map.put("id", intent.getStringExtra("fenleiid"));
		map.put("bujiami", "");
		
		return ((CBBContext)getApplicationContext()).Request(this, map, new Menchuang());
	}
	 private String getHtmlData(String bodyHTML) {
			/**
			 * telephone=no就禁止了把数字转化为拨号链接！
			 * telephone=yes就开启了把数字转化为拨号链接，要开启转化功能，这个meta就不用写了,在默认是情况下就是开启！
			 * viewport的理解:http://www.cnblogs.com/2050/p/3877280.html
			 */
	        String head =  "<head>" +
	       		    	"<meta charset=\"utf-8\" />"+
	       		    	" <meta name=\"format-detection\" content=\"telephone=no\" />"+
	       		    	"<meta name=\"viewport\" content=\"user-scalable=no, initial-scale=1, maximum-scale=2, minimum-scale=1, width=device-width, height=device-height \" />" +
//	      <!-- WARNING: for iOS 7, remove the width=device-width and height=device-height attributes. See https://issues.apache.org/jira/browse/CB-4323 -->
							"<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
							"</head>";
	        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
	    }
}
