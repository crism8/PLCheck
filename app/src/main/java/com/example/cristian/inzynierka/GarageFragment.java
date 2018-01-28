package com.example.cristian.inzynierka;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import Moka7.S7;
import Moka7.S7Client;

/**
 * Created by Cristian on 2018-01-20.
 */

public class GarageFragment extends Fragment {

    private static final String Tag = "GarageFragment";
    private ImageButton fan;
    private ImageButton alarm;
    private ImageButton lights;
    private ImageButton garageDoor;
    public S7Client client;
    private boolean isTurnOn;
    private  static  final  int dbGarage = 3;



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
            int dbPosFan = 3;
            @Override
            public void onClick(View v) {
                if(isOnFan) {
                    fan.setImageResource(R.drawable.fan);
                    isOnFan = false;

                } else {
                    fan.setImageResource(R.drawable.fan_color);
                    isOnFan = true;
                }
                isTurnOn = isOnFan;
                new PLCData(dbGarage, dbPosFan).execute("");
            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            boolean isOnAlarm = false;
            int dbPosAlarm = 5;
            @Override
            public void onClick(View v) {
                if(isOnAlarm) {
                    alarm.setImageResource(R.drawable.alarm_off);
                    isOnAlarm = false;

                } else {
                    alarm.setImageResource(R.drawable.alarm_on);
                    isOnAlarm = true;
                }
                isTurnOn = isOnAlarm;
                new PLCData(dbGarage, dbPosAlarm).execute("");
            }
        });
        garageDoor.setOnClickListener(new View.OnClickListener() {
            boolean isOnGarage = false;
            int dbPosGarageDoor = 4;
            @Override
            public void onClick(View v) {
                if(isOnGarage) {
                    garageDoor.setImageResource(R.drawable.garage_down);
                    isOnGarage = false;

                } else {
                    garageDoor.setImageResource(R.drawable.garage_up);
                    isOnGarage = true;
                }
                isTurnOn = isOnGarage;
                new PLCData(dbGarage, dbPosGarageDoor).execute("");
            }
        });
        lights.setOnClickListener(new View.OnClickListener() {
            boolean isOnLights = false;
            int dbPosLights = 2;
            @Override
            public void onClick(View v) {
                if(isOnLights) {
                    lights.setImageResource(R.drawable.light_bulb);
                    isOnLights = false;

                } else {
                    lights.setImageResource(R.drawable.light_bulb_color);
                    isOnLights = true;
                }
                isTurnOn = isOnLights;
                new PLCData(dbGarage, dbPosLights).execute("");
            }
        });
    }
    private class PLCData extends AsyncTask<String, Void, String> {
        String ret = "";
        int dataBlockNumber;
        int dataBlockPos;
        public PLCData(int i, int j) {
            dataBlockNumber = i;
            dataBlockPos = j;
        }
        boolean isConnected = false;
        @Override
        protected String doInBackground(String... params) {
            try{
                client.SetConnectionType(S7.S7_BASIC);
                String ip = "192.168.1.100";
                int rack = 0;
                int slot = 1;
                int res = client.ConnectTo(ip, rack, slot);
                if (res==0){ //connection is ok
                    byte[] readData = new byte[1];
                    byte[] writeData = new byte[1];
                    S7.SetBitAt(writeData,0, 1, isTurnOn);
                    res = client.ReadArea(S7.S7AreaDB, dataBlockNumber,dataBlockPos,1,readData);
                    res = client.WriteArea(S7.S7AreaDB, dataBlockNumber, dataBlockPos, 1, writeData);
                    isTurnOn = S7.GetBitAt(readData,0,1);
                    ret = "value of Bool DB: :"+S7.GetBitAt(readData,0,1);
                    isConnected = true;
                    //ret = "Connection established.";
                }else{
                    isConnected = false;
                    ret= "ERR: "+ S7Client.ErrorText(res);
                }
                client.Disconnect();
            }catch (Exception e) {
                ret = "EXC: " +e.toString();
                Thread.interrupted();
            }
            return "executed";
        }
    }
}
