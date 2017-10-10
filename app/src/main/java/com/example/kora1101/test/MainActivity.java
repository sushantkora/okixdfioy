package com.example.kora1101.test;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private Sensor temperature;
    private Sensor pressure;

    TextView X_value;
    TextView Y_value;
    TextView Z_value;
    TextView Gyro1;
    TextView Gyro2;
    TextView Gyro3;
    TextView Mag1;
    TextView Mag2;
    TextView Mag3;
    TextView Light,Pressure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        X_value=(TextView)findViewById(R.id.acceX);
        Y_value=(TextView)findViewById(R.id.acceY);
        Z_value=(TextView)findViewById(R.id.acceZ);

        Gyro1=(TextView)findViewById(R.id.gyro1);
        Gyro2=(TextView)findViewById(R.id.gyro2);
        Gyro3=(TextView)findViewById(R.id.gyro3);

        Mag1=(TextView)findViewById(R.id.mag1);
        Mag2=(TextView)findViewById(R.id.mag2);
        Mag3=(TextView)findViewById(R.id.mag3);

        Light =(TextView)findViewById(R.id.light);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


    }
    protected void onResume() {
        super.onResume();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            temperature = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            pressure = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if ( sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x, y, z;
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            X_value.setText(Float.toString(x));
            Y_value.setText(Float.toString(y));
            Z_value.setText(Float.toString(z));

        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x, y, z;
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            Gyro1.setText(Float.toString(x));
            Gyro2.setText(Float.toString(y));
            Gyro3.setText(Float.toString(z));
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            float t;
            t = sensorEvent.values[0];
            Light.setText(Float.toString(t));
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x, y, z;
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            Mag1.setText(Float.toString(x));
            Mag2.setText(Float.toString(y));
            Mag3.setText(Float.toString(z));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public  void OnButtonClick(View view){
        Intent intent=new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent);
        finish();
    }
}