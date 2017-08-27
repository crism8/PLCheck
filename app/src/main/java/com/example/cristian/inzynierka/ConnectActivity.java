package com.example.cristian.inzynierka;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import Moka7.*;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }
    public void plcConnect (View view) {
        EditText ipAddress = (EditText) findViewById(R.id.addressIPEditText);
        EditText slot = (EditText) findViewById(R.id.slotEditText);
        EditText rack = (EditText) findViewById(R.id.rackEditText);

        String ipAd = ipAddress.getText().toString();
        String slotSting = slot.getText().toString();
        String rackString = rack.getText().toString();
    }
}
