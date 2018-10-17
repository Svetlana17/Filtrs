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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Fitriroscope extends AppCompatActivity implements  View.OnClickListener, SensorEventListener {

        String[] data = {"Ось ох", "Ось оу", "Ось oz"};
        Button mButton;

    Button button;
    private SensorManager mSensorManager;
    Sensor mSensorGiroscope;
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
    float ALPHA=0.05f;
    float alphaX;
    float alphaY;
    float alphaZ;
    private boolean graficflag = false;
    private boolean state;
    private int timer = 0;
    private double k=0.1;
    float fx;
    float fy;
    float fz;
//    Button mButton;

    //    Spinner spinner;
//    String[] acxios = {"ускорение  по х", "ускорение по y ", "ускорение по z "};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitriroscope);

        mButton=(Button) findViewById(R.id.button);
        mButton.setOnClickListener((View.OnClickListener) this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorGiroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        ////
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                graph = (GraphView) findViewById(R.id.graph);
                String selected = parent.getItemAtPosition(position).toString();
//                   if(position "0"){
//                       graph.removeSeries(seriesXX);
//                   }
            }
            //
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        //////








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

            seriesXX = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 0),

            });
            seriesXX.setColor(Color.YELLOW);

            seriesZZ = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 0),
            });
            seriesZZ.setColor(Color.LTGRAY);


            seriesYY = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 0),
            });
            seriesYY.setColor(Color.MAGENTA);

            graph.addSeries(seriesXX);
            graph.addSeries(seriesYY);
            graph.addSeries(seriesZZ);
            graph.addSeries(seriesX);
            graph.addSeries(series);
            graph.addSeries(seriesZ);

            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(20);
            feedMultiple();
        }

    protected float[] lowPassFilter( float[] input, float[] output ) {
        if ( output == null ) return input;

        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
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

           alphaX = (float) (1-k)*x;
            alphaY = (float) (1-k)*y;
            alphaZ = (float) (1-k)*z;








            series.appendData(new DataPoint(graph2LastYValue, y), true, 20);
            seriesX.appendData(new DataPoint(graph2LastXValue, x), true, 20);
            seriesZ.appendData(new DataPoint(graph2LastZValue, z), true, 20);
            seriesXX.appendData(new DataPoint(graph2LastXValue, alphaX), true, 20);
            seriesYY.appendData(new DataPoint(graph2LastYValue, alphaY), true, 20);
            seriesZZ.appendData(new DataPoint(graph2LastZValue, alphaZ), true, 20);
            graph.addSeries(series);
            graph.addSeries(seriesX);
            graph.addSeries(seriesZ);
            graph.addSeries(seriesXX);
            graph.addSeries(seriesYY);
            graph.addSeries(seriesZZ);

            if (!graficflag) {
                graph.removeSeries(seriesXX);
                graph.removeSeries(seriesYY);
                graph.removeSeries(seriesZZ);
            } else {
                graph.addSeries(seriesXX);
                graph.addSeries(seriesYY);
                graph.addSeries(seriesZZ);

            }
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
            mSensorManager.registerListener(this, mSensorGiroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }




    public void onClick(View v)
    {
        Intent intent=new Intent();
        intent.setClass(this, MainActivity.class);

        startActivity(intent);
        finish();
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
//                intent.setClass(this, RunActivity.class);

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


}