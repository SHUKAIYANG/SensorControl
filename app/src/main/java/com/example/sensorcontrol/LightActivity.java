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
import android.widget.Toast;

public class LightActivity extends AppCompatActivity {

    private Context context;
    private TextView textViewLight;
    private ImageView imageViewLight;
    private SensorManager sensorManager;
    private Sensor sensor;
    private MyLightListener listenerLight;
    private TextView textViewTemp, textViewHumi;
    private Sensor sensor_temp;
    private SensorManager sensorManager_temp;
    private MyTempListener listenerTemp;
    private Sensor sensor_Humi;
    private MyHumiListener listenerHumi;
    private SensorManager sensorManager_Humi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        context = this;

        textViewLight = (TextView) findViewById(R.id.textView_light);
        textViewLight.setText("");

        imageViewLight = (ImageView) findViewById(R.id.imageView_light);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        listenerLight = new MyLightListener(); // 自訂 inner class

        sensorManager.registerListener(listenerLight, sensor, SensorManager.SENSOR_DELAY_NORMAL);


        // 老師說自我練習在 lightActivity 練習新增 溫度與濕度 的 TextView

        //  Temperature **********************************************************
        textViewTemp = (TextView) findViewById(R.id.textView_temp);
        textViewTemp.setText("");

        sensor_temp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        listenerTemp = new MyTempListener();

        sensorManager_temp = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorManager_temp.registerListener(listenerTemp, sensor_temp, SensorManager.SENSOR_DELAY_NORMAL);
       //  **********************************************************

        //  humi **********************************************************
        textViewHumi = (TextView) findViewById(R.id.textView_humi);
        textViewHumi.setText("");

        sensor_Humi = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        listenerHumi = new MyHumiListener();

        sensorManager_Humi = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorManager_Humi.registerListener(listenerHumi, sensor_Humi, SensorManager.SENSOR_DELAY_NORMAL);
        //  **********************************************************

        if(sensor_temp == null || sensorManager_Humi == null) {
            Toast.makeText(context, "There is no temperature sensor or humiditiy sensor.", Toast.LENGTH_SHORT).show();
        }

    }// onCreate()

    //  Light 監聽 **********************************************************
    private class MyLightListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {

            Log.d("light", "light sensor change");

            StringBuilder sb = new StringBuilder();

            sb.append("sensor:" +  event.sensor.getName() + "\n");

            float lightValue = event.values[0];

            sb.append("Light value = " + lightValue + "\n");

            textViewLight.setText(sb.toString());

            if(lightValue < 40) {
                imageViewLight.setImageResource(R.drawable.dark_light);
                textViewLight.setText("Too dark!");
            }else {
                imageViewLight.setImageResource(R.drawable.imageslight);

            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
    //  **********************************************************

    //  Temperature 監聽 *********************************************************
    private class MyTempListener implements SensorEventListener{
        @Override
        public void onSensorChanged(SensorEvent event) {

            // temp
            Log.d("temp", "temp sensor change");

            StringBuilder sb_temp = new StringBuilder();

            sb_temp.append("sensor:" + event.sensor.getName() + "\n");

            float tempValue = event.values[0];

            sb_temp.append("Temperature value = " + tempValue);

            textViewTemp.setText(sb_temp.toString() + (char) 0x00B0 +"C");

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
    //  **********************************************************

    //  humi 監聽 **********************************************************
    private class MyHumiListener implements SensorEventListener{
        @Override
        public void onSensorChanged(SensorEvent event) {

            Log.d("humi", "humidity sensor change");

            StringBuilder sb_humi = new StringBuilder();

            sb_humi.append("sensor:" + event.sensor.getName() + "\n");

            float humiValue = event.values[0];

            sb_humi.append("Relative Humidity value = " + humiValue);

            textViewHumi.setText(sb_humi.toString() + "%");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
    //  **********************************************************

    // activity 結束後要把 監聽關掉 不然會耗電
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(listenerLight);
    }

}// class light
