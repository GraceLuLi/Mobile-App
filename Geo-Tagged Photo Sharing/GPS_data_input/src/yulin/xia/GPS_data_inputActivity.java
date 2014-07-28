package yulin.xia;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GPS_data_inputActivity extends Activity {
	public String storage="";
	 private final String        DEBUG_TAG   = "Activity01";  
     
	    private TextView    mTextView=null;  
	 
	    private Button      mButton=null;  
	
	 private void updateWithNewLocation(Location location) {
	        String latLongString="";
	        TextView myLocationText;
	        TextView myLocation;
	        myLocationText = (TextView)findViewById(R.id.myLocationText);
	        myLocation=(TextView)findViewById(R.id.myLocation);
	        if (location != null) {
	        double lat = location.getLatitude();
	        double lng = location.getLongitude();
	        
	        
	        latLongString = "lat:" + lat + "lng:" + lng;
	        } else {
	        latLongString = "can't get location";
	        }
	        storage=storage+"\n"+latLongString;
	        myLocationText.setText("your current postion:\n" +
	        latLongString);
	        myLocation.setText(storage); 
	 
	 }
	 private final LocationListener locationListener = new LocationListener() {
	        public void onLocationChanged(Location location) {
	        updateWithNewLocation(location);
	        }
	        public void onProviderDisabled(String provider){
	        updateWithNewLocation(null);
	    }
	    public void onProviderEnabled(String provider){ }
	    public void onStatusChanged(String provider, int status,
	    Bundle extras){ }
	};
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	{ super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(serviceName);
        //String provider = LocationManager.GPS_PROVIDER;
            
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        
        Location location = locationManager.getLastKnownLocation(provider);
        updateWithNewLocation(location);
        /*每隔1000ms更新一次，并且不考虑位置的变化。*/
        locationManager.requestLocationUpdates(provider, 1000, 1,
                locationListener); 
        mButton = (Button)findViewById(R.id.Button01);  
        mTextView=(TextView)findViewById(R.id.TextView01);  
    
          
        //登陆  
        
        mButton.setOnClickListener(new OnClickListener()  
        {  
            public void onClick(View v)  
            {  
                Socket socket = null;  
                String message =storage;   
                try   
                {     
                    //创建Socket  
                    socket = new Socket("207.151.231.151",3021); //查看本机IP,每次开机都不同  
                    //socket=new Socket("192.168.1.110",50000);  
                    //向服务器发送消息  
                    PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);        
                    out.println(message);   
                      
                    //接收来自服务器的消息  
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));   
                    String msg = br.readLine();   
                      
                    if ( msg != null )  
                    {  
                        mTextView.setText(msg);  
                    }  
                    else  
                    {  
                        mTextView.setText("success");  
                    }  
                    //关闭流  
                    out.close();  
                    br.close();  
                    //关闭Socket  
                    socket.close();   
                }  
                catch (Exception e)   
                {  
                    // TODO: handle exception  
                    Log.e(DEBUG_TAG, e.toString());  
                }  
            }  
        });  
    	}
		
    	
    }
    
}
