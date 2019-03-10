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

/**
 * アラームのアプリケーション
 */
public class AlarmApplication extends Application {
    private static final String TAG = AlarmApplication.class.getSimpleName();
    /**
     * このアプリケーションにて、アラームの状態を保持する
     */
    private AlarmState mState = IdleState.getInstance();
    /**
     * オブザーバリスト
     */
    private List<AlarmStateObserver> observers = new ArrayList<>();

    /**
     * アラームを開始する
     * @param context AlarmStateに渡すコンテキスト
     * @param sec アラーム設定秒数
     */
    public void startAlarm(Context context, int sec) {
        Log.d(TAG, "startAlarm/in");
        mState.start(context, sec);
        updateAndNotifyState(ExecutingState.getInstance());
        Log.d(TAG, "startAlarm/out");
    }

    /**
     * アラームを中止する
     * @param context AlarmStateに渡すコンテキスト
     */
    public void stopAlarm(Context context) {
        Log.d(TAG, "stopAlarm/in");
        mState.stop(context);
        updateAndNotifyState(IdleState.getInstance());
        Log.d(TAG, "stopAlarm/out");
    }

    /**
     * 状態を更新する
     * @param state 更新後の状態
     */
    public void updateAndNotifyState(AlarmState state) {
        Log.d(TAG, "updateAndNotifyState/in old=" + mState + "-> new=" + state);
        mState = state;
        notifyObservers();
        Log.d(TAG, "updateAndNotifyState/out");
    }

    /**
     * アラーム秒数の設定の画面（ダイアログ）を表示できるかどうか
     * @return true:表示できる, false:表示できない
     */
    public boolean canOpenSetting() {
        Log.d(TAG, "canOpenSetting enabled=" + !mState.isRunning());
        return !mState.isRunning();
    }

    /**
     * 登録されたオブザーバにアラームの状態の更新を通知する
     */
    public void notifyObservers() {
        Log.d(TAG, "notifyObservers/in");
        for (AlarmStateObserver observer : observers) {
            observer.update(mState);
        }
        Log.d(TAG, "notifyObservers/out");
    }

    /**
     * オブザーバを追加する
     * @param observer 追加するオブザーバ
     */
    public void addObserver(AlarmStateObserver observer) {
        Log.d(TAG, "addObserver/in");
        observers.add(observer);
        notifyObservers();
        Log.d(TAG, "addObserver/out");
    }

    /**
     * オブザーバを削除する
     * @param observer 削除するオブザーバ
     */
    public void removeObserver(AlarmStateObserver observer) {
        Log.d(TAG, "removeObserver/in");
        observers.remove(observer);
        Log.d(TAG, "removeObserver/out");
    }
}
