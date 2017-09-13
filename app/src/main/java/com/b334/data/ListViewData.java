package com.b334.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.b334.antschool.R;

public class ListViewData {
	public ArrayList<HashMap<String, Object>> buildData() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "中国");
		map.put("time", "54分钟前");
		map.put("image", R.drawable.ic_launcher);
		map.put("information", "派送中");
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "英国");
		map.put("time", "54分钟前");
		map.put("image", R.drawable.ic_launcher);
		map.put("information", "派送中");
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "美国");
		map.put("time", "America");
		map.put("image", R.drawable.ic_launcher);
		map.put("information", "派送中");
		data.add(map);
		
		map = new HashMap<String, Object>();
		map.put("name", "荷兰");
		map.put("info", "Dutch");
		map.put("image", R.drawable.ic_launcher);
		map.put("information", "派送中");
		data.add(map);

		map = new HashMap<String, Object>();
		map.put("name", "新西兰");
		map.put("info", "New Zealand");
		map.put("image", R.drawable.ic_launcher);
		map.put("information", "派送中");
		data.add(map);


		return data;
	}
}
