package com.example.cristian.inzynierka;

import android.app.Dialog;
import android.os.AsyncTask;
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
import Moka7.S7;
import Moka7.S7Client;

/**
 * Created by Cristian on 2018-01-20.
 */

public class HouseFragment extends Fragment {

    private static final String Tag = "HouseFragment";
    public S7Client client;
    private ImageButton fan;
    private ImageButton alarm;
    private ImageButton lights;
    private Button temperature;
    private ImageButton window;
    private boolean isTurnOn;
    private boolean isTemp;
    private static final int dbHouse= 1;
    private int temp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.house_fragment, container, false);
        client = new S7Client();
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
                new PLCData(dbHouse, dbPosFan).execute("");
            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            boolean isOnAlarm = false;
            int dbPosAlarm = 4;
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
                new PLCData(dbHouse, dbPosAlarm).execute("");
            }
        });
        window.setOnClickListener(new View.OnClickListener() {
            boolean isOnWindow = false;
            int dbPosWindow = 5;
            @Override
            public void onClick(View v) {
                if(isOnWindow) {
                    window.setImageResource(R.drawable.jalousie_down);
                    isOnWindow = false;

                } else {
                    window.setImageResource(R.drawable.jalousie_up);
                    isOnWindow = true;
                }
                isTurnOn = isOnWindow;
                new PLCData(dbHouse, dbPosWindow).execute("");
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
                new PLCData(dbHouse, dbPosLights).execute("");
            }
        });
        temperature.setOnClickListener(new View.OnClickListener() {
            int dbPosTemperature = 6;
            @Override
            public void onClick(View v) {
                showDialog();
                isTemp = true;
                new PLCData(dbHouse, dbPosTemperature).execute("");
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
        np.setMaxValue(35);
        np.setMinValue(0);
        np.setWrapSelectorWheel(true);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                temperature.setText(String.valueOf(np.getValue())+"°C");
                temp = np.getValue();
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
                    byte[] writeDataTemp = new byte[16];
                    S7.SetBitAt(writeData,0, 1, isTurnOn);
                    res = client.ReadArea(S7.S7AreaDB, dataBlockNumber,dataBlockPos,1,readData);
                    res = client.WriteArea(S7.S7AreaDB, dataBlockNumber, dataBlockPos, 1, writeData);
                    if (isTemp) {
                        S7.SetDIntAt(writeDataTemp, 0, temp);
                        res = client.WriteArea(S7.S7AreaDB, dataBlockNumber, dataBlockPos, 1, writeDataTemp);
                        isTemp = false;
                    }
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
