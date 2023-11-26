package com.example.alarmclock.ui.dashboard;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.alarmclock.AlarmHelper;
import com.example.alarmclock.AlarmReceiver;
import com.example.alarmclock.CustomAdapter;
import com.example.alarmclock.R;
import com.example.alarmclock.SharedPrefsHelper;
import com.example.alarmclock.databinding.FragmentDashboardBinding;
import com.example.alarmclock.Alarm;
import com.example.alarmclock.databinding.ListItemAlarmBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ListItemAlarmBinding bind;
    private List<Alarm> alarms;
    private ListView listViewAlarms;


    CustomAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        bind = ListItemAlarmBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        adapter = new CustomAdapter(getContext(), R.layout.list_item_alarm, alarms);
        listViewAlarms = binding.listViewAlarms;




        alarms = new ArrayList<>();
        updateAlarms(alarms);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void displayAlarms(List<Alarm> alarms) {
        adapter = new CustomAdapter(getContext(), R.layout.list_item_alarm, alarms);
        listViewAlarms.setAdapter(adapter);
    }

    private void updateAlarms(List<Alarm> updatedAlarms) {
        updatedAlarms = SharedPrefsHelper.getAlarms(getContext());

        if(updatedAlarms.size()!=0) displayAlarms(updatedAlarms);
        for(int i = 0 ; i < updatedAlarms.size();i++)
            if(updatedAlarms.get(i).isEnabled()){
                long snoozeInterval = 24 * 60 * 60 * 1000;

                long newAlarmTime = snoozeInterval;

                Intent alarmIntent = new Intent(getContext(), AlarmReceiver.class);
                AlarmHelper.setAlarm(getContext(),  newAlarmTime, alarmIntent);
            }
        SharedPrefsHelper.saveAlarms(getContext(),updatedAlarms);
        adapter.notifyDataSetChanged();
    }

}