package com.example.cristian.inzynierka;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;

/**
 * Created by Cristian on 2018-01-20.
 */

public class HouseFragment extends Fragment {

    private static final String Tag = "HouseFragment";

    ImageButton fan;
    ImageButton alarm;
    ImageButton lights;
    Button temperature;
    ImageButton window;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.house_fragment, container, false);
        setupButtons(view);
        return view;
    }

    public void setupButtons(View v) {
        fan = (ImageButton) v.findViewById(R.id.airConditionImageButton);
        lights = (ImageButton) v.findViewById(R.id.LightsImageButton);
        alarm = (ImageButton) v.findViewById(R.id.houseAlarmImageButton);
        window = (ImageButton) v.findViewById(R.id.jalousieImageButton);
        temperature = (Button) v.findViewById(R.id.temperatureButton);

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
        window.setOnClickListener(new View.OnClickListener() {
            boolean isOnWindow = false;
            @Override
            public void onClick(View v) {
                if(isOnWindow) {
                    window.setImageResource(R.drawable.jalousie_down);
                    isOnWindow = false;

                } else {
                    window.setImageResource(R.drawable.jalousie_up);
                    isOnWindow = true;
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
        temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    public void showDialog()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.temperature_dialog_layout, null);
        mBuilder.setTitle("Change temperature: ");

        Button b2 = (Button) mView.findViewById(R.id.cancelTempButton);
        Button b1 = (Button) mView.findViewById(R.id.okTempButton);
        final NumberPicker np = (NumberPicker) mView.findViewById(R.id.setTemperatureNumberPicker);
        np.setMaxValue(30);
        np.setMinValue(0);
        np.setWrapSelectorWheel(true);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                temperature.setText(String.valueOf(np.getValue())+"°C");
                dialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // dismiss the dialog
            }
        });
        dialog.show();


    }
}
