package com.example.alarmclock;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PowerManager;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag")
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "POWERMANAGER");
        wl.acquire();
        playAlarm(context);
        Intent displayIntent = new Intent(context, AlarmDisplayActivity.class);
        displayIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(displayIntent);
        wl.release();
    }
    private void playAlarm(Context context) {
        try {
            Uri alarmUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.aagebhi);
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(context, alarmUri);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

