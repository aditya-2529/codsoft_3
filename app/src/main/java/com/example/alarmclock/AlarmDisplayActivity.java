package com.example.alarmclock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class AlarmDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_display);
        SlidingUpPanelLayout slidingUpPanelLayout = findViewById(R.id.sliding_layout);
        Button btnSnooze = findViewById(R.id.btnSnooze);
        Button btnCancel = findViewById(R.id.btnCancel);


        btnSnooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snoozeAlarm();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                AlarmHelper.cancelAlarm(getApplicationContext(),alarmIntent);

                finish();
            }
        });
    }

    private void snoozeAlarm() {

        long snoozeInterval = 10 * 60 * 1000;

        long newAlarmTime = SystemClock.elapsedRealtime() + snoozeInterval;

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, newAlarmTime, pendingIntent);
    }
}