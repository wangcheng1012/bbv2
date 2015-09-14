package com.wlj.chuangbabav2.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wlj.bean.Base;

public class FenLei extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5941087336722569537L;
	private String name;
	private String pic;
	private List<FenLei> child;
	
	public String getName() {
		return name;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<FenLei> getChild() {
		return child;
	}

	public void setChild(List<FenLei> child) {
		this.child = child;
	}

	// {"id":"5540a44dd812a83f8eed3c86","name":"门窗","child":[{"id":"5540a47ed812a83f8eed3c88","name":"推拉门窗"},{"id":"5540a47ed812a83f8eed3c87","name":"平开门窗"},{"id":"55482668d6c4591248857578","name":"铝木复合门窗"}]}
	@Override
	public Base parse(JSONObject jsonObject) throws JSONException {
		FenLei fenLei = new FenLei();
		fenLei.setId(jsonObject.optString("id"));
		fenLei.setName(jsonObject.optString("name"));
		fenLei.setPic(jsonObject.optString("pic"));
		
		List<FenLei> chil = new ArrayList<FenLei>();
		JSONArray childJA = jsonObject.optJSONArray("child");
		if(childJA != null){
			for (int i = 0; i < childJA.length(); i++) {
				FenLei fl =  new FenLei();
				JSONObject childjo = childJA.optJSONObject(i);
				
				fl.setId(childjo.optString("id"));
				fl.setName(childjo.optString("name"));
				chil.add(fl);
			}
		}
		fenLei.setChild(chil);
		
		return fenLei;
	}

}
