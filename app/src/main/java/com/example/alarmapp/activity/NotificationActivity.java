package com.example.alarmapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.alarmapp.AlarmApplication;
import com.example.alarmapp.AlarmService;
import com.example.alarmapp.R;

/**
 * アラーム完了通知のアクティビティ
 */
public class NotificationActivity extends AlarmActivity {
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
            mApplication.requestStop((AlarmActivity) NotificationActivity.this);

            switch (v.getId()) {
                case R.id.buttonDoNotRestart:    // 再通知しない
                    // NOP
                    break;
                case R.id.buttonReset:             // 再設定
                    Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.buttonRestart:           // 再通知する
                    mApplication.requestStart(NotificationActivity.this, mCurrentSecond);
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
        Button cancelButton = (Button) findViewById(R.id.buttonDoNotRestart);
        cancelButton.setOnClickListener(mButtonClickListener);

        Button resetButton = (Button) findViewById(R.id.buttonReset);
        resetButton.setOnClickListener(mButtonClickListener);

        Button restartButton = (Button) findViewById(R.id.buttonRestart);
        restartButton.setOnClickListener(mButtonClickListener);
        Log.d(TAG, "initComponents/out");
    }
}
