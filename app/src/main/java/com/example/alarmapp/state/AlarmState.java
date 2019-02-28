package com.example.alarmapp.state;

import android.content.Context;

/**
 *
 */
public interface AlarmState {
    /**
     *
     * @param context
     * @param sec
     */
    void start(Context context, int sec);

    /**
     *
     * @param context
     */
    void stop(Context context);

    /**
     *
     * @return
     */
    boolean canOpenSetting();

    /**
     *
     * @return
     */
    boolean isRunning();
}
