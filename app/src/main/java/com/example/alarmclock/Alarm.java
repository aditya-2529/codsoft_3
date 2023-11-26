package com.example.alarmclock;

import com.google.gson.Gson;

import java.io.Serializable;

public class Alarm implements Serializable {
    private int id;
    private String time;
    private boolean isEnabled;
    static Gson gson = new Gson();

    public Alarm(int id, String time, boolean isEnabled) {
        this.id = id;
        this.time = time;
        this.isEnabled = isEnabled;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
    public static String serialize(Alarm alarm) {
        return gson.toJson(alarm);
    }

    public static Alarm deserialize(String serializedAlarm) {
        return gson.fromJson(serializedAlarm, Alarm.class);
    }
}
