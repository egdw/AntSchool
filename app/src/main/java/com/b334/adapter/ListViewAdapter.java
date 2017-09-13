package com.b334.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.b334.antschool.R;
import com.b334.data.ListViewData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter  extends BaseAdapter {
	
	private Context  context;
	private ArrayList<HashMap<String, Object>> arrayList;
	public ListViewAdapter(Context  context, ArrayList<HashMap<String, Object>> arrayList){
		this.arrayList = arrayList;
		this.context= context;
		
		
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holderView = null;
		if(convertView ==null){
			holderView = new HolderView();
			convertView =LayoutInflater.from(context).inflate(R.layout.list_item_data, null);
	        holderView.iv_icon =(ImageView) convertView.findViewById(R.id.img_list_item);
	        holderView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
	        holderView.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
	        holderView.tv_information = (TextView) convertView.findViewById(R.id.tv_information);
	        convertView.setTag(holderView);
	        
		}else{
			holderView = (HolderView) convertView.getTag();
		}	
		ArrayList<HashMap<String,Object>> data = new ListViewData().buildData();
		for (HashMap<String, Object> hashMap : data) {
			System.out.println(hashMap.toString());
		}
		
		
		holderView.tv_name.setText((String) data.get(position).get("name"));
		holderView.tv_time.setText((String)data.get(position).get("time"));
	    holderView.tv_information.setText((String)data.get(position).get("information"));
	    
		
		return convertView;
	}

	
	public class HolderView {
		
		private ImageView iv_icon;
		private TextView tv_information;
		private TextView tv_name;
		private TextView tv_time;
		
	}
}
