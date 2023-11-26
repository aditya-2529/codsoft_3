package com.example.alarmclock;


import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class SharedPrefsHelper {

    private static final String PREFS_NAME = "AlarmPrefs";
    private static final String ALARM_LIST_KEY = "AlarmList";

    public static void saveAlarms(Context context, List<Alarm> alarms) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(alarms);
        editor.putString(ALARM_LIST_KEY, json);
        editor.apply();
    }

    public static List<Alarm> getAlarms(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(ALARM_LIST_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Alarm>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
