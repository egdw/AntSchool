package com.b334.entities;

import java.util.ArrayList;

public class ImagUrl {

	private ArrayList<String> url;

	public ArrayList<String> getUrl() {
		return url;
	}

	public void setUrl(ArrayList<String> url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ImagUrl [url=" + url + "]";
	}

}
