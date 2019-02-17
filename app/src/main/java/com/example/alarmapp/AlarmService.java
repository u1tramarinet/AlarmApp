package com.example.alarmapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {
    private static final String TAG = AlarmService.class.getSimpleName();
    public static final String KEY_ALARM_SECOND = "alarm_second";
    private int mAlarmSecond;

    public AlarmService() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate/in");
        super.onCreate();
        Log.d(TAG, "onCreate/out");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        if (intent != null) {
            mAlarmSecond = intent.getIntExtra(KEY_ALARM_SECOND, 60);
            long msecond = mAlarmSecond * 100;
            try {
                Thread.sleep(msecond);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent newIntent = new Intent(this, NotificationActivity.class);
            newIntent.putExtra(KEY_ALARM_SECOND, mAlarmSecond);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
            stopSelf();
        }
        return START_NOT_STICKY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        // NOP
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy/in");
        super.onDestroy();
        Log.d(TAG, "onDestroy/out");
    }
}
