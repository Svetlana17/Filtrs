package com.example.user.magnitometr;

import android.content.Intent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Fitriroscope extends AppCompatActivity implements  View.OnClickListener{
    Button mButton;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitriroscope);
        textView=(TextView)findViewById(R.id.filtr);
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