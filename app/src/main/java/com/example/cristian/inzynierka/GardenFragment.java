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
        watering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // zla metoda watering.setBackgroundResource(R.drawable.hose_full);
            }
        });
    }
}