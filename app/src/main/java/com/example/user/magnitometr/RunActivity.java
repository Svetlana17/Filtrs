package com.example.user.magnitometr;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class RunActivity extends AppCompatActivity implements View.OnClickListener{
    private SensorManager mSensorManager;
    Sensor sensorAccelerometr;
    Button mButton;
    Spinner spinner;
    String[] acxios = {"ускорение  по х", "ускорение по y ", "ускорение по z "};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        spinner = (Spinner)findViewById(R.id.spinner);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometr = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, acxios);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        ////
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item =  parent.getItemAtPosition(position).toString();
                System.out.println(item);
                switch (item){
                    case "ускорение  по х":


//                      graficflag = true;
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Ваш выбор: " + item, Toast.LENGTH_LONG);
                        toast.show();
                        System.out.println(item + " " + id);
                        break;
                    case "ускорение по y ":
//                      graficflag = false;
                        Toast toasts = Toast.makeText(getApplicationContext(),
                                "Ваш выбор: " + item, Toast.LENGTH_LONG);
                        toasts.show();
                        System.out.println(item + " " + id);
                        break;
                    case "ускорение по z ":
//                      graficflag = true;
                        Toast toast1 = Toast.makeText(getApplicationContext(),
                                "Ваш выбор: " + item, Toast.LENGTH_LONG);
                        toast1.show();
                        System.out.println(item + " " + id);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        mButton=(Button) findViewById(R.id.button);
        mButton.setOnClickListener((View.OnClickListener) this);
    }



    public void onClick(View v)
    {
        Intent intent=new Intent();
        intent.setClass(this, MainActivity.class);

        startActivity(intent);
        finish();
    }
}
