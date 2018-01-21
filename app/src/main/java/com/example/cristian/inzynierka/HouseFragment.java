package com.example.cristian.inzynierka;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Cristian on 2018-01-20.
 */

public class HouseFragment extends Fragment {

    private static final String Tag = "HouseFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.house_fragment, container, false);

        return view;
    }
}
