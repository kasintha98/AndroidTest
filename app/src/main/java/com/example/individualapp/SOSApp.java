package com.example.individualapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SOSApp extends AppCompatActivity {

    MapsActivity mapsActivity = new MapsActivity();
    double latitude = mapsActivity.latitude;
    double longitude = mapsActivity.longitude;
    boolean stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sosapp);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
    }

    public void sendAlert(View view){
        String name = "Kasintha";
        String ImNo = "IM/2017/031";
        String contactNo = "0783557926";
        stop=false;

        //I’m " + name + " " + ImNo +". Please Help Me. I’m in

        String message = "http://maps.google.com/?q="+latitude+","+longitude+".";

        //String aaa = "long: "+ longitude + " lat : "+latitude;
        try {
            SmsManager smsManager= SmsManager.getDefault();

            while(stop ==false){
                Thread thread = new Thread(new Runnable()
                {
                    int lastMinute;
                    int currentMinute;
                    @Override
                    public void run()
                    {
                        lastMinute = currentMinute;
                        while (true)
                        {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());
                            currentMinute = calendar.get(Calendar.MINUTE);
                            if (currentMinute != lastMinute){
                                lastMinute = currentMinute;
                                smsManager.sendTextMessage(contactNo,null,message,null,null);
                            }
                        }
                    }
                });
                thread.run();
            }


            Toast.makeText(getApplicationContext(),"Alert SMS Sent to your emergency contact person!",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Alert SMS Sending Failed",Toast.LENGTH_LONG).show();
        }
    }

    public void onStop(View view){
        stop = true;
        Toast.makeText(getApplicationContext(),"Alert SMS SendingStopped",Toast.LENGTH_LONG).show();
    }
}

