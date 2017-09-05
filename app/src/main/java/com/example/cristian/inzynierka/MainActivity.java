package com.example.cristian.inzynierka;

import android.content.Intent;
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

import com.example.cristian.inzynierka.elevationdrag.VisualisationActivity;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = ((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
    }
    public void goToVizualization(View view) {
        Intent intent = new Intent(this, VizualizationActivity.class);

        startActivity(intent);
    }
    public void goToConnect(View view) {
        Intent intent = new Intent(this, ConnectActivity.class);

        startActivity(intent);
    }

    public void goToVisual(View view) {
        Intent intent = new Intent(this, VisualisationActivity.class);

        startActivity(intent);
    }

    public void goToDraw(View view) {
        Intent intent = new Intent(this, DrawingActivity.class);

        startActivity(intent);
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

}
