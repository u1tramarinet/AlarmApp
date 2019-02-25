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

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick/in");
            mApplication.stopAlarm(NotificationActivity.this);

            switch (v.getId()) {
                case R.id.button_resetting:
                    Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_redo:
                    mApplication.startAlarm(NotificationActivity.this, mCurrentSecond);
                    break;
            }
            finish();
            Log.d(TAG, "onClick/out");
        }
    };

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
        Button cancelButton = (Button) findViewById(R.id.button_cancel_redo);
        cancelButton.setOnClickListener(mButtonClickListener);

        Button resetButton = (Button) findViewById(R.id.button_resetting);
        resetButton.setOnClickListener(mButtonClickListener);

        Button restartButton = (Button) findViewById(R.id.button_redo);
        restartButton.setOnClickListener(mButtonClickListener);
        Log.d(TAG, "initComponents/out");
    }
}
