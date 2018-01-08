package com.example.cristian.inzynierka;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import Moka7.*;

public class ConnectActivity extends AppCompatActivity {
    String ipAd;
    int slotInt;
    int rackInt;
    S7Client client = new S7Client();
    EditText ipAddress;
    EditText slot;
    EditText rack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        setupUI();

    }
    public void setupUI() {
        EditText ipAddress = (EditText) findViewById(R.id.addressIPEditText);
        EditText slot = (EditText) findViewById(R.id.slotEditText);
        EditText rack = (EditText) findViewById(R.id.rackEditText);
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
        EditText ipAddress = (EditText) findViewById(R.id.addressIPEditText);
        EditText slot = (EditText) findViewById(R.id.slotEditText);
        EditText rack = (EditText) findViewById(R.id.rackEditText);
        slotInt = Integer.parseInt(slot.getText().toString());
        rackInt = Integer.parseInt(rack.getText().toString());
        ipAd = ipAddress.getText().toString();
        Log.d("myTag", "This is my message:"+ ipAd);
        new PLCConnectionChecker().execute("");
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private class PLCConnectionChecker extends AsyncTask<String, Void, String> {
        String ret = "";
        @Override
        protected String doInBackground(String... params) {
            try{
                client.SetConnectionType(S7.S7_BASIC);
                int res = client.ConnectTo(ipAd, rackInt,slotInt);

                if (res==0){ //connection is ok
                    //byte[] data = new byte[4];
                    //res = client.ReadArea(S7.S7AreaDB,7,0,4,data);
                    //ret = "value of DB7.DBD0:"+S7.GetFloatAt(data,0);
                    ret = "Connection established.";
                }else{
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
            TextView txout = (TextView) findViewById(R.id.ConnectTextView);
            txout.setText(ret);
        }
    }
}
