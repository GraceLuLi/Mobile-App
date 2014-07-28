package com.betterman.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.R;

public class MenuActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_page);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.go_register_page:
			//intent.setClass(MenuActivity.this, RegisterActivity.class);
			//MenuActivity.this.startActivity(intent);
			 //intent = new Intent("android.intent.action.REGISTER"); 
			//MenuActivity.this.startActivity(intent); 
			startActivity(new Intent(MenuActivity.this, RegisterActivity.class));
			break;
		case R.id.go_scanner_page:
			startActivity(new Intent(MenuActivity.this, ScannerActivity.class));
			break;
		case R.id.go_light_page:
			startActivity(new Intent(MenuActivity.this, LightActivity.class));
			break;
		case R.id.go_search_page:
			startActivity(new Intent(MenuActivity.this, SearchActivity.class));
			break;
		default:
			break;
		}
	}

}
