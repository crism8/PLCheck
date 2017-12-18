package com.example.cristian.inzynierka;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Point;
import android.widget.ImageButton;
import android.view.LayoutInflater;
import android.content.Context;
import android.widget.PopupWindow;


public class VisualisationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private GridLayout grid;
    private int width;
    private  int height;
    private int numberOfColumns;
    private  int numberOfRows;
    Point p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualization);

        toolbar = ((Toolbar) findViewById(R.id.toolbar2));
        numberOfRows = 8;
        numberOfColumns = 2;

        grid = (GridLayout) findViewById(R.id.grid2);
        width = (int) (getResources().getDisplayMetrics().widthPixels/numberOfColumns);
        height = (int) (getResources().getDisplayMetrics().heightPixels/numberOfRows);

        setSupportActionBar(toolbar);
    }
    public void addWidget (View arg0) {
        if (p != null)
            showPopup(VisualisationActivity.this, p);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        int[] location = new int[2];
        ImageButton button = (ImageButton) findViewById(R.id.Button1);

        // Get the x, y location and store it in the location[] array
        // location[0] = x, location[1] = y.
        button.getLocationOnScreen(location);

        //Initialize the Point with x, and y positions
        p = new Point();
        p.x = location[0];
        p.y = location[1];
    }

    // The method that displays the popup.
    private void showPopup(final AppCompatActivity context, Point p) {
        int popupWidth = 200;
        int popupHeight = 150;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
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

