package com.example.cristian.inzynierka;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.util.HashMap;
import java.util.Map;

public class VisualisationActivity extends FragmentActivity implements ListOfItemsDialog.OnDialogSelectorListener {

    private static final String TAG = "VisualisationActivity";
    private TypedArray visualizationItemsImagesArray;
    ImageButton inputButtons[] = new ImageButton[9];
    ImageButton outputButtons[] = new ImageButton[9];
    boolean isInputButton = false;
    boolean isOutputButton = false;
    int buttonNumber = 0;
    boolean isDefaultOnClickMethod = true;
    Map<String,String> myMap = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization2);
        visualizationItemsImagesArray = getResources().obtainTypedArray(R.array.visualizationItemsImagesArray);
        setupButtons();
    }
    public void setupButtons() {
        Resources res = getResources();
        for (int i = 1; i < 9; i++) {
            String idInName = "inputButton" + i;
            String idOutName = "outputButton" + i;
            inputButtons[i] = (ImageButton) findViewById(res.getIdentifier(idInName, "id", getPackageName()));
            outputButtons[i] = (ImageButton) findViewById(res.getIdentifier(idOutName, "id", getPackageName()));
            Button OkButton = (Button) findViewById(R.id.OK_Button);
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
            OkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
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
}

