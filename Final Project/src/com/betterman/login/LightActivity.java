package com.betterman.login;

import org.openintents.sensorsimulator.hardware.Sensor; 

import com.google.zxing.client.android.R;

import android.app.Activity; 
import android.hardware.SensorManager; 
import android.os.Bundle; 
import android.widget.ImageView;
import android.widget.TextView; 

public  class LightActivity extends Activity implements  android.hardware.SensorEventListener { 

    private TextView myTextView1; 
    private ImageView myImageView1;
    private SensorManager mySensorManager; 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.light); 
        myTextView1 = (TextView) findViewById(R.id.brightness); 
        myImageView1 = (ImageView) findViewById(R.id.imageView1);
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
    } 
    @Override 
    protected void onResume() { 
        mySensorManager.registerListener( 
                this, 
                mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), 
                SensorManager.SENSOR_DELAY_GAME 
                
                
                ); 
        super.onResume(); 
    } 
    @Override 
    protected void onStop() { 
        // TODO Auto-generated method stub 
        mySensorManager.unregisterListener(this); 
        super.onStop(); 
    } 
    @Override 
    protected void onPause() { 
        mySensorManager.unregisterListener(this); 
        super.onPause(); 
    } 
   
    @Override 
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) { 
        // TODO Auto-generated method stub 
           
    } 

    @Override 
    public void onSensorChanged(android.hardware.SensorEvent event) { 
        // TODO Auto-generated method stub 
        float[] values = event.values; 
        int sensorType = event.sensor.TYPE_LIGHT; 
        if (sensorType == Sensor.TYPE_LIGHT) { 
            myTextView1.setText("Brightness:\n"+values[0] +"lux"); 
            float value = values[0];
            if(value<=SensorManager.LIGHT_CLOUDY)
            {
            	myImageView1.setImageResource(R.drawable.p1);//
            }else if(value>SensorManager.LIGHT_CLOUDY && value<=SensorManager.LIGHT_SUNRISE)
            {
            	myImageView1.setImageResource(R.drawable.p2);//
            }else if(value>SensorManager.LIGHT_SUNRISE && value<=SensorManager.LIGHT_OVERCAST)
            {
            	myImageView1.setImageResource(R.drawable.p3);//
            }else if(value>SensorManager.LIGHT_OVERCAST && value<=SensorManager.LIGHT_SHADE)
            {
            	myImageView1.setImageResource(R.drawable.p4);//
            }else if(value>SensorManager.LIGHT_SHADE && value<=SensorManager.LIGHT_SUNLIGHT)
            {
            	myImageView1.setImageResource(R.drawable.p5);//
            }
        } 
    } 
}
