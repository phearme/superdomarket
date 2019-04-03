package com.phearme.superdomarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.phearme.superdoclient.SuperdoBag;
import com.phearme.superdogroceries.ISuperdoGroceriesEventsListener;
import com.phearme.superdogroceries.SuperdoGroceries;
import com.phearme.superdomarket.adapters.GroceriesAdapter;
import com.phearme.superdomarket.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GroceriesAdapter groceriesAdapter;
    private ISuperdoGroceriesEventsListener groceriesEventsListener;
    private MainViewModel.IMainViewModelEvents viewModelEvents;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new MainViewModel();
        viewModelEvents = new MainViewModel.IMainViewModelEvents() {
            @Override
            public void onStartStopButtonClick(boolean fetchingGroceries) {
                if (fetchingGroceries) {
                    SuperdoGroceries.getInstance(MainActivity.this).getGroceries(groceriesEventsListener);
                } else {
                    SuperdoGroceries.getInstance(MainActivity.this).stopGettingGroceries();
                }
            }
        };
        viewModel.setListener(viewModelEvents);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewModel);

        // bind recycler view to adapter
        groceriesEventsListener = new ISuperdoGroceriesEventsListener() {
            @Override
            public void onGroceriesDataChanged(ArrayList<SuperdoBag> groceries) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("DEBUG", "tick");
                        groceriesAdapter.setGroceries(groceries);
                        groceriesAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        groceriesAdapter = new GroceriesAdapter(new ArrayList<>());
        RecyclerView rvGroceries = findViewById(R.id.rvGroceries);
        rvGroceries.setLayoutManager(new LinearLayoutManager(this));
        rvGroceries.setAdapter(groceriesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setFetchingGroceries(true);
        SuperdoGroceries.getInstance(this).getGroceries(groceriesEventsListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setFetchingGroceries(false);
        SuperdoGroceries.getInstance(this).stopGettingGroceries();
    }


}
