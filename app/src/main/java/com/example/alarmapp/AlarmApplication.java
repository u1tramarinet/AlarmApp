package com.example.alarmapp;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AlarmApplication extends Application {
    private static final String TAG = AlarmApplication.class.getSimpleName();
    private boolean mIsRunning = false;
    private List<AlarmStateObserver> observers = new ArrayList<>();

    public void addObserver(AlarmStateObserver observer) {
        Log.d(TAG, "addObserver/in");
        observers.add(observer);
        Log.d(TAG, "addObserver/out");
    }

    public void removeObserver(AlarmStateObserver observer) {
        Log.d(TAG, "removeObserver/in");
        observers.remove(observer);
        Log.d(TAG, "removeObserver/out");
    }

    public void notifyObservers() {
        Log.d(TAG, "notifyObservers/in");
        for (AlarmStateObserver observer : observers) {
            observer.update(this);
        }
        Log.d(TAG, "notifyObservers/out");
    }

    public boolean isRunning() {
        Log.d(TAG, "isRunning result=" + mIsRunning);
        return mIsRunning;
    }

    public void updateState(boolean isRunning) {
        Log.d(TAG, "updateState/in");
        mIsRunning = isRunning;
        notifyObservers();
        Log.d(TAG, "updateState/out");
    }
}
