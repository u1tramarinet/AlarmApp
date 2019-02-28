package com.example.alarmapp.observer;

import com.example.alarmapp.state.AlarmState;

/**
 *
 */
public interface AlarmStateObserver {
    /**
     *
     * @param state
     */
    void update(AlarmState state);
}
