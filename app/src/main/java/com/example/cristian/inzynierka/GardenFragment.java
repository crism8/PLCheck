package com.example.cristian.inzynierka;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import Moka7.S7Client;
import Moka7.S7;


/**
 * Created by Cristian on 2018-01-20.
 */

public class GardenFragment extends Fragment {

    private static final String Tag = "GardenFragment";
    private ImageButton watering;
    private ImageButton fountain;
    private ImageButton gateway;
    private ImageButton lights;
    public S7Client client;
    private boolean isTurnOn;
    private  static  final  int dbGarden = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.garden_fragment, container, false);
        setupButtons(view);
        client = new S7Client();
        return view;
    }

    public void setupButtons(View v) {
        watering = (ImageButton) v.findViewById(R.id.wateringImageButton);
        gateway = (ImageButton) v.findViewById(R.id.gatewayImageButton);
        fountain = (ImageButton) v.findViewById(R.id.fountainImageButton);
        lights = (ImageButton) v.findViewById(R.id.fountainLightsImageButton);
        watering.setOnClickListener(new View.OnClickListener() {
            boolean isOnWatering = false;
            int dbPosWatering = 2;
            @Override
            public void onClick(View v) {
                if(isOnWatering) {
                    watering.setImageResource(R.drawable.hose);
                    isOnWatering = false;

                } else {
                    watering.setImageResource(R.drawable.hose_full);
                    isOnWatering = true;
                }
                isTurnOn = isOnWatering;
                new PLCData(dbGarden, dbPosWatering).execute("");
            }
        });
        gateway.setOnClickListener(new View.OnClickListener() {
            boolean isOnGateway = false;
            int dbPosGateway = 5;
            @Override
            public void onClick(View v) {
                if(isOnGateway) {
                    gateway.setImageResource(R.drawable.gateway);
                    isOnGateway = false;

                } else {
                    gateway.setImageResource(R.drawable.gateway_color);
                    isOnGateway = true;
                }
                isTurnOn = isOnGateway;
                new PLCData(dbGarden, dbPosGateway).execute("");
            }
        });
        fountain.setOnClickListener(new View.OnClickListener() {
            boolean isOnFountain = false;
            int dbPosFountain = 4;
            @Override
            public void onClick(View v) {
                if(isOnFountain) {
                    fountain.setImageResource(R.drawable.fountain);
                    isOnFountain = false;

                } else {
                    fountain.setImageResource(R.drawable.fountain_full);
                    isOnFountain = true;
                }
                isTurnOn = isOnFountain;
                new PLCData(dbGarden, dbPosFountain).execute("");
            }
        });
        lights.setOnClickListener(new View.OnClickListener() {
            boolean isOnLights = false;
            int dbPosLights = 3;
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
                new PLCData(dbGarden, dbPosLights).execute("");
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