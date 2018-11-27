package com.example.user.magnitometr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

import static android.view.KeyCharacterMap.ALPHA;

public class MainActivity extends     AppCompatActivity
       implements SensorEventListener, View.OnClickListener
 {

//
//   // private static final String tag = LowPassFilter.class.getSimpleName();
//
//
//    private float[] output;
//
//        Button buttonS;
//        Button button;
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
        float x;
        float yy;
        float zz;
//
//      protected float timeConstant;
        private float altha = 0.5f;
        private boolean state;
        private int timer = 0;
        Button mbutton;

    protected long startTime;
    protected long timestamp;
    protected int count;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);



     //            mbutton=(Button)findViewById(R.id.button);
//            button = findViewById(R.id.button);
//            state = false;
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


           // output[i] = output[i] + ALPHA * (input[i] - output[i]);
           //

            xx =xx+altha*(x-xx);
            yy=yy+altha*(y-yy);
            zz=zz+altha*(z-zz);

           // series.appendData(new DataPoint(graph2LastYValue, y), true, 20);
//            seriesX.appendData(new DataPoint(graph2LastXValue, x), true, 20);
            seriesZ.appendData(new DataPoint(graph2LastZValue, z), true, 20);
//            seriesXX.appendData(new DataPoint(graph2LastXValue, xx), true, 20);
           // seriesYY.appendData(new DataPoint(graph2LastYValue, yy), true, 20);
           seriesZZ.appendData(new DataPoint(graph2LastZValue, zz), true, 20);
          //  graph.addSeries(seriesX);
           // graph.addSeries(series);
           graph.addSeries(seriesZ);
           // graph.addSeries(seriesXX);
           //graph.addSeries(seriesYY);
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
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                //accelVals = lowPass( event.values.clone(), accelVals );
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
            mSensorManager.unregisterListener((SensorEventListener) MainActivity.this);
            thread.interrupt();
            super.onDestroy();
        }

// @Override
//     public void onSensorChanged(SensorEvent event) {
//
//     }
//
//     @Override
//     public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//     }

//     @Override
//     public void onClick(View v) {
//
//     }
        public void onClick(View v) {
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }