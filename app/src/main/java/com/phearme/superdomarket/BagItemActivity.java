package com.phearme.superdomarket;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class BagItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag_item);

        String color = getIntent().getStringExtra("bagColor");
        if (color != null) {
            findViewById(R.id.imgBackgroundBagView).setBackgroundColor(Color.parseColor(color));
        }
    }
}
