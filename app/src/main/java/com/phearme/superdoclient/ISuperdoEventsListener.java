package com.phearme.superdoclient;

public interface ISuperdoEventsListener {
    void onConnectionChanged(SuperdoClientState newState);
    void onItemReceived(SuperdoBag bag);
}
