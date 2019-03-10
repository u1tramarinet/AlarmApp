package com.example.alarmapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * アラーム完了通知のアクティビティ
 */
public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = NotificationActivity.class.getSimpleName();
    /**
     * アラームのアプリケーション
     */
    private AlarmApplication mApplication;
    /**
     * アラーム設定秒数
     */
    private int mCurrentSecond;
    /**
     * 画面の各ボタンに設定するリスナー
     */
    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick/in");
            mApplication.stopAlarm(NotificationActivity.this);

            switch (v.getId()) {
                case R.id.button_do_not_restart:    // 再通知しない
                    // NOP
                    break;
                case R.id.button_reset:             // 再設定
                    Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_restart:           // 再通知する
                    mApplication.startAlarm(NotificationActivity.this, mCurrentSecond);
                    break;
            }
            finish();
            Log.d(TAG, "onClick/out");
        }
    };

    /**
     * {@inheritDoc}
     */
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

    /**
     * コンポーネントの初期処理
     */
    private void initComponents() {
        Log.d(TAG, "initComponents/in");
        Button cancelButton = (Button) findViewById(R.id.button_do_not_restart);
        cancelButton.setOnClickListener(mButtonClickListener);

        Button resetButton = (Button) findViewById(R.id.button_reset);
        resetButton.setOnClickListener(mButtonClickListener);

        Button restartButton = (Button) findViewById(R.id.button_restart);
        restartButton.setOnClickListener(mButtonClickListener);
        Log.d(TAG, "initComponents/out");
    }
}
