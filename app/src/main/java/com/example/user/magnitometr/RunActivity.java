package com.example.user.magnitometr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
//Для фильтронных значений с акслерометра\
public class RunActivity extends AppCompatActivity
        implements View.OnClickListener,
 SensorEventListener {

    String[] data = {"Ось ох", "Ось оу", "Ось oz"};
    Button button;
    private SensorManager mSensorManager;

    Sensor sensorAccelerometr;
    GraphView graph;
    private double graph2LastXValue = 5d;
    private double graph2LastYValue = 5d;
    private double graph2LastZValue = 5d;
    private Double[] dataPoints;
    LineGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> seriesX;
    LineGraphSeries<DataPoint> seriesZ;
    LineGraphSeries<DataPoint> seriesXX;
    LineGraphSeries<DataPoint> seriesYY;
    LineGraphSeries<DataPoint> seriesZZ;
    private Thread thread;
    private boolean plotData = true;
    float xx;
    float yy;
    float zz;
    private boolean graficflag = false;
    private float On_1 = 1;
    private float altha = 0.1f;
    private boolean state;
    private int timer = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        /////////
          //        // адаптер
 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
    // заголовок
        spinner.setPrompt("Title");
    // выделяем элемент
        spinner.setSelection(2);
    // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

                                          {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view,
                                                                         int position, long id) {
                                                  // показываем позиция нажатого элемента
                                                  Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> arg0) {
                                              }
                                          });
        ////////

        button = findViewById(R.id.button);
        state = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometr = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),
        });
        series.setColor(Color.GREEN);
        graph.addSeries(series);
        seriesX = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),

        });
        seriesX.setColor(Color.BLACK);

        seriesZ = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),
        });
        seriesZ.setColor(Color.RED);
        seriesXX = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),

        });
        seriesXX.setColor(Color.YELLOW);
//
        seriesZZ = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),
        });
        seriesZZ.setColor(Color.LTGRAY);

//
        seriesYY = new LineGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),
        });
        seriesYY.setColor(Color.MAGENTA);

        graph.addSeries(seriesX);
        graph.addSeries(series);
        graph.addSeries(seriesZ);
        graph.addSeries(seriesXX);
        graph.addSeries(seriesYY);
        graph.addSeries(seriesZZ);
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(20);
        feedMultiple();

    }

    //}
    public void addEntry(SensorEvent event) {
        float[] values = event.values;
        float x = values[0];
        System.out.println(x);
        float y = values[1];
        System.out.println(y);
        float z = values[2];
        System.out.println(z);

        graph2LastXValue += 1d;
        graph2LastYValue += 1d;
        graph2LastZValue += 1d;
        xx = (float) (On_1 + altha * (x - On_1));
        yy = (float) (On_1 + altha * (y - On_1));
        zz = (float) (On_1 + altha * (z - On_1));

        series.appendData(new DataPoint(graph2LastYValue, y), true, 20);
        seriesX.appendData(new DataPoint(graph2LastXValue, x), true, 20);
        seriesZ.appendData(new DataPoint(graph2LastZValue, z), true, 20);
        seriesXX.appendData(new DataPoint(graph2LastXValue, xx), true, 20);
        seriesYY.appendData(new DataPoint(graph2LastYValue, yy), true, 20);
        seriesZZ.appendData(new DataPoint(graph2LastZValue, zz), true, 20);
        graph.addSeries(series);
        graph.addSeries(seriesX);
        graph.addSeries(seriesZ);
        graph.addSeries(seriesXX);
        graph.addSeries(seriesYY);
        graph.addSeries(seriesZZ);
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
                        Thread.sleep(500);
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
        mSensorManager.unregisterListener((SensorEventListener) this);

    }

    //    @Override
    public void onSensorChanged(final SensorEvent event) {
        if (plotData) {
//            addEntry(event);
            //
            new Thread(new Runnable() {

                @Override
                public void run() {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry(event);
                        }
                    });


                }

            }).start();

            //
            plotData = false;
        }
    }

    //    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener((SensorEventListener) this, sensorAccelerometr, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        mSensorManager.unregisterListener((SensorEventListener) RunActivity.this);
        thread.interrupt();
        super.onDestroy();
    }




//
 public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);

        startActivity(intent);
        finish();
    }


     }
//}