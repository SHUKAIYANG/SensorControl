package com.example.sensorcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private List<String> sensorName;
    private final String TAG = "main";
    private ListView listViewSensor;
    private ArrayAdapter<String> adapter;
    private Sensor sensor;
    private Intent intent;
    private Sensor sensor_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        listViewSensor = (ListView) findViewById(R.id.listview_id);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL); // 取得所有的 sensor list

        sensorName= new ArrayList<String>();


        //foreach 取得 sensor 的一些資訊
        for(Sensor sensorItem : sensorList) {

            String message = sensorItem.getType() + "-" + sensorItem.getName() + ":" + sensorItem.getPower() + "mA\n" + sensorItem.getVendor();
            Log.d(TAG, "sensor = " + message);

            //把資料放進 sensorName
            sensorName.add(message);
        }


        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, sensorName);

        listViewSensor.setAdapter(adapter);

        setTitle("Sensor number:" + sensorName.size());


        listViewSensor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Sensor item = sensorList.get(position);
                int sensorID = item.getType();
                Log.d(TAG, "sensorID = " + sensorID);

                switch (sensorID) {
                    case Sensor.TYPE_PROXIMITY:

                        // 避免沒有這個 TYPE 所以給一個 Toast 以免當掉
                        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

                        if(sensor == null){
                            Toast.makeText(context, "NO Proximity sensor", Toast.LENGTH_SHORT).show();
                        }else {
                            intent = new Intent(context, ProximityActivity.class);
                            startActivity(intent);
                        }

                        break;

                    case Sensor.TYPE_LIGHT:

                        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

                        if(sensor == null){
                            Toast.makeText(context, "NO light sensor", Toast.LENGTH_SHORT).show();
                        }else {
                            intent = new Intent(context, LightActivity.class);
                            startActivity(intent);
                        }

                        break;

                    case Sensor.TYPE_ACCELEROMETER:

                        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

                        if(sensor == null){
                            Toast.makeText(context, "NO ACC sensor", Toast.LENGTH_SHORT).show();
                        }else {
                            intent = new Intent(context, ACCActivity.class);
                            startActivity(intent);
                        }

                        break;

                }


            }
        });

    }// onCreate()


}// class main
