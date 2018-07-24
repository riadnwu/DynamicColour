package com.example.riadul.dynamiccolour;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Random;

public class DynamicColour extends AppCompatActivity {

    private RelativeLayout layout;
    private SensorManager sensor;
    private float gravityFast,gravityLast,sake,x1,y1,z1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_colour);

        sensor=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor.registerListener(sensorEvent,sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        gravityFast=gravityLast=SensorManager.GRAVITY_EARTH;
        sake=2.5f;

    }
    private SensorEventListener sensorEvent=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {


             x1=event.values[0];
             y1=event.values[1];
             z1=event.values[2];


            gravityLast=gravityFast;
            gravityFast=(float) Math.sqrt((x1*x1)+(y1*y1)+(z1*z1)); // Magnitude of the acceleration: √x^2+y^2+z^2
            sake=sake*0.9f+(gravityFast-gravityLast);

           // Toast.makeText(DynamicColour.this,"x:"+x1+" y:"+y1+"z:"+z1+"",Toast.LENGTH_SHORT).show();

            if(sake>5)  // when  sake accuracy is gater then five
            {
                ChangeColour();
            }

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void ChangeColour()
    {
        TextView text=(TextView) findViewById(R.id.text);
        Random random=new Random(); // Variable of Random generator
        layout=(RelativeLayout) findViewById(R.id.layout); //Variable of Relaative layout
        int w,x,y,z;    // Four variable for Background colour on Relative Layout
        w= random.nextInt(256); // alpha is a value 0 to 255
        x= random.nextInt(256); // x is denoted Red
        y= random.nextInt(256); // y is denoted Green
        z= random.nextInt(256); // z is denoted Blue
        text.setText("SensorEvent  x: "+x1+"   y: "+y1+"   z: "+z1+"\n\n"+"Magnitude of the acceleration: "+gravityFast+
                "\n\n"+"Sake Accuracy: "+sake+"\n\n"+"Colour Code: "+w+" : "+x+" : "+y+" : "+z+"");
       // Toast.makeText(DynamicColour.this,"Colour Code:"+w+" : "+x+" : "+y+" : "+z+"",Toast.LENGTH_SHORT).show();
        layout.setBackgroundColor(Color.argb(w,x,y,z)); //Set Background colour to layout
    }

}
