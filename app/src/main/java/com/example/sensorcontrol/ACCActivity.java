package com.example.sensorcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ACCActivity extends AppCompatActivity {

    private Context context;
    private TextView textViewACC;
    private ImageView imageViewLeft, imageViewRight, imageViewTop, imageViewDown;
    private ImageButton imageButtonStop;
    private boolean stopFlag;
    private SensorManager sensorManager;
    private Sensor sensor;
    private MyACCListener accListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc);


        context = this;

        // 先將手機設定固定方向 (只能直放)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textViewACC = (TextView) findViewById(R.id.textView_acc);

        textViewACC.setText("");

        imageViewLeft = (ImageView) findViewById(R.id.imageView_sensorLeft);

        imageViewRight = (ImageView) findViewById(R.id.imageView_sensorRight);

        imageViewTop = (ImageView) findViewById(R.id.imageView_sensorTop);

        imageViewDown = (ImageView) findViewById(R.id.imageView_sensorDown);


        imageViewLeft.setVisibility(View.INVISIBLE);
        imageViewRight.setVisibility(View.INVISIBLE);
        imageViewTop.setVisibility(View.INVISIBLE);
        imageViewDown.setVisibility(View.INVISIBLE);

        imageButtonStop = (ImageButton) findViewById(R.id.imageButton_sensorStop);

        stopFlag = true; // 設一個旗標 for 按了 Button才會停止 stop

        imageButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(stopFlag) {

                    stopFlag = false;
                    imageButtonStop.setImageResource(R.drawable.cancel_1);
                    Log.d("acc", "stopFlag = " + stopFlag);

                }else {

                    stopFlag = true;
                    imageButtonStop.setImageResource(R.drawable.ok_1);
                    imageViewLeft.setVisibility((View.INVISIBLE));
                    imageViewRight.setVisibility((View.INVISIBLE));
                    imageViewTop.setVisibility((View.INVISIBLE));
                    imageViewDown.setVisibility((View.INVISIBLE));
                    Log.d("acc", "stopFlag = " + stopFlag);
                }
            }
        });


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        accListener = new MyACCListener();

        sensorManager.registerListener(accListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);



    }// onCreate()


    private class MyACCListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {

            StringBuilder sb = new StringBuilder();
            sb.append("sensor:" + event.sensor.getName() + "\n");
            textViewACC.setText(sb.toString());

            if(stopFlag == false) {

                float xValue = event.values[0];
                float yValue = event.values[1];
                float zValue = event.values[2];
                textViewACC.append("X value = " + xValue + "\n");
                textViewACC.append("Y value = " + yValue + "\n");
                textViewACC.append("Z value = " + zValue + "\n");

                // 動作判斷
                if(xValue < -4.0f) {
                    imageViewRight.setVisibility((View.VISIBLE));
                    imageViewLeft.setVisibility((View.INVISIBLE));
                    imageViewTop.setVisibility((View.INVISIBLE));
                    imageViewDown.setVisibility((View.INVISIBLE));

                }else if(xValue > 4.0f) {
                    imageViewLeft.setVisibility((View.VISIBLE));
                    imageViewRight.setVisibility((View.INVISIBLE));
                    imageViewTop.setVisibility((View.INVISIBLE));
                    imageViewDown.setVisibility((View.INVISIBLE));


                }else {

                    if(zValue > 7) {

                        imageViewTop.setVisibility((View.VISIBLE));
                        imageViewDown.setVisibility((View.INVISIBLE));
                        imageViewLeft.setVisibility((View.INVISIBLE));
                        imageViewRight.setVisibility((View.INVISIBLE));


                    }else if(zValue < 0) {

                        imageViewDown.setVisibility((View.VISIBLE));
                        imageViewTop.setVisibility((View.INVISIBLE));
                        imageViewLeft.setVisibility((View.INVISIBLE));
                        imageViewRight.setVisibility((View.INVISIBLE));

                    }else {

                        imageViewLeft.setVisibility((View.INVISIBLE));
                        imageViewRight.setVisibility((View.INVISIBLE));
                        imageViewTop.setVisibility((View.INVISIBLE));
                        imageViewDown.setVisibility((View.INVISIBLE));
                    }


                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    }// class MyACCListener

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(accListener);
    }


}// class ACC
