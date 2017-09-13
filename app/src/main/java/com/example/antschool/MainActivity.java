package com.example.antschool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b334.antschool.R;
import com.wang.tool.Logining;
import com.wang.tool.RsaUtil;

public class MainActivity extends Activity {
	EditText et_username;
	EditText et_passward;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_username = (EditText) findViewById(R.id.et_signup_username);
		et_passward = (EditText) findViewById(R.id.et_signup_password);
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
					String data = Logining.first(name, "hzkjzyjsxy");
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
