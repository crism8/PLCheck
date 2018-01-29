package com.example.cristian.inzynierka;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
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
    private TypedArray conveyorItemsImagesArray;
    private TypedArray pumpItemsImagesArray;
    private TypedArray lightItemsImagesArray;
    private TypedArray blackBulbItemsImagesArray;
    private TypedArray diodeItemsImagesArray;
    private ImageButton inputButtons[] = new ImageButton[9];
    private ImageButton outputButtons[] = new ImageButton[9];
    private TextView inTextViews[] = new TextView [9];
    private TextView outTextViews[] = new TextView[9];
    private Button inDBButtons[] = new Button[9];
    private Button outDBButtons[] = new Button[9];
    private String ip;
    private int slot;
    private int rack;
    private int dbIn[] = new int[9];
    private int dbPosIn[]= new int[9];
    private int dbOut[] = new int[9];
    private int dbPosOut[]= new int[9];
    private int buttonAnimation[][]= new int[16][2];
    public S7Client client = new S7Client();
    private int numberOfSetButton = 0;


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
                    chooseInDB(inDBButtons[finalI], t, finalI);
                }
            });
            outDBButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView t = outTextViews[finalI];
                    chooseOutDB(outDBButtons[finalI], t, finalI);
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
        for (int i = 1; i < 9; i++) {
            if (dbIn[i]!= 0 && dbPosIn[i]!= 0) {
                new PLCData(dbIn[i], dbPosIn[i], i).execute("");

            }
            if (dbOut[i]!= 0 && dbPosOut[i]!= 0) {
                new PLCData(dbOut[i], dbPosOut[i], i).execute("");
            }
        }
    }
    public void startAnimating (int i) {
        for (int j = 0; j <=numberOfSetButton; j++){
            if (inputButtons[i].getId() == buttonAnimation[j][0]) {
            switch (buttonAnimation[j][1]) {
                case 0:
                    animatePump(inputButtons[i]);
                    break;
                case 1:
                    animateConvoyer(inputButtons[i]);
                    break;
                case 4:
                    animateLight(inputButtons[i]);
                    break;
                case 5:
                    animateBlackBulb(inputButtons[i]);
                    break;
                case 6:
                    animateDiode(inputButtons[i]);
                    break;
                    default:
                        animateRotate(inputButtons[i]);
                }
            }
            if (outputButtons[i].getId() == buttonAnimation[j][0]) {
                switch (buttonAnimation[j][1]) {
                    case 0:
                        animatePump(outputButtons[i]);
                        break;
                    case 1:
                        animateConvoyer(outputButtons[i]);
                        break;
                    case 4:
                        animateLight(outputButtons[i]);
                        break;
                    case 5:
                        animateBlackBulb(outputButtons[i]);
                        break;
                    case 6:
                        animateDiode(outputButtons[i]);
                        break;
                    default:
                        animateRotate(outputButtons[i]);
                }
            }
        }
    }

    public void chooseItem(View view) {
        final ListOfItemsDialog sd = ListOfItemsDialog.newInstance(R.array.visualizationItemsStringArray, -1, view);
        sd.show(getSupportFragmentManager(), TAG);
    }
    public void chooseOutDB(final Button but, final TextView tView, final int numberOfComp) {
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
                     dbOut[numberOfComp] = Integer.parseInt(dbNumberEditText.getText().toString());
                     dbPosOut[numberOfComp] = Integer.parseInt(dbPosEditText.getText().toString());
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
    public void chooseInDB(final Button but, final TextView tView, final int numberOfComp) {
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
                    dbIn[numberOfComp] = Integer.parseInt(dbNumberEditText.getText().toString());
                    dbPosIn[numberOfComp] = Integer.parseInt(dbPosEditText.getText().toString());
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
        buttonAnimation[numberOfSetButton][0] = v.getId();
        Drawable drawable = visualizationItemsImagesArray.getDrawable(selectedIndex);
        buttonAnimation[numberOfSetButton][1] = selectedIndex;
        b.setImageDrawable(drawable);
        b.setBackground(getResources().getDrawable(R.color.whiteColor));
        b.setScaleType(ImageView.ScaleType.FIT_CENTER);
        numberOfSetButton ++;
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
    public void animatePump(View v) {
        final ImageButton ImgBut = (ImageButton) v;
        pumpItemsImagesArray = getResources().obtainTypedArray(R.array.pumpItemsImagesArray);
        new CountDownTimer(1000,100) {
            int i=0;
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                Drawable drawable = pumpItemsImagesArray.getDrawable(i);
                ImgBut.setImageDrawable(drawable);
                ImgBut.setBackground(getResources().getDrawable(R.color.whiteColor));
                ImgBut.setScaleType(ImageView.ScaleType.FIT_CENTER);
                i++;
                if(i== pumpItemsImagesArray.getIndexCount()-1) i=0;
                start();
            }
        }.start();
    }
    public void animateLight(View v) {
        final ImageButton ImgBut = (ImageButton) v;
        lightItemsImagesArray = getResources().obtainTypedArray(R.array.lightItemsImagesArray);
        new CountDownTimer(1000,100) {
            int i=0;
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                Drawable drawable = lightItemsImagesArray.getDrawable(i);
                ImgBut.setImageDrawable(drawable);
                ImgBut.setBackground(getResources().getDrawable(R.color.whiteColor));
                ImgBut.setScaleType(ImageView.ScaleType.FIT_CENTER);
                i++;
                if(i== lightItemsImagesArray.getIndexCount()-1) i=0;
                start();
            }
        }.start();
    }
    public void animateBlackBulb(View v) {
        final ImageButton ImgBut = (ImageButton) v;
        blackBulbItemsImagesArray = getResources().obtainTypedArray(R.array.blackBulbItemsImagesArray);
        new CountDownTimer(1000,100) {
            int i=0;
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                Drawable drawable = blackBulbItemsImagesArray.getDrawable(i);
                ImgBut.setImageDrawable(drawable);
                ImgBut.setBackground(getResources().getDrawable(R.color.whiteColor));
                ImgBut.setScaleType(ImageView.ScaleType.FIT_CENTER);
                i++;
                if(i== blackBulbItemsImagesArray.getIndexCount()-1) i=0;
                start();
            }
        }.start();
    }
    public void animateDiode(View v) {
        final ImageButton ImgBut = (ImageButton) v;
        diodeItemsImagesArray = getResources().obtainTypedArray(R.array.diodeItemsImagesArray);
        new CountDownTimer(1000,100) {
            int i=0;
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                Drawable drawable = diodeItemsImagesArray.getDrawable(i);
                ImgBut.setImageDrawable(drawable);
                ImgBut.setBackground(getResources().getDrawable(R.color.whiteColor));
                ImgBut.setScaleType(ImageView.ScaleType.FIT_CENTER);
                i++;
                if(i== diodeItemsImagesArray.getIndexCount()-1) i=0;
                start();
            }
        }.start();
    }
    public void animateConvoyer(final View v) {
        conveyorItemsImagesArray = getResources().obtainTypedArray(R.array.conveyorItemsImagesArray);
        new CountDownTimer(1000,100) {
            int i=0;
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                ImageButton ImgBut = (ImageButton) v;
                //ImgBut.setImageResource(R.drawable.fountain);
                Drawable drawable = conveyorItemsImagesArray.getDrawable(i);
                ImgBut.setImageDrawable(drawable);
                ImgBut.setBackground(getResources().getDrawable(R.color.whiteColor));
                ImgBut.setScaleType(ImageView.ScaleType.FIT_CENTER);
                i++;
                if(i== conveyorItemsImagesArray.getIndexCount()-1) i=0;
                start();
            }
        }.start();
    }

    private class PLCData extends AsyncTask<String, Void, String> {
        String ret = "";
        boolean isTurnOn;
        int dataBlockNumber;
        int dataBlockPos;
        int butNumber;
        public PLCData(int i, int j, int butnumb) {
            dataBlockNumber = i;
            dataBlockPos = j;
            butNumber = butnumb;
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
                startAnimating(butNumber);
            } else {
                //notConnectedDialog();
            }
        }
    }
}

