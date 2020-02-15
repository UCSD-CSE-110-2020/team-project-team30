package com.example.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.walkwalkrevolution.Fitness.FitnessService;
import com.example.walkwalkrevolution.Fitness.FitnessServiceFactory;

import com.example.walkwalkrevolution.ui.information.InformationFragment;

import java.util.Timer;
import java.util.TimerTask;

public class WalkInProgress extends AppCompatActivity {

    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    private String fitnessServiceKey = "GOOGLE_FIT";

    private static final String TAG = "WalkInProgress";

    private TextView textSteps;
    private TextView textMiles;
    private Chronometer chronometer;
    private FitnessService fitnessService;
    private long elapsedTime;


    final Handler handler = new Handler();
    private Timer t;
    private TimerTask updateSteps;

    public long stepCount = -1;
    public long stepCountOnStart = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_in_progress);
        textSteps = findViewById(R.id.tv_WalkScreen);
        textMiles = findViewById(R.id.tv_Miles);
        chronometer = findViewById(R.id.chronometer);


        final String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);

        updateSteps = new TimerTask() {
            //long pseudoStep = 0;
            boolean isFirstTime = true;
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        fitnessService.updateStepCount();
                        if( stepCount > 0 && isFirstTime == true) {
                            isFirstTime = false;
                            stepCountOnStart = stepCount;
                        }

                        setStepTextView(stepCount - stepCountOnStart);
                        setMilesTextView(stepCount - stepCountOnStart);
                    }
                });
                //pseudoStep++;
            }
        };

        t = new Timer();
        t.schedule(updateSteps, 0, 100);

        final Button stopWalk = (Button) findViewById(R.id.btn_STOP);

        stopWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                elapsedTime = getElapsedTime();
                save(v);

                stopWalk.setVisibility(View.GONE);
                /*
                Fragment informationFragment = new InformationFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.walk_screen_container, informationFragment);
                transaction.commit();
                 */
                //stop the fitness service!
                Fragment InformationFragment = new InformationFragment();
                // adding fragment to relative layout by using layout id
                getSupportFragmentManager().beginTransaction().add(R.id.walk_screen_container, InformationFragment).commit();
            }
        });

        chronometer.start();

        fitnessService.setup();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

//       If authentication was required during google fit setup, this will be called after the user authenticates
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == fitnessService.getRequestCode()) {
                fitnessService.updateStepCount();
            }
        }
        else {
            Log.e(TAG, "ERROR, google fit result code: " + resultCode);
        }
    }

    public void setStepCount(long stepCount) {
        //textSteps.setText(String.valueOf(stepCount));
        this.stepCount = stepCount;
    }

    public void setStepTextView(long stepCount) {
        textSteps.setText(String.valueOf(stepCount));
    }

    public void setMiles(long stepCount) {}

    public void setMilesTextView(long stepCount){
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        String heightStr = sharedPreferences.getString("height", "");
        int height = Integer.valueOf(heightStr);
        double stride = (height*0.413)/63360;
        double result = (stepCount*stride);
        textMiles.setText(String.format("%.2f", result));
    }

    public void save(View view){
        TextView steps = (TextView) findViewById(R.id.tv_WalkScreen);
        TextView miles = (TextView) findViewById(R.id.tv_Miles);

        SharedPreferences sharedPreferences = getSharedPreferences("last_walk", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("steps", steps.getText().toString());
        editor.putString("miles", miles.getText().toString());
        editor.putString("time", String.valueOf(elapsedTime));

        editor.apply();
    }

    public long getElapsedTime() {
        long time = SystemClock.elapsedRealtime() - chronometer.getBase();
        return time;
    }


    @Override
    protected void onStart() {
        super.onStart();

        /*
        long t0 = getElapsedTime();
        while(getElapsedTime() - t0 < 1000);
        fitnessService.updateStepCount();
        stepCountOnStart = stepCount;
        //setStepTextView(stepCount - stepCountOnStart);
*/
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
