package com.example.user.magnitometr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MovenentActivity extends AppCompatActivity {
    private static final int REFRESH_ID = 1;
    private static final int GIROSCOPE = 2;
    private static final int MOVENENT=3;//ПЕРЕМЕЩЕНИЕ
    private static final String LOG_TAG = "my_tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movenent);
    }



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
        menu.add(0, MOVENENT,3,R.string.MOVENENT);
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
            case 3:
                // load data
//                Log.d(LOG_TAG, "action: load data");
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
                Intent intentss = new Intent();
                intentss.setClass(this, MovenentActivity.class);

                startActivity(intentss);
                finish();
        }
        return false;
    }
//
//

}
