package com.betterman.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.R;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText mAccount;
	private EditText mPwd;
	private Button mLoginButton;
	private TextView tv_msg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mAccount = (EditText) findViewById(R.id.login_edit_account);
		mPwd = (EditText) findViewById(R.id.login_edit_pwd);
		mLoginButton = (Button) findViewById(R.id.login_btn_login);
		tv_msg = (TextView) findViewById(R.id.show_msg);
	}

	@Override
    public void onClick(View v) {
		//Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.login_btn_login:
			login();
			break;
		case R.id.go_menu_page:
			startActivity(new Intent(LoginActivity.this, MenuActivity.class));
			break;
		default:
			break;
		}
	}
	

	public void login() {
	    String key = "12345678";
		String userName = mAccount.getText().toString().trim();
		String userPwd = mPwd.getText().toString().trim();
		if(userName == null || userName.length() == 0)
		{
			tv_msg.setText("Username can't be empty!");
		}
		else if(userPwd == null || userPwd.length() == 0)
		{
			tv_msg.setText("Password can't be empty!");
		}
        try {
            String des_name = DES.encryptDES(userName,key);
            String des_pw = DES.encryptDES(userPwd, key);              
            
        if(des_name!= null && des_pw!=null){
    		String urlpath = "http://ee579finalpro1.appspot.com/ee579finalpatient";
    		//tv_name.setText(urlpath);
    		HttpPost request = new HttpPost(urlpath);
			//tv_name.setText("Step1");
			//request.setURI(new URI());
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("Username",des_name));
			params.add(new BasicNameValuePair("Password",des_pw));
			//tv_name.setText("Step2");
			HttpEntity formEntity = new UrlEncodedFormEntity(params);
			//tv_name.setText("Step3");
			request.setEntity(formEntity);
			//tv_name.setText("Step4");
			HttpClient client = new DefaultHttpClient();
			//tv_name.setText("Step5");
			HttpResponse httpResponse = client.execute(request);
			//tv_name.setText("Step6");
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				if(strResult.contains("name not exist"))
				{
					tv_msg.setText("Username not exist!");
				}
				else if(strResult.contains("wrong password"))
				{
					tv_msg.setText("Password is wrong! Please try again.");
				}
				else if(strResult.contains("okay"))
				{
					// enter the patient register page
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, RegisterActivity.class);
					LoginActivity.this.startActivity(intent);
				}
				else
				{
					tv_msg.setText("Something wrong when get data from our server!");
				}
			}
			else
			{
				tv_msg.setText("Trouble!!!");
			}
			//request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			//tv_name.setText((CharSequence) nameValuePairs);
			//client.execute(request);
/**********************************************************************************************/
			//get the result from the google app engine
			
			//HttpResponse response = client.execute(request);
			//if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
			//	InputStream is = response.getEntity().getContent();   
			//	String result = inStream2String(is);  
			//	Assert.assertEquals(result, "POST_SUCCESS");  
			//	}
			//HttpEntity he = response.getEntity();
			//InputStream is = he.getContent();
			//BufferedReader br = new BufferedReader(new InputStreamReader(is));
			//String resultString = br.readLine();
			//tv_name.setText("");//wait for delete
			//tv_name.setText(resultString);//wait for delete
			
			}
        
        
				else{
					
				}
            
    } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
           tv_msg.setText("Http Error!");
    }

}





}
