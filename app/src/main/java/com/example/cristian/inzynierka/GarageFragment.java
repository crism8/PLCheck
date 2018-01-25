package com.example.cristian.inzynierka;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Cristian on 2018-01-20.
 */

public class GarageFragment extends Fragment {

    private static final String Tag = "GarageFragment";
    ImageButton fan;
    ImageButton alarm;
    ImageButton lights;
    ImageButton garageDoor;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.garage_fragment, container, false);
        setupButtons(view);
        return view;
    }

    public void setupButtons(View v) {
        fan = (ImageButton) v.findViewById(R.id.fanGarageImageButton);
        lights = (ImageButton) v.findViewById(R.id.garageLightsImageButton);
        alarm = (ImageButton) v.findViewById(R.id.garageAlarmImageButton);
        garageDoor = (ImageButton) v.findViewById(R.id.garageDoorImageButton);
        fan.setOnClickListener(new View.OnClickListener() {
            boolean isOnFan = false;
            @Override
            public void onClick(View v) {
                if(isOnFan) {
                    fan.setImageResource(R.drawable.fan);
                    isOnFan = false;

                } else {
                    fan.setImageResource(R.drawable.fan_color);
                    isOnFan = true;
                }
            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            boolean isOnAlarm = false;
            @Override
            public void onClick(View v) {
                if(isOnAlarm) {
                    alarm.setImageResource(R.drawable.alarm_off);
                    isOnAlarm = false;

                } else {
                    alarm.setImageResource(R.drawable.alarm_on);
                    isOnAlarm = true;
                }
            }
        });
        garageDoor.setOnClickListener(new View.OnClickListener() {
            boolean isOnGarage = false;
            @Override
            public void onClick(View v) {
                if(isOnGarage) {
                    garageDoor.setImageResource(R.drawable.garage_down);
                    isOnGarage = false;

                } else {
                    garageDoor.setImageResource(R.drawable.garage_up);
                    isOnGarage = true;
                }
            }
        });
        lights.setOnClickListener(new View.OnClickListener() {
            boolean isOnLights = false;
            @Override
            public void onClick(View v) {
                if(isOnLights) {
                    lights.setImageResource(R.drawable.light_bulb);
                    isOnLights = false;

                } else {
                    lights.setImageResource(R.drawable.light_bulb_color);
                    isOnLights = true;
                }
            }
        });
    }
}
