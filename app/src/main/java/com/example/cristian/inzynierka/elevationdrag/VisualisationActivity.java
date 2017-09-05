package com.example.cristian.inzynierka.elevationdrag;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.cristian.inzynierka.R;

public class VisualisationActivity extends AppCompatActivity {
    private int width;
    private int height;
    private GridLayout grid;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualisation);
        toolbar = ((Toolbar) findViewById(R.id.visualToolbar));

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ElevationDragFragment fragment = new ElevationDragFragment();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();

        width = (int) (getResources().getDisplayMetrics().widthPixels/3f);
        height = (int) (getResources().getDisplayMetrics().heightPixels/10f);

        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.actionAdd:
                 /*
                View circle = new View(this);
                circle.setLayoutParams(new LinearLayout.LayoutParams(width, height));


                ImageView img = new ImageView(this);
                img.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                img.setImageResource(R.mipmap.ic_launcher);
                grid.addView(img);
                */


                break;
            case R.id.circle:
                break;
            case R.id.square:
                break;
            case R.id.triangle:
                break;
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
