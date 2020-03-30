package com.example.sensorcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ProximityActivity extends AppCompatActivity {

    private Context context;
    private TextView textViewPro;
    private ImageView imageViewPro;
    private SensorManager sensorManager;
    private Sensor sensor;
    private final String TAG2 = "pro";
    private MyProximityListener sensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        context = this;

        textViewPro = (TextView) findViewById(R.id.textView_proximity);
        textViewPro.setText("");

        imageViewPro = (ImageView) findViewById(R.id.imageView_proximity);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        sensorListener = new MyProximityListener(); // 自訂的 inner class

        sensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);





    }// onCreate()

    private class MyProximityListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {

            Log.d(TAG2, "sensor change"); // TAG2 = "pro"

            StringBuilder sb = new StringBuilder();

            sb.append("sensor:" + event.sensor.getName() + "\n");

            float proValue = event.values[0]; // 因為只有一個參數，所以取 index 0的地方

            sb.append("proximity value = " + proValue + "cm\n");

            textViewPro.setText(sb.toString());

            if(proValue < 1) {
                imageViewPro.setImageResource(R.drawable.p2); // 小於1 所以哭哭
                textViewPro.setText("Too close !!");
            }else {
                imageViewPro.setImageResource(R.drawable.p1);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }


    // activity 結束後要把 監聽關掉 不然會耗電
    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(sensorListener);
        super.onDestroy();
    }




}// class Proximity
