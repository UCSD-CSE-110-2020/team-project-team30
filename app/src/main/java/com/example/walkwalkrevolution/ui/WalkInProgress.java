package com.example.walkwalkrevolution.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.walkwalkrevolution.Fitness.FitnessService;
import com.example.walkwalkrevolution.Fitness.FitnessServiceFactory;
import com.example.walkwalkrevolution.R;

public class WalkInProgress extends AppCompatActivity {

    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";

    private static final String TAG = "WalkInProgress";

    private TextView textSteps;
    private TextView textMiles;
    private Chronometer chronometer;
    private FitnessService fitnessService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_in_progress);
        textSteps = findViewById(R.id.tv_WalkScreen);
        textMiles = findViewById(R.id.tv_Miles);
        chronometer = findViewById(R.id.chronometer);

        String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);

        Button stopWalk = (Button) findViewById(R.id.btn_STOP);

        stopWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                finish();
            }
        });

        fitnessService.updateStepCount();
        chronometer.start();

        fitnessService.setup();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//       If authentication was required during google fit setup, this will be called after the user authenticates
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == fitnessService.getRequestCode()) {
                fitnessService.updateStepCount();
            }
        } else {
            Log.e(TAG, "ERROR, google fit result code: " + resultCode);
        }
    }

    public void setStepCount(long stepCount) {
        textSteps.setText(String.valueOf(100));
    }

    public void setMiles(long stepCount){
        double stride = (65*0.413)/150000;
        double result = (stepCount*65*stride);
        textMiles.setText(String.format("%.2f", result));
    }

}
