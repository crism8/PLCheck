package com.example.cristian.inzynierka;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VizualizationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private GridLayout grid;
    private int width;
    private  int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualization);

        toolbar = ((Toolbar) findViewById(R.id.toolbar2));

        grid = (GridLayout) findViewById(R.id.grid2);
        width = (int) (getResources().getDisplayMetrics().widthPixels/5f);
        height = (int) (getResources().getDisplayMetrics().heightPixels/10f);

        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.actionAdd:
                ImageView img = new ImageView(this);
                img.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                img.setImageResource(R.mipmap.ic_launcher);
                grid.addView(img);


                break;
            case R.id.circle:
                View circleView = new View(this);
                circleView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                circleView.setBackground(getResources().getDrawable(R.drawable.circle));
                //circleView.setBackgroundColor(Color.RED);
                grid.addView(circleView);

                break;
            case R.id.square:
                View squareView = new View(this);
                squareView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                squareView.setBackground(getResources().getDrawable(R.drawable.square));
             //   squareView.setBackgroundColor(Color.BLACK);
                grid.addView(squareView);

                break;
            case R.id.triangle:
                View triangleView = new View(this);
                triangleView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                triangleView.setBackground(getResources().getDrawable(R.drawable.triangle));
            //    triangleView.setBackgroundColor(Color.BLUE);
                grid.addView(triangleView);
                break;
            case R.id.line:
                View lineView = new View(this);
                lineView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                lineView.setBackground(getResources().getDrawable(R.drawable.line));
             //   lineView.setBackgroundColor(Color.BLUE);
                grid.addView(lineView);
                break;
            case R.id.rectangle:
            View rectangleView = new View(this);
            rectangleView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
            rectangleView.setBackground(getResources().getDrawable(R.drawable.rectangle));
            //
            //     rectangleView.setBackgroundColor(Color.BLUE);
            grid.addView(rectangleView);
            break;
            case R.id.arrow:
                View arrowView = new View(this);
                arrowView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                arrowView.setBackground(getResources().getDrawable(R.drawable.arrow));
                //
                //     rectangleView.setBackgroundColor(Color.BLUE);
                grid.addView(arrowView);
                break;
        }
        return true;
    }
}
