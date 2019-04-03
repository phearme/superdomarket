package com.phearme.superdomarket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.phearme.superdoclient.SuperdoBag;
import com.phearme.superdogroceries.ISuperdoGroceriesEventsListener;
import com.phearme.superdogroceries.SuperdoGroceries;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SuperdoGroceries.getInstance(this).getGroceries(new ISuperdoGroceriesEventsListener() {
            @Override
            public void onGroceriesDataChanged(ArrayList<SuperdoBag> groceries) {
                for (SuperdoBag bag : groceries) {
                    Log.d("DEBUG", "grocery " + bag.name);
                }

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SuperdoGroceries.getInstance(MainActivity.this).stopGettingGroceries();
            }
        }, 10000);
    }
}
