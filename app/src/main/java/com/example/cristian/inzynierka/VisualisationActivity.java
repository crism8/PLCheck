package com.example.cristian.inzynierka;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Moka7.S7;
import Moka7.S7Client;

public class VisualisationActivity extends FragmentActivity implements ListOfItemsDialog.OnDialogSelectorListener {

    private static final String TAG = "VisualisationActivity";
    private TypedArray visualizationItemsImagesArray;
    ImageButton inputButtons[] = new ImageButton[9];
    ImageButton outputButtons[] = new ImageButton[9];
    TextView inTextViews[] = new TextView [9];
    TextView outTextViews[] = new TextView[9];
    Button inDBButtons[] = new Button[9];
    Button outDBButtons[] = new Button[9];
    private String ip;
    private int slot;
    private int rack;
    private int db;
    private int dbPos;
    public S7Client client = new S7Client();
   // boolean isDefaultOnClickMethod = true;
   // Map<String,String> myMap = new HashMap<String, String>();


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
            String idInTextViewName = "posInTextView" + i;
            String idOutTextViewName = "posOutTextView" + i;
            String idInName = "inputButton" + i;
            String idOutName = "outputButton" + i;
            String idOutDBName = "outDB" + i;
            String idInDBName = "inDB" + i;
            inTextViews[i] = (TextView) findViewById(res.getIdentifier(idInTextViewName, "id", getPackageName()));
            outTextViews[i] = (TextView) findViewById(res.getIdentifier(idOutTextViewName, "id", getPackageName()));
            inDBButtons[i] = (Button) findViewById(res.getIdentifier(idInDBName, "id", getPackageName()));
            outDBButtons[i] = (Button) findViewById(res.getIdentifier(idOutDBName, "id", getPackageName()));
            inputButtons[i] = (ImageButton) findViewById(res.getIdentifier(idInName, "id", getPackageName()));
            outputButtons[i] = (ImageButton) findViewById(res.getIdentifier(idOutName, "id", getPackageName()));
            inputButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chooseItem(v);
                }
            });
            final int finalI = i;
            inDBButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView t = inTextViews[finalI];
                    //Button b = (Button) v;
                    chooseDB(inDBButtons[finalI], t);
                }
            });
            outDBButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView t = outTextViews[finalI];
                    chooseDB(outDBButtons[finalI], t);
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
        new PLCData(db, dbPos).execute("");
    }

    public void chooseItem(View view) {
        final ListOfItemsDialog sd = ListOfItemsDialog.newInstance(R.array.visualizationItemsStringArray, -1, view);
        sd.show(getSupportFragmentManager(), TAG);
    }
    public void chooseDB(final Button but, final TextView tView) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(VisualisationActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.db_params_dialog_layout, null);
        final EditText dbNumberEditText = (EditText) mView.findViewById(R.id.dbEditText);
        final EditText dbPosEditText = (EditText) mView.findViewById(R.id.dbPosEditText);
         Button okButton = (Button) mView.findViewById(R.id.OkDbButton);
         Button cancelButton = (Button) mView.findViewById(R.id.CancelDbButton);
         mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
         dialog.show();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(!dbNumberEditText.getText().toString().isEmpty() && !dbPosEditText.getText().toString().isEmpty()) {
                     dialog.dismiss();
                     db = Integer.parseInt(dbNumberEditText.getText().toString());
                     dbPos = Integer.parseInt(dbPosEditText.getText().toString());
                     String dbString = "DB: " + dbNumberEditText.getText().toString();
                     String dbPosString = "Pos: " + dbPosEditText.getText().toString();
                     but.setText(dbString);
                     tView.setText(dbPosString);
                 } else {
                     Toast.makeText(VisualisationActivity.this, R.string.dialogDesc, Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }
    public void onSelectedOption(int selectedIndex, View v) {
        ImageButton b = (ImageButton) v;
        Drawable drawable = visualizationItemsImagesArray.getDrawable(selectedIndex);
        b.setImageDrawable(drawable);
        b.setBackground(getResources().getDrawable(R.color.whiteColor));
        b.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    public void animateRotate(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) v;
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(button, "rotation", 0f, 360f);
        rotateAnimation.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotateAnimation);
        animatorSet.start();
    }
    public void animateX(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) v;
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(button, "X", 420f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX);
        animatorSet.start();
    }
    public void animateY(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) v;
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(button, "Y", 300f);
        animatorY.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();
    }
    public void animateAlpha(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) v;
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(button, View.ALPHA, 1.0f, 0.0f);
        animatorAlpha.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorAlpha);
        animatorSet.start();
    }

    private class PLCData extends AsyncTask<String, Void, String> {
        String ret = "";
        boolean isTurnOn;
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
                int res = client.ConnectTo(ip, rack, slot);
                if (res==0){ //connection is ok
                    byte[] data1 = new byte[50];

                    res = client.ReadArea(S7.S7AreaDB,dataBlockNumber,dataBlockPos,1,data1);
                    isTurnOn = S7.GetBitAt(data1,0,1);
                    ret = "value of Bool DB: :"+S7.GetBitAt(data1,0,1);
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
            if (isConnected) {
                isConnected = false;
                //goToVisualDialog();
            } else {
                //notConnectedDialog();
            }
        }
    }
}

