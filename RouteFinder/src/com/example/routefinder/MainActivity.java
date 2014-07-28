package com.example.routefinder;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView loc;
	private RadioGroup rg;
	private RadioButton rbutton1;
	private RadioButton rbutton2;
	private RadioButton rbutton3;
	private RadioButton rbutton4;
	private RadioButton rbutton5;
	private RadioButton rbutton6;
	private RadioButton rbutton7;
	private RadioButton rbutton8;
	private RadioButton rbutton9;
	private RadioButton rbutton10;
	private Button button1;
	private Button button2;
	private Button button3;
	private TextView status;
	private LocationManager locationManager;
    private String currentlocation;
	Criteria criteria = new Criteria();

	private String[] blue={"Transit Mall","Anaheim","Pacific Coast Highway","Willow","Wardlow","Del Amo",
    		"Artesia","Compton","Willowbrook","103rd Street/Watts Towers","Firestone",
    		"Florence ","Slauson","Vernon","Washington","San Pedro","Grand","Pico","7th Street/Metro Center"};
    
	private String[] red={"North Hollywood","Universal City","Hollywood/Highland","Hollywood/Vine",
			"Hollywood/Western","Vermont/Sunset","Vermont/Santa Monica","Vermont/Beverly",
			"Wilshire/Vermont","Westlake/MacArthur Park","7th Street/Metro Center","Pershing Square",
			"Civic Center","Los Angeles Union Station"};
	private String[] gold={"Sierra Madre Villa","Allen","Lake","Memorial Park","Del Mar","Fillmore",
			"South Pasadena","Highland Park","Southwest Museum","Heritage Square","Lincoln/Cypress",
			"Chinatown","Los Angeles Union Station","Little Tokyo/Arts District","Pico/Aliso","Mariachi Plaza",
			"Soto","Indiana","Maravilla","East LA Civic Center","Atlantic"};
	private String[] green={"Norwalk","Lakewood Boulevard","Long Beach","Willowbrook","Avalon",
			"Harbor Freeway ","Vermont/Athens","Crenshaw","Hawthorne/Lennox","Aviation/LAX",
			"Mariposa","El Segundo","Douglas","Redondo Beach"};
	
    private final LocationListener locationListener = new LocationListener() {

			public void onLocationChanged(Location location) {

			updateWithNewLocation(location);}

			public void onProviderDisabled(String provider){

			updateWithNewLocation(null);}

			public void onProviderEnabled(String provider){ }

			public void onStatusChanged(String provider, int status,Bundle extras){ }};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rg=(RadioGroup)findViewById(R.id.RG);
    	rbutton1=(RadioButton)findViewById(R.id.radioButton1);
    	rbutton2=(RadioButton)findViewById(R.id.radioButton2);
    	rbutton3=(RadioButton)findViewById(R.id.radioButton3);
    	rbutton4=(RadioButton)findViewById(R.id.radioButton4);
    	rbutton5=(RadioButton)findViewById(R.id.radioButton5);
    	rbutton6=(RadioButton)findViewById(R.id.radioButton6);
    	rbutton7=(RadioButton)findViewById(R.id.radioButton7);
    	rbutton8=(RadioButton)findViewById(R.id.radioButton8);
    	rbutton9=(RadioButton)findViewById(R.id.radioButton9);
    	rbutton10=(RadioButton)findViewById(R.id.radioButton10);	
    	button1=(Button)findViewById(R.id.button1);
    	button2=(Button)findViewById(R.id.button2);
    	button3=(Button)findViewById(R.id.button3);
    	loc=(TextView)findViewById(R.id.editText1);
    	status=(TextView)findViewById(R.id.editText2);
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(serviceName);
       
    	criteria.setAccuracy(Criteria.ACCURACY_FINE);
    	criteria.setAltitudeRequired(false);
    	criteria.setBearingRequired(false);
    	criteria.setCostAllowed(true);
    	criteria.setPowerRequirement(Criteria.POWER_LOW);
    	
    	String provider = locationManager.getBestProvider(criteria, true);
    	Location location = locationManager.getLastKnownLocation(provider);
    	updateWithNewLocation(location);
    	locationManager.requestLocationUpdates(provider, 2000, 10,locationListener);
    	
    	//get the current location
	    button1.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View v){
	    		loc.setText(currentlocation);
	    		
            	}	     	
	    	});
	    
	    //find the fastest path
	    button2.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View v){
	    		if(rbutton1.isChecked()){
	    			getpath("1");
				}
	    		if(rbutton2.isChecked()){
	    			getpath("2");
				}
	    		if(rbutton3.isChecked()){
	    			getpath("3");
				}
	    		if(rbutton4.isChecked()){
	    			getpath("4");
				}
	    		if(rbutton5.isChecked()){
	    			getpath("5");
				}
	    		if(rbutton6.isChecked()){
	    			getpath("6");
				}
	    		if(rbutton7.isChecked()){
	    			getpath("7");
				}
	    		if(rbutton8.isChecked()){
	    			getpath("8");
				}
	    		if(rbutton9.isChecked()){
	    			getpath("9");
				}
	    		if(rbutton10.isChecked()){
	    			getpath("0");
				}
	    		
            	}	 
	  
	    	
	    	});
	    //show the map
	    button3.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View v){
	    	//	ImageView iv = (ImageView) findViewById(R.id.image_test);  
	    	//	Bitmap bit = BitmapFactory.decodeFile("/storage/sdcard0/DCIM/Camera/metroMap.jpg");   
	    		
	    		
            	}	     	
	    	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
    private void updateWithNewLocation(Location location) {

    	//TextView myLocationText;

    	if (location != null){
    		double lat = location.getLatitude();
    		double lng = location.getLongitude();
    		currentlocation = format(lat) +","+ format(lng);
    		
    	} else {
    		currentlocation = "Cannot find location";
    		
    	}

    	
    }
    public String format(double num){
    	NumberFormat formatter = new DecimalFormat("0.0000");
    	String s= formatter.format(num);
    	return s;
    	
    	
    }	
    public void getpath(String dest){
		String uriAPI = "http://ee579assin4.appspot.com/appassin4?sValue="+dest+currentlocation;
		//status.setText(uriAPI);
		HttpPost httpRequest = new HttpPost(uriAPI);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("str", "post string"));
	
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params,
					HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);
			//status.setText(uriAPI);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
			
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());

			    ShowRoute(strResult.substring(0,strResult.length()-1));
					
		
			} else {
				//mTextView1.setText("Error Response: "+ httpResponse.getStatusLine().toString());
			}

		} catch (ClientProtocolException e) {
			//mTextView1.setText(e.getMessage().toString());
			e.printStackTrace();
		} catch (IOException e) {
			//mTextView1.setText(e.getMessage().toString());
			e.printStackTrace();
		} catch (Exception e) {
			//mTextView1.setText(e.getMessage().toString());
			e.printStackTrace();
		}
    	
    }
    public void ShowRoute(String strResult){
    	//status.setText(strResult);
    	String[] strs = strResult.split(","); 
    	if(strs[2].equals("NO")){
    		status.setText("No need to take subway.They are close to the same station.");
    		return;
    	}
    	String s="The distance to the nearest distance is "+format(Double.parseDouble(strs[0]))+"km\n"+
    	"The distance from the last station to destination is "+format(Double.parseDouble(strs[1]))+"km\n";
    	s=s+"\nRoute:\n";
    	int flag=Integer.parseInt(strs[2].substring(0,1));
    	int stn=Integer.parseInt(strs[2].substring(1));
    	
    	if(flag==1){
    		s=s+"blue: ";
    		s=s+blue[stn];
    	}else if(flag==2){
    		s=s+"red: ";
    		s=s+red[stn];
    	}else if(flag==3){
    		s=s+"gold: ";
    		s=s+gold[stn];
    	}else{
    		s=s+"green:";
    		s=s+green[stn];
    	}
    
    	for(int i=3;i<strs.length;i++){
    		
    	 int line=Integer.parseInt(strs[i].substring(0,1));
    	 int point=Integer.parseInt(strs[i].substring(1));
    	 //status.setText(strResult+i);
    	 if(line!=flag){
    		 
    		 s=s+"\n transition time: 10min";
    			if(line==1){
    	    		s=s+"\nblue: ";
    	    		s=s+blue[point];
    	    	}else if(line==2){
    	    		s=s+"\nred: ";
    	    		s=s+red[point];
    	    	}else if(line==3){
    	    		s=s+"\ngold";
    	    		s=s+gold[point];
    	    	}else{
    	    		s=s+"\ngreen";
    	    		s=s+green[point];
    	    	}
    			flag=line;
    	 }else{
    		
    		 if(line==1){
    			 s=s+",\n       "+blue[point];
    		 }else if(line==2){
    			 s=s+",\n       "+red[point];
    		 }else if(line==3){
    			 s=s+",\n       "+gold[point];
    		 }else{
    			 s=s+",\n       "+green[point];
    		 }
    		// status.setText(strResult+line+point);
    	 }
    
    	}
          status.setText(s);
    	return;
    }

}
