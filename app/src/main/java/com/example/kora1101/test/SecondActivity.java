package com.example.kora1101.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
/**
 * Created by kora1101 on 10/10/2017.
 */

public class SecondActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private Sensor temperature;
    private Sensor pressure;

    GraphView graph_Acce,graph_Gyro, graph_Light, graph_Mag;
    private LineGraphSeries<DataPoint> series_Acc, series_Gyro, series_Light, series_Mag;

    HandlerClass handlerClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        handlerClass=new HandlerClass();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        graph_Acce=(GraphView)findViewById(R.id.graphViewAcce);
        graph_Acce.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        // graph_Acce.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        graph_Gyro=(GraphView)findViewById(R.id.graphViewGyro);
        graph_Gyro.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        //graph_Gyro.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        graph_Light =(GraphView)findViewById(R.id.graphViewTemperature);
        graph_Light.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        // graph_Light.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        graph_Mag =(GraphView)findViewById(R.id.graphViewGyropressure);
        graph_Mag.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        // graph_Mag.getViewport().setScalableY(true); // enables vertical zooming and scrolling

      /*  graph_Acce.getViewport().setYAxisBoundsManual(true);
        graph_Acce.getViewport().setMinY(0);
        graph_Acce.getViewport().setMaxY(30);
        graph_Light.getViewport().setYAxisBoundsManual(true);
        graph_Light.getViewport().setMinY(0);
        graph_Light.getViewport().setMaxY(30);

        graph_Gyro.getViewport().setYAxisBoundsManual(true);
        graph_Gyro.getViewport().setMinY(0);
        graph_Gyro.getViewport().setMaxY(10);
        graph_Mag.getViewport().setYAxisBoundsManual(true);
        graph_Mag.getViewport().setMinY(0);
        graph_Mag.getViewport().setMaxY(30);
*/
        // data
        series_Acc = new LineGraphSeries<DataPoint>();
        series_Acc.setTitle("Accelerometer");
        series_Acc.setColor(Color.RED);

        series_Gyro = new LineGraphSeries<DataPoint>();
        series_Gyro.setTitle("Gyrometer");
        series_Gyro.setColor(Color.GREEN);

        series_Light = new LineGraphSeries<DataPoint>();
        series_Light.setTitle("Light");
        series_Light.setColor(Color.YELLOW);

        series_Mag = new LineGraphSeries<DataPoint>();
        series_Mag.setTitle("Magnetic Field");
        series_Mag.setColor(Color.BLUE);

        graph_Acce.getLegendRenderer().setVisible(true);
        graph_Acce.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph_Gyro.getLegendRenderer().setVisible(true);
        graph_Gyro.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph_Light.getLegendRenderer().setVisible(true);
        graph_Light.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph_Mag.getLegendRenderer().setVisible(true);
        graph_Mag.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graph_Acce.addSeries(series_Acc);
        graph_Gyro.addSeries(series_Gyro);
        graph_Light.addSeries(series_Light);
        graph_Mag.addSeries(series_Mag);

    }
    int indexAcce=0,indexGyro=0, indexLight =0, indexMag =0;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if ( sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x, y, z;
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            final double  normal=Math.sqrt(x * x + y * y + z * z);
            handlerClass.obtainMessage(Utility.Accelerometer, new DataPoint(indexAcce,normal)).sendToTarget();
            indexAcce++;


        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x, y, z;
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];
            final double  normal=Math.sqrt(x * x + y * y + z * z);
            handlerClass.obtainMessage(Utility.GyroSensor, new DataPoint(indexGyro,normal)).sendToTarget();
            indexGyro++;

        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            float t;
            t = sensorEvent.values[0];
            handlerClass.obtainMessage(Utility.Light, new DataPoint(indexLight,t)).sendToTarget();
            indexLight++;
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x, y, z;
            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];
            final double  normal=Math.sqrt(x * x + y * y + z * z);
            handlerClass.obtainMessage(Utility.Mag, new DataPoint(indexMag,normal)).sendToTarget();
            indexMag++;
        }
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
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    public void OnSecondButtonClick(View view){
        Intent intent=new Intent(SecondActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    class HandlerClass extends Handler {
        HandlerClass() {
        }

        public void handleMessage(Message msg) {
            DataPoint dataPoint=(DataPoint ) msg.obj;
            switch (msg.what) {
                case Utility.Accelerometer:
                    series_Acc.appendData(dataPoint,true,Utility.GraphSize);

                    return;
                case Utility.GyroSensor:
                    series_Gyro.appendData(dataPoint,true,Utility.GraphSize);

                    return;
                case Utility.Light:
                    series_Light.appendData(dataPoint,true,Utility.GraphSize);

                    return;
                case Utility.Mag:
                    series_Mag.appendData(dataPoint,true,Utility.GraphSize);

                    return;
                default:
                    return;
            }
        }
    }
}