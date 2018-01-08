package com.example.cristian.inzynierka;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.sql.Array;

public class VisualisationActivity extends FragmentActivity implements ListOfItemsDialog.OnDialogSelectorListener {

    private static final String TAG = "VisualisationActivity";
    private TypedArray visualizationItemsImagesArray;

    public void onSelectedOption(int selectedIndex, View v) {
        ImageButton button = (ImageButton) findViewById(v.getId());
        Log.d("myTag3", "This is my messa33ge" + v.getId()+ "" + R.id.inputButton1);
        Drawable drawable = visualizationItemsImagesArray.getDrawable(selectedIndex);
        button.setImageDrawable(drawable);
        button.setBackground(getResources().getDrawable(R.color.whiteColor));
        button.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization2);
        visualizationItemsImagesArray = getResources().obtainTypedArray(R.array.visualizationItemsImagesArray);
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

