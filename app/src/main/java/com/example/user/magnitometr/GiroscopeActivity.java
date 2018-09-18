package com.example.user.magnitometr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GiroscopeActivity extends AppCompatActivity implements  View.OnClickListener, SensorEventListener {
    private SensorManager mSensorManager;


    private static final String LOG_TAG = "my_tag";
    private static final int REFRESH_ID = 1;
    private static final int GIROSCOPE = 2;
    private static final int LOAD_DATA_ID = 2;

    Sensor sensorGiroscope;
    GraphView graph;
    private double graph2LastXValue = 5d;
    private double graph2LastYValue = 5d;
    private double graph2LastZValue = 5d;
    private Double[] dataPoints;
    LineGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> seriesX;
    LineGraphSeries<DataPoint> seriesZ;

    private Thread thread;
    private boolean plotData = true;

    private boolean graficflag = false;
    private double k=0.1;
    private boolean state;
    private int timer = 0;
    private float alphaX;
    private float alphaY;
    private float alphaZ;
    //    Spinner spinner;
//    String[] acxios = {"ускорение  по х", "ускорение по y ", "ускорение по z "};
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener((View.OnClickListener) this);
        state = false;
//        spinner = (Spinner)findViewById(R.id.spinner);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorGiroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, acxios);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(arrayAdapter);
//        ////
//      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//          @Override
//          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//              String item =  parent.getItemAtPosition(position).toString();
//              System.out.println(item);
//              switch (item){
//                  case "ускорение  по х":
//
//
////                      graficflag = true;
//                      Toast toast = Toast.makeText(getApplicationContext(),
//                              "Ваш выбор: " + item, Toast.LENGTH_LONG);
//                      toast.show();
//                      System.out.println(item + " " + id);
//                      break;
//                  case "ускорение по y ":
////                      graficflag = false;
//                      Toast toasts = Toast.makeText(getApplicationContext(),
//                              "Ваш выбор: " + item, Toast.LENGTH_LONG);
//                      toasts.show();
//                      System.out.println(item + " " + id);
//                      break;
//                  case "ускорение по z ":
////                      graficflag = true;
//                      Toast toast1 = Toast.makeText(getApplicationContext(),
//                              "Ваш выбор: " + item, Toast.LENGTH_LONG);
//                      toast1.show();
//                      System.out.println(item + " " + id);
//                      break;
//              }
//          }
//          @Override
//          public void onNothingSelected(AdapterView<?> parent) {
//
//          }
//      });


        System.out.println(sensorGiroscope);
        graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),
        });
        series.setColor(Color.GREEN);

        seriesX = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),

        });
        seriesX.setColor(Color.BLACK);

        seriesZ = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),
        });
        seriesZ.setColor(Color.RED);

        graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),
        });
        series.setColor(Color.BLUE);




        graph.addSeries(seriesX);
        graph.addSeries(series);
        graph.addSeries(seriesZ);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(20);
        feedMultiple();
    }

    public void addEntry(SensorEvent event) {
        /*     LineGraphSeries<DataPoint> series = new LineGraphSeries<>();*/
        float[] values = event.values;
        // Movement
        float x = values[0];
        System.out.println(x);
        float y = values[1];
        System.out.println(y);
        float z = values[2];
        System.out.println(z);

        if (state) {
            timer++;
            if (timer % 5 == 0) {
                System.out.println(timer);
                // saveText(event);
            }
        }


        graph2LastXValue += 1d;
        graph2LastYValue += 1d;
        graph2LastZValue += 1d;

        alphaX = (float) ((1-k)*x);
        alphaY = (float) ((1-k)*y);
        alphaZ = (float) ((1-k)*z);

        series.appendData(new DataPoint(graph2LastYValue, y), true, 20);
        seriesX.appendData(new DataPoint(graph2LastXValue, x), true, 20);
        seriesZ.appendData(new DataPoint(graph2LastZValue, z), true, 20);

        graph.addSeries(series);
        graph.addSeries(seriesX);
        graph.addSeries(seriesZ);

//        if (!graficflag) {
//            graph.removeSeries(seriesXX);
//            graph.removeSeries(seriesYY);
//            graph.removeSeries(seriesZZ);
//        } else {
//            graph.addSeries(seriesXX);
//            graph.addSeries(seriesYY);
//            graph.addSeries(seriesZZ);
//
//        }
        //*добавление фильтра
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
//                new DataPoint(x, y),
//        });
//        graph.addSeries(series);
    }

    private void addDataPoint(double acceleration) {
        dataPoints[499] = acceleration;
    }

    private void feedMultiple() {

        if (thread != null) {
            thread.interrupt();
        }

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    plotData = true;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (thread != null) {
            thread.interrupt();
        }
        mSensorManager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (plotData) {
            addEntry(event);
            plotData = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, sensorGiroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        mSensorManager.unregisterListener(GiroscopeActivity.this);
        thread.interrupt();
        super.onDestroy();
    }





//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.Giroscope:
//                Intent intent = new Intent();
//                intent.setClass(this, GiroscopeActivity.class);
//
//                startActivity(intent);
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
///////

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(LOG_TAG, "onPrepareOptionsMenu");
        menu.removeItem(R.id.acselerometr);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, REFRESH_ID, 1, R.string.acselerometr);
        menu.add(0, GIROSCOPE, 2, R.string.giroscope);

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.acselerometr:
//                return true;
            case 1:
//                // acselerometr
//                Log.d(LOG_TAG, "action: acselerometr");
//                return true;
                Intent intent = new Intent();
                intent.setClass(this, MainActivity.class);

                startActivity(intent);
                finish();
            case 2:
                // load data
//                Log.d(LOG_TAG, "action: load data");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
                Intent intents = new Intent();
                intents.setClass(this, GiroscopeActivity.class);

                startActivity(intents);
                finish();
        }
        return false;
    }
//

    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this, Fitriroscope.class);

        startActivity(intent);
        finish();
    }

}


