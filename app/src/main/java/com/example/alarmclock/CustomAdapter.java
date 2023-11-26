package com.example.alarmclock;

// CustomAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Alarm> {

    public CustomAdapter(Context context, int resource, List<Alarm> alarms) {
        super(context, resource, alarms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_alarm, parent, false);
        }

        Alarm currentAlarm = getItem(position);

        TextView timeTextView = listItemView.findViewById(R.id.textViewTime);
        timeTextView.setText(currentAlarm.getTime());

        Switch switchAlarm = listItemView.findViewById(R.id.switchAlarm);
        switchAlarm.setChecked(currentAlarm.isEnabled());


        return listItemView;
    }
}

