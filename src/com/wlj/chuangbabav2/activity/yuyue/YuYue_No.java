package com.wlj.chuangbabav2.activity.yuyue;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mrwujay.cascade.AreaSelect;
import com.mrwujay.cascade.model.CityModel;
import com.mrwujay.cascade.model.DistrictModel;
import com.mrwujay.cascade.model.ProvinceModel;
import com.mrwujay.cascade.service.XmlParserHandler;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.activity.Main;
import com.wlj.chuangbabav2.activity.PSWLogin;
import com.wlj.chuangbabav2.bean.User;
import com.wlj.chuangbabav2.bean.YuYue;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.ui.BaseFragment;
import com.wlj.util.ExecutorServices;
import com.wlj.util.Log;
import com.wlj.util.UIHelper;

public class YuYue_No extends Fragment implements OnClickListener, OnItemSelectedListener {
	
	protected Context mContext;
	protected View view;// infalte的布局
	private EditText name,phone,shuoming;
	private Spinner sheng,shi;
	private Button submintyuyue;
	
	private Intent intent;
	private YuYue yuyuebean;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mContext = getActivity().getApplicationContext();
		Log.w("dd", "onCreate");
	}
	/**
	 * 每次加载 都会调用onCreateView
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (null == view) {

			view = inflater.inflate(R.layout.fragment_main_yuyue, null);
			name = (EditText)view.findViewById(R.id.name);
			phone = (EditText)view.findViewById(R.id.phone);
			shuoming = (EditText)view.findViewById(R.id.shuoming);
			submintyuyue = (Button)view.findViewById(R.id.submintyuyue);
			submintyuyue.setOnClickListener(this);
			((TextView)view.findViewById(R.id.title)).setText("快速预约");
			initProviceCityDistrictView();
		} else {
			FrameLayout viewParent = (FrameLayout) view.getParent();
			if(viewParent != null){
				viewParent.removeAllViews();
				viewParent = new FrameLayout(mContext);
				viewParent.addView(view);
				return viewParent;
			}
		}
		
		Bundle arguments = getArguments();
		if(arguments != null){
			yuyuebean = (YuYue) arguments.getSerializable("base");
			if(yuyuebean != null){
				name.setText(yuyuebean.getName());
				phone.setText(yuyuebean.getYuyuePhone());
				shuoming.setText(yuyuebean.getMessage());
				
				sheng.setSelection(Arrays.asList(mProvinceDatas).indexOf(yuyuebean.getSheng()));
				mCurrentCityName = yuyuebean.getShi();
			}
		}
		
		return view;
	}
	
	protected Handler handle2 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:
				UIHelper.loadingClose();
				UIHelper.ToastMessage(getActivity(), "预约成功");
				((Main)getActivity()).mTabHost.setCurrentTab(0);
				break;
			case -1:
				Exception e = (Exception)msg.obj;
				UIHelper.ToastMessage(getActivity(), e.getMessage());
				UIHelper.loadingClose();
//				{"state":1,"description":"登录信息过期,请重新登录!"}
				if("登录信息过期,请重新登录!".equals(e.getMessage())){
					Intent intent = new Intent(mContext, PSWLogin.class);
					intent.putExtra("activityname","com.wlj.chuangbabav2.activity.Main") ;
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, 11);
				}
				
				break;
			}
		}
	};

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.submintyuyue:
			
			UIHelper.loading("提交中……", getActivity());
			ExecutorServices.getExecutorService().execute(new Runnable() {
				
				@Override
				public void run() {
					Map<String, Object> map = new HashMap<String, Object>(); 
					map.put("url", URLs.yuyue); 
					if(yuyuebean != null){
						map.put("id", yuyuebean.getId());
					}else{
						map.put("id", "");
					}
					map.put("yuyuePhone", phone.getText()+"");  
					map.put("sheng", mCurrentProviceName);  
					map.put("shi", mCurrentCityName);  
					map.put("message", shuoming.getText()+"");  
					map.put("name", name.getText()+"");  
					map.put("orderId", (System.currentTimeMillis()+"").substring(1));
					
					Message message = Message.obtain();
					try {
						((CBBContext)mContext).Request(getActivity(), map, null);
						message.what = 2;
					} catch (Exception e) {
						e.printStackTrace();
						message.what = -1;
						message.obj = e;
					}
					handle2.sendMessage(message);
				}
			});
			break;
		}
	}


	/**
	 * 所有省
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();

	/**
	 * 当前省的名称
	 */
	public String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	public String mCurrentCityName;
	/**
	 * 零时市
	 */
	private String[] Citis ;
	
	private String fristProviceName;
	
	public String addr;

	/**
	 * 解析省市区的XML数据
	 */
    protected void initProvinceDatas()
	{
		List<ProvinceModel> provinceList = null;
    	AssetManager asset = mContext.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			provinceList = handler.getDataList();
			
			mProvinceDatas = new String[provinceList.size()];
        	for (int i=0; i< provinceList.size(); i++) {
        		// 遍历所有省的数据
        		mProvinceDatas[i] = provinceList.get(i).getName();
        		List<CityModel> cityList = provinceList.get(i).getCityList();
        		String[] cityNames = new String[cityList.size()];
        		for (int j=0; j< cityList.size(); j++) {
        			// 遍历省下面的所有市的数据
        			cityNames[j] = cityList.get(j).getName();
        			List<DistrictModel> districtList = cityList.get(j).getDistrictList();
        			String[] distrinctNameArray = new String[districtList.size()];
        			DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
        			for (int k=0; k<districtList.size(); k++) {
        				// 遍历市下面所有区/县的数据
        				DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
//        				// 区/县对于的邮编，保存到mZipcodeDatasMap
//        				mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				distrinctArray[k] = districtModel;
        				distrinctNameArray[k] = districtModel.getName();
        			}
//        			// 市-区/县的数据，保存到mDistrictDatasMap
//        			mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
        		}
        		// 省-市的数据，保存到mCitisDatasMap
        		mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
        	}
        } catch (Throwable e) {  
            e.printStackTrace();  
        } finally {
        	
        } 
	}
	
    private void initProviceCityDistrictView(){
    	
    	initProvinceDatas();
    	
    	sheng = (Spinner) view.findViewById(R.id.sheng);
		shi = (Spinner) view.findViewById(R.id.shi);
		
		// 添加change事件
    	sheng.setOnItemSelectedListener(this);
    	// 添加change事件
    	shi.setOnItemSelectedListener(this);
    	
    	ArrayAdapter<String>  adapter = new ArrayAdapter<String>(mContext.getApplicationContext(), R.layout.item_spinner, mProvinceDatas);
    	sheng.setAdapter(adapter);
    	
    	fristProviceName = mCurrentProviceName;
		
		if(fristProviceName != null){
			
			List<String> asList = Arrays.asList(mProvinceDatas);
	    	
	    	if(asList.contains(mCurrentProviceName)){
	    		sheng.setSelection(asList.indexOf(mCurrentProviceName)); 
	    		fristProviceName = null;
	    	}
		}
    }
    
    /**
	 * spinner选择事件
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		switch (parent.getId()) {
		
		case R.id.sheng:
			Citis =  mCitisDatasMap.get(sheng.getItemAtPosition(position));
			
			ArrayAdapter<String>  adapter = new ArrayAdapter<String>(mContext.getApplicationContext(),R.layout.item_spinner, Citis);
			shi.setAdapter(adapter);
				
			List<String> asList = Arrays.asList(Citis);
			
			if(asList.contains(mCurrentCityName)){
				shi.setSelection(asList.indexOf(mCurrentCityName)); 
			}
			mCurrentProviceName = ((TextView) view).getText()+"";
			
			break;
		case R.id.shi:
			
			mCurrentCityName = ((TextView) view).getText()+"";
			
			break;
			
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
}
