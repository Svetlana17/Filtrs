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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static com.example.user.magnitometr.AveragingFilter.DEFAULT_TIME_CONSTANT;

public class MovenentActivity extends BaseFilter
  implements SensorEventListener, View.OnClickListener {


        private static final String tag = LowPassFilter.class.getSimpleName();


        private float[] output;


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
        float x;
        float yy;
        float zz;

        protected float timeConstant;
        private float altha = 0.05f;
        private boolean state;
        private int timer = 0;
        Button mbutton;

        protected long startTime;
        protected long timestamp;
        protected int count;

        public void onCreate(Bundle savedInstanceState) {

            //mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensorAccelerometr = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            graph = (GraphView) graph.findViewById(R.id.graph);
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

//}


    public MovenentActivity() {
            this(DEFAULT_TIME_CONSTANT);
        }

    public MovenentActivity(float timeConstant) {
            this.timeConstant = timeConstant;
            reset();
        }

    private void reset() {
        startTime = 0;
        timestamp = 0;
        count = 0;
    }

    /**
         * Add a sample.
         *
         * @param values
         *            The acceleration data. A 1x3 matrix containing the data from the X, Y and Z axis of the sensor
         *            noting that order is arbitrary.
         * @return Returns the output of the fusedOrientation.
         */
        public float[] filter(float[] values)
        {
            // Initialize the start time.
            if (startTime == 0)
            {
                startTime = System.nanoTime();
            }

            timestamp = System.nanoTime();

            // Find the sample period (between updates) and convert from
            // nanoseconds to seconds. Note that the sensor delivery rates can
            // individually vary by a relatively large time frame, so we use an
            // averaging technique with the number of sensor updates to
            // determine the delivery rate.
            float dt = 1 / (count++ / ((timestamp - startTime) / 1000000000.0f));

            float alpha = timeConstant / (timeConstant + dt);

            output[0] = alpha * output[0] + (1 - alpha) * values[0];
            output[1] = alpha * output[1] + (1 - alpha) * values[1];
            output[2] = alpha * output[2] + (1 - alpha) * values[2];

            return output;
        }

        //@Override
        public float[] getOutput() {
            return output;
        }

        public void setTimeConstant(float timeConstant)
        {
            this.timeConstant = timeConstant;
        }





    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(View v) {

    }
}
