package com.example.alarmapp.activity;

import android.content.res.Resources;
import android.support.v4.app.DialogFragment;
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

import com.example.alarmapp.AlarmApplication;
import com.example.alarmapp.NotificationSettingDialogFragment;
import com.example.alarmapp.R;
import com.example.alarmapp.SharedPreferencesUtil;
import com.example.alarmapp.observer.AlarmStateObserver;
import com.example.alarmapp.state.AlarmState;

/**
 * メインアクティビティ
 */
public class MainActivity extends AlarmActivity
        implements NotificationSettingDialogFragment.DialogActionListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * アラーム設定秒数
     */
    private int mCurrentSecond;
    /**
     * 通知時間を表示するテキストビュー
     */
    private TextView mTextViewNotificationTime;
    /**
     * アラームの状態を表示するテキストビュー
     */
    private TextView mTextViewState;
    /**
     * アラームのアプリケーション
     */
    private AlarmApplication mApplication;

    /**
     * 通知状態を管理しているApplicationから変更通知を受け取るためのオブザーバ
     */
    private AlarmStateObserver mObserver = new AlarmStateObserver() {
        @Override
        public void update(AlarmState state) {
            Log.d(TAG, "update/in");
            updateState(state.isRunning());
            Log.d(TAG, "update/out");
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate/in");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApplication = (AlarmApplication)getApplication();
        initComponents();
        updateSecond();
        Log.d(TAG, "onCreate/out");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStart() {
        Log.d(TAG, "onStart/in");
        super.onStart();
        mApplication.addObserver(mObserver);
        Log.d(TAG, "onStart/out");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStop() {
        Log.d(TAG, "onStop/in");
        super.onStop();
        mApplication.removeObserver(mObserver);
        Log.d(TAG, "onStop/out");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy/in");
        super.onDestroy();
        mApplication.requestStop((AlarmActivity) this);
        Log.d(TAG, "onDestroy/out");
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
     * ダイアログでOKを押した際の動作
     */
    @Override
    public void onDialogPositiveClick() {
        Log.d(TAG, "onDialogPositiveClick/in");
        updateSecond();
        Log.d(TAG, "onDialogPositiveClick/out");
    }

    /**
     * コンポーネントの初期処理
     */
    private void initComponents() {
        Log.d(TAG, "initialize/in");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextViewNotificationTime = (TextView) findViewById(R.id.textViewNotificationTimeValue);
        mTextViewState = (TextView) findViewById(R.id.textViewStateValue);
        Button startButton = (Button) findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onStartClick/in");
                mApplication.requestStart((AlarmActivity) MainActivity.this, mCurrentSecond);
                Log.d(TAG, "onStartClick/out");
            }
        });

        Button stopButton = (Button) findViewById(R.id.buttonStop);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onStopClick/in");
                mApplication.requestStop((AlarmActivity) MainActivity.this);
                Log.d(TAG, "onStopClick/out");
            }
        });
        Log.d(TAG, "initialize/out");
    }

    /**
     * アラーム設定秒数の設定ダイアログを表示する
     * ただし、実行中であった場合、その旨のトーストを表示しダイアログは表示しない
     */
    private void showSettingDialog() {
        Log.d(TAG, "showSettingDialog/in");
        if (mApplication.canOpenSetting()) {
            DialogFragment dialog = NotificationSettingDialogFragment.newInstance();
            dialog.show(getSupportFragmentManager(), NotificationSettingDialogFragment.class.getSimpleName());
        } else {
            Toast.makeText(this, R.string.warning_in_running, Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "showSettingDialog/out");
    }

    /**
     * 画面に表示されている秒数を更新する
     */
    private void updateSecond() {
        Log.d(TAG, "updateSecond/in");
        Resources res = getResources();
        int defaultSecond = res.getInteger(R.integer.second_default);
        mCurrentSecond = SharedPreferencesUtil.readInt(this, SharedPreferencesUtil.KEY_NOTIFICATION_TIME, defaultSecond);
        mTextViewNotificationTime.setText(getString(R.string.second, mCurrentSecond));
        Log.d(TAG, "updateSecond/out");
    }

    /**
     * 画面に表示されているアラーム設定秒数を更新する
     * @param isRunning アラームが実行中かどうか
     */
    private void updateState(boolean isRunning) {
        Log.d(TAG, "updateAndNotifyState/in isRunning=" + isRunning);
        int textId;
        if (isRunning) {
            textId = R.string.state_notification_executing;
        } else {
            textId = R.string.state_notification_suspended;
        }
        mTextViewState.setText(textId);
        Log.d(TAG, "updateAndNotifyState/out");
    }
}
