package com.example.cristian.inzynierka;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import Moka7.*;

public class ConnectActivity extends AppCompatActivity {
    public String ipAd;
    public int slotInt;
    public int rackInt;
    public S7Client client = new S7Client();
    public EditText ipAddress;
    public EditText slot;
    public EditText rack;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        setupUI();
    }
    public void setupUI() {
        ipAddress = (EditText) findViewById(R.id.addressIPEditText);
        slot = (EditText) findViewById(R.id.slotEditText);
        rack = (EditText) findViewById(R.id.rackEditText);
        ipAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        slot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        rack.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    public void plcConnect (View view) {
        spinner.setVisibility(View.VISIBLE);
        slotInt = Integer.parseInt(slot.getText().toString());
        rackInt = Integer.parseInt(rack.getText().toString());
        ipAd = ipAddress.getText().toString();
        hideKeyboard(view);
        Log.d("myTag", "This is my message:"+ ipAd);
        new PLCConnectionChecker().execute("");
    }

    private void goToVisualDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Connection established\nGo to visualisation");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), VisualisationActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    private void notConnectedDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Connection could not be Established\nCheck parameters and try again");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private class PLCConnectionChecker extends AsyncTask<String, Void, String> {
        String ret = "";
        boolean isConnected = false;
        @Override
        protected String doInBackground(String... params) {
            try{
                client.SetConnectionType(S7.S7_BASIC);
                int res = client.ConnectTo(ipAd, rackInt,slotInt);
                if (res==0){ //connection is ok
                    //byte[] data = new byte[4];
                    //res = client.ReadArea(S7.S7AreaDB,7,0,4,data);
                    //ret = "value of DB7.DBD0:"+S7.GetFloatAt(data,0);
                    isConnected = true;
                    ret = "Connection established.";
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

        @Override
        protected void onPostExecute(String result) {
            spinner.setVisibility(View.GONE);
            if (isConnected) {
                isConnected = false;
                goToVisualDialog();
            } else {
                notConnectedDialog();
            }
        }
    }
}
