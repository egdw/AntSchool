package com.b334.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;

public class Logining {
	public static String first(String username, String password)
			throws Exception {
		// Log.i("sss", "6666");
		URL url = new URL("http://115.159.214.202/AntSchool/login/getPublicKey?id="
				+ username);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod("POST");

		connection.setDoInput(true);

		InputStream inputStream = connection.getInputStream();
		byte[] bytes = new byte[512];
		StringBuffer sb = new StringBuffer();
		int len = -1;

		while ((len = inputStream.read(bytes)) != -1) {
			sb.append(new String(bytes, 0, len));
		}
		inputStream.close();
		System.err.println(sb.toString());
		Status status = JSON.parseObject(sb.toString(), Status.class);
		// System.out.println(status);
		String message = status.getMessage();
		String data = RsaUtil.encryptData(password, message);

		System.out.println("加密后的密码:" + data);
		return URLEncoder.encode(data, "utf-8");
	}

	public static String second(String[] s, String password) throws Exception {
		System.out.println("自己的公钥:" + s[0]);
		System.out.println("自己的密钥:" + s[1]);
		StringBuffer sb2 = new StringBuffer(
				"http://115.159.214.202/AntSchool/login/login?id=2015002530");
		sb2.append("&&password=").append(password).append("&&publicKey=")
				.append(s[0]);
		URL url = new URL(sb2.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		byte[] bytes = new byte[512];
		StringBuffer sb = new StringBuffer();
		int len = -1;
		while ((len = inputStream.read(bytes)) != -1) {
			sb.append(new String(bytes, 0, len));
		}
		Status status = JSON.parseObject(sb.toString(), Status.class);
		System.out.println(status);
		int i = status.getStatus();
		if (i == 200) {
			System.out.println("获取token");
			return RsaUtil.decryptData(status.getMessage(), s[1]);
		}
		System.out.println(i);
		return ""+i;
	}

}
