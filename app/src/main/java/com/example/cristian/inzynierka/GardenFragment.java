package com.example.cristian.inzynierka;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Cristian on 2018-01-20.
 */

public class GardenFragment extends Fragment {

    private static final String Tag = "GardenFragment";
    ImageButton watering;
    ImageButton fountain;
    ImageButton gateway;
    ImageButton lights;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.garden_fragment, container, false);
        setupButtons(view);
        return view;
    }

    public void setupButtons(View v) {
        watering = (ImageButton) v.findViewById(R.id.wateringImageButton);
        gateway = (ImageButton) v.findViewById(R.id.gatewayImageButton);
        fountain = (ImageButton) v.findViewById(R.id.fountainImageButton);
        lights = (ImageButton) v.findViewById(R.id.fountainLightsImageButton);
        watering.setOnClickListener(new View.OnClickListener() {
            boolean isOnWatering = false;
            @Override
            public void onClick(View v) {
                if(isOnWatering) {
                    watering.setImageResource(R.drawable.hose);
                    isOnWatering = false;

                } else {
                    watering.setImageResource(R.drawable.hose_full);
                    isOnWatering = true;
                }
            }
        });
        gateway.setOnClickListener(new View.OnClickListener() {
            boolean isOnGateway = false;
            @Override
            public void onClick(View v) {
                if(isOnGateway) {
                    gateway.setImageResource(R.drawable.gateway);
                    isOnGateway = false;

                } else {
                    gateway.setImageResource(R.drawable.gateway_color);
                    isOnGateway = true;
                }
            }
        });
        fountain.setOnClickListener(new View.OnClickListener() {
            boolean isOnFountain = false;
            @Override
            public void onClick(View v) {
                if(isOnFountain) {
                    fountain.setImageResource(R.drawable.fountain);
                    isOnFountain = false;

                } else {
                    fountain.setImageResource(R.drawable.fountain_full);
                    isOnFountain = true;
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