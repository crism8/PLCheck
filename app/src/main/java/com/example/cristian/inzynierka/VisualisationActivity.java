package com.example.cristian.inzynierka;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.util.HashMap;
import java.util.Map;

import Moka7.S7;
import Moka7.S7Client;

public class VisualisationActivity extends FragmentActivity implements ListOfItemsDialog.OnDialogSelectorListener {

    private static final String TAG = "VisualisationActivity";
    private TypedArray visualizationItemsImagesArray;
    ImageButton inputButtons[] = new ImageButton[9];
    ImageButton outputButtons[] = new ImageButton[9];
    boolean isInputButton = false;
    boolean isOutputButton = false;
    private int buttonNumber = 0;
    private String ip;
    private int slot;
    private int rack;
    public S7Client client = new S7Client();
    boolean isDefaultOnClickMethod = true;
    Map<String,String> myMap = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization2);
        visualizationItemsImagesArray = getResources().obtainTypedArray(R.array.visualizationItemsImagesArray);
        getDataForConnection();
        setupButtons();
    }

    private void getDataForConnection() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ip = extras.getString("goodIp");
            rack = extras.getInt("goodRack");
            slot = extras.getInt("goodSlot");
        }
    }

    public void setupButtons() {
        Resources res = getResources();
        for (int i = 1; i < 9; i++) {
            String idInName = "inputButton" + i;
            String idOutName = "outputButton" + i;
            inputButtons[i] = (ImageButton) findViewById(res.getIdentifier(idInName, "id", getPackageName()));
            outputButtons[i] = (ImageButton) findViewById(res.getIdentifier(idOutName, "id", getPackageName()));
            Log.d("myTag4", "This is my messa33ge" + idInName + idOutName);
            inputButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseItem(v);
                }
            });
            outputButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseItem(v);
                }
            });
        }
        Button OkButton = (Button) findViewById(R.id.OK_Button);
        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVisual();
            }
        });
    }
    public void startVisual() {
        new PLCData().execute("");
    }

    public void chooseItem(View view) {
        final ListOfItemsDialog sd = ListOfItemsDialog.newInstance(R.array.visualizationItemsStringArray, -1, view);
        sd.show(getSupportFragmentManager(), TAG);
    }
    public void onSelectedOption(int selectedIndex, View v) {
        for (int i = 1; i < 9; i++) {
            if (v.getId() == inputButtons[i].getId()) {
                isInputButton = true;
                buttonNumber = i;
                break;
            } else if (v.getId() == outputButtons[i].getId()) {
                isOutputButton = true;
                buttonNumber = i;
                break;
            }
        }
        Drawable drawable = visualizationItemsImagesArray.getDrawable(selectedIndex);
        if (isOutputButton) {
            outputButtons[buttonNumber].setImageDrawable(drawable);
            outputButtons[buttonNumber].setBackground(getResources().getDrawable(R.color.whiteColor));
            outputButtons[buttonNumber].setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else if (isInputButton) {
            inputButtons[buttonNumber].setImageDrawable(drawable);
            inputButtons[buttonNumber].setBackground(getResources().getDrawable(R.color.whiteColor));
            inputButtons[buttonNumber].setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        isOutputButton = false;
        isInputButton = false;
    }

    public void animateRotate(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) findViewById(v.getId());
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(button, "rotation", 0f, 360f);
        rotateAnimation.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotateAnimation);
        animatorSet.start();
    }
    public void animateX(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) findViewById(v.getId());
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(button, "X", 420f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();
    }
    public void animateY(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) findViewById(v.getId());
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(button, "Y", 300f);
        animatorY.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();
    }
    public void animateAlpha(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) findViewById(v.getId());
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(button, View.ALPHA, 1.0f, 0.0f);
        animatorAlpha.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorAlpha);
        animatorSet.start();
    }
    private class PLCData extends AsyncTask<String, Void, String> {
        String ret = "";
        String ret2 = "";
        String ret3 = "";
        String ret4 = "";
        String ret5 = "";

        boolean isConnected = false;
        @Override
        protected String doInBackground(String... params) {
            try{
                client.SetConnectionType(S7.S7_BASIC);
                int res = client.ConnectTo("192.168.1.100", 0, 1);
                if (res==0){ //connection is ok
                    byte[] data1 = new byte[50];
                    byte[] data2 = new byte[50];
                    byte[] data3 = new byte[50];
                    byte[] data4 = new byte[50];
                    byte[] data5 = new byte[50];


                    res = client.ReadArea(S7.S7AreaDB,1,0,32,data1);
                    res = client.ReadArea(S7.S7AreaDB,1,1,32,data1);
                    res = client.ReadArea(S7.S7AreaDB,1,0,8,data1);

                    res = client.ReadArea(S7.S7AreaDB,2,0,32,data2);
                    res = client.ReadArea(S7.S7AreaDB,2,1,32,data2);
                    res = client.ReadArea(S7.S7AreaDB,2,0,16,data2);

                    res = client.ReadArea(S7.S7AreaDB,3,0,32,data3);
                    res = client.ReadArea(S7.S7AreaDB,3,2,32,data3);
                    res = client.ReadArea(S7.S7AreaDB,3,3,32,data3);


                    res = client.ReadArea(S7.S7AreaDB,4,0,32,data4);
                    res = client.ReadArea(S7.S7AreaDB,4,1,16,data4);
                    res = client.ReadArea(S7.S7AreaDB,5,0,32,data5);
                    res = client.ReadArea(S7.S7AreaDB,4,3,12,data5);

                    ret = "value of Bool DB: :"+S7.GetBitAt(data1,1,1);
                    ret2 = "value of DInt DB: "+ S7.GetDIntAt(data2, 11);
                    ret3 = "value of Printable String DB: "+ S7.GetPrintableStringAt(data3, 1, 16);
                    ret4 = "value of String DB: "+ S7.GetStringAt(data4, 1, 16);
                    ret5 = "value of Date DB: "+ S7.GetDateAt(data5, 1);

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

        @Override
        protected void onPostExecute(String result) {
            Log.d("ret", ret);
            //spinner.setVisibility(View.GONE);
            if (isConnected) {
                isConnected = false;
                //goToVisualDialog();
            } else {
                //notConnectedDialog();
            }
        }
    }
}

