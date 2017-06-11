package com.example.cristian.inzynierka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private GridLayout grid;
    private int width;
    private  int height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = ((Toolbar) findViewById(R.id.toolbar));

        grid = (GridLayout) findViewById(R.id.grid);
        width = (int) (getResources().getDisplayMetrics().widthPixels/3f);
        height = (int) (getResources().getDisplayMetrics().heightPixels/10f);



        setSupportActionBar(toolbar);
        findViewById(R.id.dodaj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             TextView tv = (TextView) findViewById(R.id.label);
                tv.setText("Cessc");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                break;
            case R.id.square:
                break;
            case R.id.triangle:
                break;
        }
        return true;
    }
}
