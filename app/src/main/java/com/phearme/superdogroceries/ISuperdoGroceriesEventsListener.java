package com.phearme.superdogroceries;

import com.phearme.superdoclient.SuperdoBag;

import java.util.ArrayList;

public interface ISuperdoGroceriesEventsListener {
    void onGroceriesDataChanged(ArrayList<SuperdoBag> groceries);
}
