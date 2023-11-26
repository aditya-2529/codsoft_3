package com.example.alarmclock.ui.home;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.alarmclock.Alarm;
import com.example.alarmclock.AlarmHelper;
import com.example.alarmclock.AlarmReceiver;
import com.example.alarmclock.R;
import com.example.alarmclock.SharedPrefsHelper;
import com.example.alarmclock.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    Handler handler = new Handler(Looper.getMainLooper());
    Long setTime = 0l;
    private List<Alarm> alarms;
    private final int delayMillis = 1000;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        updateDateTime();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    updateDateTime();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                handler.postDelayed(this, delayMillis);
            }
        }, delayMillis);

        binding.btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                // Handle the selected time
                                String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                                setTime = (long) getAlarmTimeInMillis(selectedHour,selectedMinute);
                                Intent alarmIntent = new Intent(getContext(), AlarmReceiver.class);
                                AlarmHelper.setAlarm(getContext(),  setTime, alarmIntent);
                                alarms = new ArrayList<>();
                                alarms.add(new Alarm(1,time,true));
                                SharedPrefsHelper.saveAlarms(getContext(), alarms);
                            }
                        },
                        hour, // Initial hour
                        minute, // Initial minute
                        false // 24-hour format
                );

                timePickerDialog.show();

            }
        });
        return root;
    }

    private long getAlarmTimeInMillis(int hour, int minute) {
        Calendar currentTime = Calendar.getInstance();
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, hour);
        alarmTime.set(Calendar.MINUTE, minute);
        alarmTime.set(Calendar.SECOND, 0);

        if (alarmTime.before(currentTime)) {
            alarmTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        return alarmTime.getTimeInMillis();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void updateDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        String date = dateFormat.format(calendar.getTime());

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String time = timeFormat.format(calendar.getTime());

        binding.textDateTime.setText(getString(R.string.date_time_format, date, time));
    }
}