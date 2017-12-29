package com.example.cristian.inzynierka;

import android.graphics.Color;
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


public class VisualisationActivity extends FragmentActivity implements ListOfItemsDialog.OnDialogSelectorListener {
    private Toolbar toolbar;
    private GridLayout grid;
    private int width;
    private  int height;
    private int numberOfColumns;
    private  int numberOfRows;
    private static final String TAG = "VisualisationActivity";


    public void onSelectedOption(int selectedIndex, View v) {
        // do something with the newly selected index
        ImageButton button = (ImageButton) findViewById(v.getId());
        Log.d("myTag3", "This is my messa33ge" + v.getId()+ "" + R.id.inputButton1);

        button.setBackgroundResource(R.drawable.ic_control_point_black_24dp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization);

        toolbar = ((Toolbar) findViewById(R.id.toolbar2));
        numberOfRows = 8;
        numberOfColumns = 2;

        grid = (GridLayout) findViewById(R.id.grid2);
        width = (int) (getResources().getDisplayMetrics().widthPixels/numberOfColumns);
        height = (int) (getResources().getDisplayMetrics().heightPixels/numberOfRows);

       // setSupportActionBar(toolbar);
    }
    public void goToCoonnect(View view) {
        final ListOfItemsDialog sd = ListOfItemsDialog.newInstance(R.array.visualizationItemsStringArray, -1, view);
        sd.show(getSupportFragmentManager(), TAG);
    }
    public void printNo( View v ) {
        //Log.d("myTag3", "This is my messa33ge" + v.getId() + R.id.inputButton1);

        ImageButton button = (ImageButton) findViewById(v.getId());
        button.setBackgroundResource(R.drawable.ic_control_point_black_24dp);
        //button.setBackgroundColor(R.color.colorAccent);
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.menu, menu);
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
                    ImageView Rectangle = new ImageView(this);
                    Rectangle.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                    Rectangle.setImageResource(R.drawable.jamonia);
           /* View rectangleView = new View(this);
            rectangleView.setBackground(getResources().getDrawable(R.drawable.rectangle)); */
                    //
                    //     rectangleView.setBackgroundColor(Color.BLUE);
                    grid.addView(Rectangle);
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

