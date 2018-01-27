package com.example.cristian.inzynierka;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToVisualization(View view) {
        Intent intent = new Intent(this, VisualisationActivity.class);
        startActivity(intent);
    }
    public void goToConnect(View view) {
        Intent intent = new Intent(this, ConnectActivity.class);
        startActivity(intent);
    }

    public void goToHouseControl(View view) {
        Intent intent = new Intent(this, HouseControlActivity.class);
        startActivity(intent);
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
