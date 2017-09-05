package com.example.cristian.inzynierka;

import android.app.Activity;
import android.app.VoiceInteractor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import Moka7.*;

public class ConnectActivity extends AppCompatActivity {

    String ipAd;
    int slotInt;
    int rackInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    S7Client client = new S7Client();

    public void plcConnect (View view) {
        EditText ipAddress = (EditText) findViewById(R.id.addressIPEditText);
        EditText slot = (EditText) findViewById(R.id.slotEditText);
        EditText rack = (EditText) findViewById(R.id.rackEditText);

        ipAd = ipAddress.getText().toString();
        slotInt = Integer.parseInt(slot.getText().toString());
        rackInt = Integer.parseInt(rack.getText().toString());

      //  new PlcReader().execute("");
    }
/*
    private class PlcReader extends AsyncTask<String, Void, String> {
    String ret = "";

        @Override
        protected String doInBackground(String... params) {
            try{
                client.SetConnectionType(S7.S7_BASIC);
                int res = client.ConnectTo(ipAd, rackInt,slotInt);

                if (res==0){ //connection is ok
                        byte[] data = new byte[4];
                    res = client.ReadArea(S7.S7AreaDB,7,0,4,data);
                    ret = "value of DB7.DBD0:"+S7.GetFloatAt(data,0);
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
        TextView txout = (TextView) findViewById(R.id.testtextView);
        txout.setText(ret);

    }
    }
*/
}
