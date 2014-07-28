package com.betterman.login;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.R;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class RegisterActivity extends Activity implements OnClickListener {
	
	private static final String TAG = "TestActivity";
	private final static int QR_WIDTH = 200, QR_HEIGHT = 200;
	private ImageView iv_image;
	private TextView tv_name;
	private TextView tv_weight;
	private TextView tv_height;
	private TextView result;
	private RadioGroup radiogroup;  
    private RadioButton radio0,radio1; 
    final String[] gender = new String[1];
    final String[] date = new String[1];
    private DatePicker datePicker; 
    private Calendar ca; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		init();
		
	}

	private void init() {
		iv_image = (ImageView) findViewById(R.id.image);
		tv_name = (EditText) findViewById(R.id.name);
		tv_weight = (EditText) findViewById(R.id.weight);
		tv_height = (EditText) findViewById(R.id.height);
		result = (EditText) findViewById(R.id.test);
		radiogroup=(RadioGroup)findViewById(R.id.radioGroup1); 
       // radio0=(RadioButton)findViewById(R.id.radio0);  
        //radio1=(RadioButton)findViewById(R.id.radio1);
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
			  
            @Override  
                public void onCheckedChanged(RadioGroup group, int checkedId) {  
                // 根据ID判断选择的按钮  
                    if (checkedId == R.id.radio0) {  
                        gender[0] = "male";
                    } else {  
                        gender[0] = "female"; 
                    }  
                }  
          });  
		datePicker = (DatePicker) findViewById(R.id.datePicker1);
		ca = Calendar.getInstance();  
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.showDate:
			showDate();
			break;
		case R.id.setDate:
			setDate();
			break;
		case R.id.genBarcode:
			createImage();
			break;
		case R.id.submit:
			submit();
			break;
		default:
			break;
		}
	}
	//showdDate
	private void showDate()
	{
		findViewById(R.id.datePicker1).setVisibility(View.VISIBLE); 
		datePicker.init(ca.get(Calendar.YEAR), ca.get(Calendar.MONDAY),  
                ca.get(Calendar.DAY_OF_MONTH),  
                new DatePicker.OnDateChangedListener() {  
  
                    @Override  
                    public void onDateChanged(DatePicker view, int year,  
                            int monthOfYear, int dayOfMonth) {  
                    	
                    	date[0] = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                    	result.setText(date[0]); 
                    }  
                   
                }); 
	}
	//setDate
	private void setDate()
	{
		findViewById(R.id.datePicker1).setVisibility(View.INVISIBLE); 	             
	}
	//submit
		private void submit() {
			String key = "12345678";
			String name = tv_name.getText().toString();
			String weight = tv_weight.getText().toString();
			String height = tv_height.getText().toString();	
			String gender0 = gender[0];
			String date0 = date[0];
//			if(date0!= null && gender0 != null)
//			{
//				tv_name.setText(date0+" "+ gender0);
//			}
	        try {
	                String des_name = DES.encryptDES(name,key);
	                String des_gender = DES.encryptDES(gender0, key);
	                String des_weight = DES.encryptDES(weight,key);
	                String des_height = DES.encryptDES(height,key);
	                
	                String des_birthday = DES.encryptDES(date0, key);
	              
	                
                if(name!= null && weight != null && height != null&& gender0!=null&&date0!=null){
            		String urlpath = "http://ee579finalpro1.appspot.com/ee579finalpatient";
            		//tv_name.setText(urlpath);
            		HttpPost request = new HttpPost(urlpath);
    				tv_name.setText("Step1");
    				//request.setURI(new URI());
    				List<NameValuePair> params = new ArrayList<NameValuePair>();
    				params.add(new BasicNameValuePair("Name",des_name));
    				params.add(new BasicNameValuePair("Gender",des_gender));
    				params.add(new BasicNameValuePair("Weight",des_weight));
    				params.add(new BasicNameValuePair("Height",des_height));
    				params.add(new BasicNameValuePair("Birthday",des_birthday));
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
		
		private String inStream2String(InputStream is) throws Exception {  
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			byte[] buf = new byte[1024];  
			int len = -1;  
			while ((len = is.read(buf)) != -1) {  
			baos.write(buf, 0, len);  
			}   
			return new String(baos.toByteArray());   
			} 

	//encode
	private void createImage() {
		
		//submit();
		//tv_name.setText("name has setted");
		//tv_weight.setText("w has jj");
		//tv_height.setText("h has sp");
		try {
			QRCodeWriter writer = new QRCodeWriter();

			String text = tv_name.getText().toString();

			Log.i(TAG, "������������������" + text);
			if (text == null || "".equals(text) || text.length() < 1) {
				return;
			}

			BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
					QR_WIDTH, QR_HEIGHT);

			System.out.println("w:" + martix.getWidth() + "h:"
					+ martix.getHeight());

			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}

				}
			}

			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			iv_image.setImageBitmap(bitmap);

		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
}