package com.example.user.magnitometr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LowFiltrActivity extends AppCompatActivity
        //implements SensorEventListener
        {

    private static Context _context;
    PowerManager.WakeLock mWakeLock;

    public RelativeLayout upperLayerLayout;
   // static PaintUtils paintScreen;
   // static DataView dataView;
    boolean isInited = false;
    public static float azimuth;
    public static float pitch;
    public static float roll;
    public double latitudeprevious;
    public double longitude;
    String locationContext;
    String	provider;
    DisplayMetrics displayMetrics;
  //  Camera camera;
    public int screenWidth;
    public int screenHeight;

    private float RTmp[] = new float[9];
    private float Rot[] = new float[9];
    private float I[] = new float[9];
    private float grav[] = new float[3];
    private float mag[] = new float[3];
    private float results[] = new float[3];
    private SensorManager sensorMgr;
    private List<Sensor> sensors;
    private Sensor sensorGrav, sensorMag;

    static final float ALPHA = 0.25f;
    protected float[] gravSensorVals;
    protected float[] magSensorVals;

    {

//        @Override
//        protected void onCreate (Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_filtr);
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        upperLayerLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams upperLayerLayoutParams = new RelativeLayout.LayoutParams(android.widget.RelativeLayout.LayoutParams.FILL_PARENT, android.widget.RelativeLayout.LayoutParams.FILL_PARENT);
        upperLayerLayout.setLayoutParams(upperLayerLayoutParams);
        upperLayerLayout.setBackgroundColor(Color.TRANSPARENT);

        _context = this;

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        requestWindowFeature(Window.FEATURE_NO_TITLE);


        FrameLayout headerFrameLayout = new FrameLayout(this);
        RelativeLayout headerRelativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams relaLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        headerRelativeLayout.setBackgroundColor(Color.BLACK);
        headerRelativeLayout.setLayoutParams(relaLayoutParams);
        Button button = new Button(this);
        RelativeLayout.LayoutParams buttonparams = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        buttonparams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        button.setLayoutParams(buttonparams);
        button.setText("Cancel");
        button.setPadding(15, 0, 15, 0);

        TextView titleTextView = new TextView(this);
        RelativeLayout.LayoutParams textparams = new RelativeLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        textparams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        textparams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        titleTextView.setLayoutParams(textparams);
        titleTextView.setText("Augmented Reality View");


        headerRelativeLayout.addView(button);
        headerRelativeLayout.addView(titleTextView);
        headerFrameLayout.addView(headerRelativeLayout);
        //  setContentView(cameraView);
//        addContentView(radarMarkerView, new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
//        addContentView(headerFrameLayout, new FrameLayout.LayoutParams(
//                WindowManager.LayoutParams.FILL_PARENT, 44,
//                Gravity.TOP));
//        addContentView(upperLayerLayout, upperLayerLayoutParams);
//
//        if (!isInited) {
//            dataView = new DataView(ARView.this);
//            paintScreen = new PaintUtils();
//            isInited = true;
//        }

        upperLayerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(_context, "RELATIVE LAYOUT CLICKED", Toast.LENGTH_SHORT).show();
            }
        });


    }

//        public static Context getContext () {
//        return _context;
   }
//
//        public int convertToPix ( int val){
//        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, _context.getResources().getDisplayMetrics());
//        return (int) px;
//
//    }
//        @Override
//        protected void onDestroy () {
//        super.onDestroy();
//
//    }
//
//        @Override
//        protected void onPause () {
//        super.onPause();
//        this.mWakeLock.release();

//        sensorMgr.unregisterListener(this, sensorGrav);
//        sensorMgr.unregisterListener(this, sensorMag);
//        sensorMgr = null;
//    }
//
////        @Override
////        protected void onResume () {
//
//        super.onResume();
//        this.mWakeLock.acquire();
//
//
//        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
//
//        sensors = sensorMgr.getSensorList(Sensor.TYPE_ACCELEROMETER);
//        if (sensors.size() > 0) {
//            sensorGrav = sensors.get(0);
//        }
//
//        sensors = sensorMgr.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
//        if (sensors.size() > 0) {
//            sensorMag = sensors.get(0);
//        }
//
//        sensorMgr.registerListener(this, sensorGrav, SensorManager.SENSOR_DELAY_NORMAL);
//        sensorMgr.registerListener(this, sensorMag, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//
//
//    }
//        protected float[] lowPass( float[] input, float[] output ) {
//            if ( output == null ) return input;
//
//            for ( int i=0; i<input.length; i++ ) {
//                output[i] = output[i] + ALPHA * (input[i] - output[i]);
//            }
//            return output;
//        }
//
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }
//
//
//
//
//    }
//
////}
