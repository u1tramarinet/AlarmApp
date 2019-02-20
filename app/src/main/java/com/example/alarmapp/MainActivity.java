package com.example.alarmapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements NotificationSettingDialogFragment.DialogActionListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private int mCurrentSecond;
    private boolean mIsRunning = false;

    private TextView mTextViewSecond;
    private TextView mTextViewState;
    private Button mButtonStart;
    private Button mButtonStop;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate/in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        updateSecond();
        updateState(false);
        Log.d(TAG, "onCreate/out");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
            case R.id.notification_time_setting:
                Log.d(TAG, "Pressed time setting");
                showSettingDialog();
                return true;
            default:
                Log.d(TAG, "Pressed unknown option");
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     */
    @Override
    public void onDialogPositiveClick() {
        Log.d(TAG, "onDialogPositiveClick/in");
        updateSecond();
        Log.d(TAG, "onDialogPositiveClick/out");
    }

    /**
     *
     */
    private void initComponents() {
        Log.d(TAG, "initialize/in");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextViewSecond = (TextView) findViewById(R.id.text_second);
        mTextViewState = (TextView) findViewById(R.id.text_state);
        mButtonStart = (Button) findViewById(R.id.button_start);
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onStartClick/in");
                startAlarm();
                Log.d(TAG, "onStartClick/out");
            }
        });

        mButtonStop = (Button) findViewById(R.id.button_stop);
        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onStopClick/in");
                stopAlarm();
                Log.d(TAG, "onStopClick/out");
            }
        });
        Log.d(TAG, "initialize/out");
    }

    /**
     *
     */
    private void startAlarm() {
        Log.d(TAG, "startAlarm/in");
        Intent intent = new Intent(this, AlarmService.class);
        intent.putExtra(AlarmService.KEY_ALARM_SECOND, mCurrentSecond);
        startService(intent);
        updateState(true);
        Log.d(TAG, "startAlarm/out");
    }

    /**
     *
     */
    private void stopAlarm() {
        Log.d(TAG, "stopAlarm/in");
        Intent intent = new Intent(this, AlarmService.class);
        stopService(intent);
        updateState(false);
        Log.d(TAG, "stopAlarm/out");
    }

    /**
     *
     */
    private void showSettingDialog() {
        Log.d(TAG, "showSettingDialog/in");
        if (mIsRunning) {
            Toast.makeText(this, R.string.warning_in_running, Toast.LENGTH_SHORT).show();
            return;
        }
        DialogFragment dialog = NotificationSettingDialogFragment.newInstance();
        dialog.show(getSupportFragmentManager(), NotificationSettingDialogFragment.class.getSimpleName());
        Log.d(TAG, "showSettingDialog/out");
    }

    /**
     *
     */
    private void updateSecond() {
        Log.d(TAG, "updateSecond/in");
        Resources res = getResources();
        int defaultSecond = res.getInteger(R.integer.second_default);
        mCurrentSecond = SharedPreferencesUtil.readInt(this, SharedPreferencesUtil.KEY_NOTIFICATION_TIME, defaultSecond);
        mTextViewSecond.setText(getString(R.string.second, mCurrentSecond));
        Log.d(TAG, "updateSecond/out");
    }

    /**
     *
     * @param isRunning
     */
    private void updateState(boolean isRunning) {
        Log.d(TAG, "updateState/in isRunning=" + isRunning);
        mIsRunning = isRunning;

        int textId = R.string.state_notification_suspended;
        if (mIsRunning) {
            textId = R.string.state_notification_executing;
        }
        mTextViewState.setText(textId);

        mButtonStart.setEnabled(!mIsRunning);
        mButtonStop.setEnabled(mIsRunning);
        Log.d(TAG, "updateState/out");
    }
}
