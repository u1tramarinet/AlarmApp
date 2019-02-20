package com.example.alarmapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = NotificationActivity.class.getSimpleName();
    private AlarmApplication mApplication;
    private int mCurrentSecond = 0;
    private Button mButtonCancel;
    private Button mButtonSetting;
    private Button mButtonRedo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate/in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mApplication = (AlarmApplication)getApplication();
        initComponents();
        Intent intent = getIntent();
        if (intent != null) {
            mCurrentSecond = intent.getIntExtra(AlarmService.KEY_ALARM_SECOND, 60);
        }

        Log.d(TAG, "onCreate/out");
    }

    private void initComponents() {
        Log.d(TAG, "initComponents/in");
        mButtonCancel = (Button) findViewById(R.id.button_cancel_redo);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onCancelClick/in");
                cancelNextAlarm();
                Log.d(TAG, "onCancelClick/out");
            }
        });

        mButtonSetting = (Button) findViewById(R.id.button_resetting);
        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onSettingClick/in");
                moveToTopActivity();
                Log.d(TAG, "onSettingClick/out");
            }
        });

        mButtonRedo = (Button) findViewById(R.id.button_redo);
        mButtonRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onRedoClick/in");
                startNextAlarm();
                Log.d(TAG, "onRedoClick/out");
            }
        });
        Log.d(TAG, "initComponents/out");
    }

    /**
     *
     */
    private void cancelNextAlarm() {
        Log.d(TAG, "cancelNextAlarm/in");
        mApplication.updateState(false);
        finish();
        Log.d(TAG, "cancelNextAlarm/out");
    }

    /**
     *
     */
    private void moveToTopActivity() {
        Log.d(TAG, "moveToTopActivity/in");
        mApplication.updateState(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        Log.d(TAG, "moveToTopActivity/out");
    }

    /**
     *
     */
    private void startNextAlarm() {
        Log.d(TAG, "startNextAlarm/in");
        Intent intent = new Intent(this, AlarmService.class);
        intent.putExtra(AlarmService.KEY_ALARM_SECOND, mCurrentSecond);
        startService(intent);
        finish();
        Log.d(TAG, "startNextAlarm/out");
    }
}
