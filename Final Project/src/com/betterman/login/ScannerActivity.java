package com.betterman.login;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.betterman.util.RGBLuminanceSource;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.R;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

public class ScannerActivity extends Activity implements OnClickListener {

	private TextView tv_name, tv_time,result,tv_mac;
	private RadioGroup radiogroup;
	final String[] status = new String[1];
	//final String[] macAddr = new String[1];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanner);
		init();
	}

	private void init() {
	    tv_name = (EditText) findViewById(R.id.NameText);
		tv_time = (EditText) findViewById(R.id.TimeText);
		result = (EditText) findViewById(R.id.result);
		//tv_mac = (EditText) findViewById(R.id.macAddress);
		radiogroup=(RadioGroup)findViewById(R.id.radioGroup1); 
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
			  
            @Override  
                public void onCheckedChanged(RadioGroup group, int checkedId) {  
                
                    if (checkedId == R.id.radio0) {  
                        status[0] = "Check In";
                    } else {  
                        status[0] = "Check Out"; 
                    }  
                }  
          });  
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reset:
			tv_name.setText("");
            tv_time.setText("");
			break;
		case R.id.checkQR:
			//intent.setClass(MainActivity.this, CaptureActivity.class);
			//MainActivity.this.startActivity(intent);
			Intent intent =new Intent("com.google.zxing.client.android.SCAN");//���������������actity,���������������������������������������BarcodeScanner3������������������actity
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");//���������������������������������..������������
            startActivityForResult(intent, 0);
            break;
		case R.id.submit:
			submit();
			break;
//		case R.id.getMacAddr:
//			getMac();
//			break;
		default:
			break;
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode ==0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
                
                tv_name.setText(contents);//���������������textveiw������������������������
                // set system time
               // Time time = new Time("GMT+8");    
               // time.setToNow(); 
                //tv_time.setText(time.month+"/" + time.monthDay+"/"+time.year+" \n" + time.hour + ":"+time.minute+":"+time.second);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       
                Date curDate = new Date(System.currentTimeMillis());//������������������       
                tv_time.setText(formatter.format(curDate)); 
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                tv_name.setText(" ERROR!");
            }
        }
    }
	//get MAC address
//	private void getMac(){
//		macAddr[0] = getLocalMacAddress();
//		tv_mac.setText(macAddr[0]);
//		
//	}
	public String getLocalMacAddress() {
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}
	private void submit() {
		String key = "12345678";
		//String macAddr0 = macAddr[0];
		String macAddr0 = getLocalMacAddress();
		String name = tv_name.getText().toString();
		String time = tv_time.getText().toString();
		String status0 = status[0];
//		if(date0!= null && gender0 != null)
//		{
//			tv_name.setText(date0+" "+ gender0);
//		}
        try {
        	    String des_macAddr = DES.encryptDES(macAddr0,key);
                String des_name = DES.encryptDES(name,key);
                String des_time = DES.encryptDES(time, key);
                String des_status = DES.encryptDES(status0,key);
              
                
            if(macAddr0!= null && name!= null && time != null && status != null){
        		String urlpath = "http://ee579finalpro3.appspot.com/ee579finalguidance";
        		//tv_name.setText(urlpath);
        		HttpPost request = new HttpPost(urlpath);
				tv_name.setText("Step1");
				//request.setURI(new URI());
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("MacAdd",des_macAddr));
				params.add(new BasicNameValuePair("Name",des_name));
				params.add(new BasicNameValuePair("Time",des_time));
				params.add(new BasicNameValuePair("Status",des_status));
				tv_name.setText("Step2");
				HttpEntity formEntity = new UrlEncodedFormEntity(params);
				tv_name.setText("Step3");
				request.setEntity(formEntity);
				tv_name.setText("Step4");
				HttpClient client = new DefaultHttpClient();
				tv_name.setText("Step5");
				HttpResponse httpResponse = client.execute(request);
				tv_name.setText("Step6");
				if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				{
					String strResult = EntityUtils.toString(httpResponse.getEntity());
					result.setText(strResult);
				}
				else
				{
					result.setText("Trouble!!!");
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
                result.setText("Http Error!");
        }

	}
	
}
