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
import android.widget.ImageButton;
import android.widget.ImageView;

public class VisualisationActivity extends FragmentActivity implements ListOfItemsDialog.OnDialogSelectorListener {

    private static final String TAG = "VisualisationActivity";
    private TypedArray visualizationItemsImagesArray;
    ImageButton inputButtons[] = new ImageButton[9];
    ImageButton outputButtons[] = new ImageButton[9];
    boolean isInputButton = false;
    boolean isOutputButton = false;
    int buttonNumber = 0;


    public void onSelectedOption(int selectedIndex, View v) {
        for (int i = 1; i < 9; i++) {
            if (v.getId() == inputButtons[i].getId()) {
                isInputButton = true;
            } else if (v.getId() == outputButtons[i].getId()) {
                isOutputButton = true;
            }
            buttonNumber = i;
        }
        //ImageButton button = (ImageButton) findViewById(v.getId());
        Log.d("myTag3", "This is my messa33ge" + v.getId() + "" + R.id.inputButton1);
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
        chooseAnimationForItem(selectedIndex, buttonNumber, isOutputButton);
    }
    public void chooseAnimationForItem(int animationNumber, int buttonNumber, boolean isInOut) {
        if (isInOut) {
            ImageButton switchButton = outputButtons[buttonNumber];
        } else {
            ImageButton switchButton = inputButtons[buttonNumber];
        }
        switch(animationNumber) {
            case 0:
                break;
            case 1:
                break;
            default:

        }
    }


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
    }

    public void chooseItem(View view) {
        final ListOfItemsDialog sd = ListOfItemsDialog.newInstance(R.array.visualizationItemsStringArray, -1, view);
        sd.show(getSupportFragmentManager(), TAG);
    }

    public void animateRotate(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) findViewById(v.getId());
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(button, "rotation", 0f, 360f);
        rotateAnimation.setDuration(animationDuration);
        AnimatorSet animmatorSet = new AnimatorSet();
        animmatorSet.playTogether(rotateAnimation);
        animmatorSet.start();
    }
    public void animateX(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) findViewById(v.getId());
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(button, "X", 420f);
        animatorX.setDuration(animationDuration);
        AnimatorSet animmatorSet = new AnimatorSet();
        animmatorSet.playTogether(animatorX);
        animmatorSet.start();
    }
    public void animateY(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) findViewById(v.getId());
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(button, "Y", 300f);
        animatorY.setDuration(animationDuration);
        AnimatorSet animmatorSet = new AnimatorSet();
        animmatorSet.playTogether(animatorY);
        animmatorSet.start();
    }
    public void animateAlpha(View v) {
        long animationDuration = 1000;
        ImageButton button = (ImageButton) findViewById(v.getId());
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(button, View.ALPHA, 1.0f, 0.0f);
        animatorAlpha.setDuration(animationDuration);
        AnimatorSet animmatorSet = new AnimatorSet();
        animmatorSet.playTogether(animatorAlpha);
        animmatorSet.start();
    }
}

