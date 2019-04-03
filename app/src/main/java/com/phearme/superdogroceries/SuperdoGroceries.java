package com.phearme.superdogroceries;

import android.content.Context;

import com.phearme.superdoclient.ISuperdoEventsListener;
import com.phearme.superdoclient.SuperdoBag;
import com.phearme.superdoclient.SuperdoClient;
import com.phearme.superdoclient.SuperdoClientState;
import com.phearme.superdomarket.R;

import java.util.ArrayList;

public class SuperdoGroceries {
    private static SuperdoGroceries mInstance;
    private SuperdoClient superdoClient;
    private ArrayList<SuperdoBag> groceries;
    private ISuperdoGroceriesEventsListener eventsListener;

    public synchronized static SuperdoGroceries getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SuperdoGroceries(context);
        }
        return mInstance;
    }

    private SuperdoGroceries(Context context) {
        groceries = new ArrayList<>();
        ISuperdoEventsListener clientEventsListener = new ISuperdoEventsListener() {
            @Override
            public void onConnectionChanged(SuperdoClientState newState) {
                // maybe notify consumers
            }

            @Override
            public void onItemReceived(SuperdoBag bag) {
                groceries.add(0, bag);
                if (eventsListener != null) {
                    eventsListener.onGroceriesDataChanged(groceries);
                }
            }
        };
        superdoClient = new SuperdoClient(context.getString(R.string.superdo_client_host), clientEventsListener);
    }

    // other constructor for injecting client
    public SuperdoGroceries(SuperdoClient superdoClient) {
        setSuperdoClient(superdoClient);
    }

    public void getGroceries(ISuperdoGroceriesEventsListener eventsListener) {
        this.eventsListener = eventsListener;
        if (superdoClient.getClientState() == SuperdoClientState.OPEN) {
            return;
        }
        superdoClient.Open();
    }

    public void stopGettingGroceries() {
        superdoClient.Close();
    }

    public void setSuperdoClient(SuperdoClient superdoClient) {
        this.superdoClient = superdoClient;
    }
}
