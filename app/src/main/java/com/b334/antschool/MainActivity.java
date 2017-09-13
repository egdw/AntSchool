package com.b334.antschool;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.b334.utils.Logining;
import com.b334.utils.RsaUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText et_username;
	EditText et_passward;
	private Button btnStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_username = (EditText) findViewById(R.id.et_signup_username);
		et_passward = (EditText) findViewById(R.id.et_signup_password);
		btnStart = (Button) findViewById(R.id.signup_Btna);
		
		
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent   = new Intent();
				intent.setClass(getApplication(), ViewPageActivity.class);
				startActivity(intent);
				
				
			}
		});
		
		
	}

	String name = null;
	String password = null;

	public void MyLogin(View view) {

		name = et_username.getText().toString();
		password = et_passward.getText().toString();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					String data = Logining.first(name, "hzkjzyjsxy   ");
				String strings[]=RsaUtil.createKeyPairs();
					String second = Logining.second(strings, data);
					if(second.equals("401")){
						
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();

	}


	
	
}
