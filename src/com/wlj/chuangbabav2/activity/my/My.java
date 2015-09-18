package com.wlj.chuangbabav2.activity.my;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlj.bean.Base;
import com.wlj.bean.BaseList;
import com.wlj.chuangbabav2.CBBContext;
import com.wlj.chuangbabav2.MyBaseFragment;
import com.wlj.chuangbabav2.R;
import com.wlj.chuangbabav2.bean.Menchuang;
import com.wlj.chuangbabav2.web.URLs;
import com.wlj.util.img.LoadImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My extends MyBaseFragment implements View.OnClickListener {
    private ImageView myimageView;

    private RelativeLayout persioninfo_rl;
    private RelativeLayout shoucang_rl;
    private RelativeLayout mynews_rl;
    private RelativeLayout setup_rl;

    private TextView personinfo;
    private TextView myshoucang;
    private TextView mynews;
    private TextView setup;

    @Override
    protected int getlayout() {
        return R.layout.my;
    }

    @Override
    protected void initView(View view) {

        myimageView = (ImageView) view.findViewById(R.id.myimageView);

        persioninfo_rl = (RelativeLayout) view.findViewById(R.id.persioninfo_rl);
        shoucang_rl = (RelativeLayout) view.findViewById(R.id.shoucang_rl);
        mynews_rl = (RelativeLayout) view.findViewById(R.id.mynews_rl);
        setup_rl = (RelativeLayout) view.findViewById(R.id.setup_rl);

        personinfo = (TextView) view.findViewById(R.id.personinfo);
        myshoucang = (TextView) view.findViewById(R.id.myshoucang);
        mynews = (TextView) view.findViewById(R.id.mynews);
        setup = (TextView) view.findViewById(R.id.setup);
        //
        persioninfo_rl.setOnClickListener(this);
        shoucang_rl.setOnClickListener(this);
        mynews_rl.setOnClickListener(this);
        setup_rl.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.persioninfo_rl:
            	Intent persionInfo = new Intent(mContext, PersionInfo.class);
            	persionInfo.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(persionInfo);
                break;
            case R.id.shoucang_rl:
            	Intent shouCang = new Intent(mContext, ShouCang.class);
            	shouCang.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(shouCang);
                break;
            case R.id.mynews_rl:
            	Intent  myNews =  new Intent(mContext, MyNews.class);
            	myNews.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(myNews);
                break;
            case R.id.setup_rl:
            	Intent setUp = new Intent(mContext, SetUp.class);
            	setUp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(setUp);
                break;
        }
    }

    @Override
    protected void Switch(Message msg) {

    }
    @Override
    protected void setViewDate(Object obj) {
        if(obj == null) return;
        BaseList baseList = (BaseList)obj;
        List<Base> baselist2 = baseList.getBaselist();
        if(baselist2.get(0) == null||baselist2.get(0).equals("null")) return;
        Menchuang menchuang = (Menchuang) baselist2.get(0);
        LoadImage.getinstall().addTask(URLs.HOST+menchuang.getPic(), myimageView);
        LoadImage.getinstall().doTask();
    }

    @Override
    protected Object callWebMethod() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", URLs.getPubById);
        map.put("bujiami", "");
        map.put("id", "55f7b121d812a80d22d4586a");
        return ((CBBContext) mContext).Request(getActivity(), map, new Menchuang());
    }
}