package com.example.alarmapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.alarmapp.observer.AlarmStateObserver;
import com.example.alarmapp.state.AlarmState;
import com.example.alarmapp.state.ExecutingState;
import com.example.alarmapp.state.IdleState;

import java.util.ArrayList;
import java.util.List;

public class AlarmApplication extends Application {
    private static final String TAG = AlarmApplication.class.getSimpleName();
    private AlarmState mState = IdleState.getInstance();
    private List<AlarmStateObserver> observers = new ArrayList<>();

    /**
     *
     * @param context
     * @param sec
     */
    public void startAlarm(Context context, int sec) {
        Log.d(TAG, "startAlarm/in");
        mState.start(context, sec);
        updateState(ExecutingState.getInstance());
        Log.d(TAG, "startAlarm/out");
    }

    /**
     *
     * @param context
     */
    public void stopAlarm(Context context) {
        Log.d(TAG, "stopAlarm/in");
        mState.stop(context);
        updateState(IdleState.getInstance());
        Log.d(TAG, "stopAlarm/out");
    }

    /**
     *
     * @param state
     */
    public void updateState(AlarmState state) {
        Log.d(TAG, "updateState/in old=" + mState + "-> new=" + state);
        mState = state;
        notifyObservers();
        Log.d(TAG, "updateState/out");
    }

    /**
     *
     * @return
     */
    public boolean canOpenSetting() {
        Log.d(TAG, "canOpenSetting enabled=" + mState.canOpenSetting());
        return mState.canOpenSetting();
    }

    /**
     *
     */
    public void notifyObservers() {
        Log.d(TAG, "notifyObservers/in");
        for (AlarmStateObserver observer : observers) {
            observer.update(mState);
        }
        Log.d(TAG, "notifyObservers/out");
    }

    /**
     *
     * @param observer
     */
    public void addObserver(AlarmStateObserver observer) {
        Log.d(TAG, "addObserver/in");
        observers.add(observer);
        notifyObservers();
        Log.d(TAG, "addObserver/out");
    }

    /**
     *
     * @param observer
     */
    public void removeObserver(AlarmStateObserver observer) {
        Log.d(TAG, "removeObserver/in");
        observers.remove(observer);
        Log.d(TAG, "removeObserver/out");
    }
}
