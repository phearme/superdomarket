package com.phearme.superdoclient;

import android.util.Log;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class SuperdoClient {
    private static final String logTag = "SDOCLIENT";
    private static final int WS_NORMAL_CLOSE_CODE = 1000; // https://tools.ietf.org/html/rfc6455#section-7.4

    private String wsUrl;
    private WebSocket wsClient;
    private ISuperdoEventsListener eventsListener;
    private SuperdoClientState clientState = SuperdoClientState.CLOSED;

    public SuperdoClient(String host, ISuperdoEventsListener eventsListener) {
        this.wsUrl = String.format("ws://%s/receive", host);
        this.eventsListener = eventsListener;
    }

    public void Open() {
        // client already connected
        if (clientState == SuperdoClientState.OPEN) {
            return;
        }

        // eventually abandon previous client connection
        Close();


        Request request = new Request.Builder().url(wsUrl).build();
        wsClient = new OkHttpClient().newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                changeClientState(SuperdoClientState.OPEN);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                if (text != null) {
                    SuperdoBag bag = new Gson().fromJson(text, SuperdoBag.class);
                    if (bag != null && eventsListener != null) {
                        eventsListener.onItemReceived(bag);
                    }
                }
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                changeClientState(SuperdoClientState.CLOSED);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                Log.wtf(logTag, String.format("something went wrong: %s", t.getMessage()));
                Close();
            }
        });
    }

    public void Close() {
        if (wsClient != null) {
            wsClient.close(WS_NORMAL_CLOSE_CODE, "requested by client");
            wsClient.cancel();
        }
        changeClientState(SuperdoClientState.CLOSED);
    }

    private void changeClientState(SuperdoClientState newState) {
        clientState = newState;
        if (eventsListener != null) {
            eventsListener.onConnectionChanged(clientState);
        }
    }

    public SuperdoClientState getClientState() {
        return clientState;
    }
}
