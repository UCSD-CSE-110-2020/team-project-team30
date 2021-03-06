package com.example.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.EditText;
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
    EditText mockTime;
    Button mockSubmit;
    private Chronometer chronometer;
    private FitnessService fitnessService;
    private long elapsedTime;

    final Handler handler = new Handler();
    private Timer t;
    private TimerTask updateSteps;

    public long stepCount = -1;
    public long stepCountOnStart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_in_progress);
        textSteps = findViewById(R.id.tv_WalkScreen);
        textMiles = findViewById(R.id.tv_Miles);
        chronometer = findViewById(R.id.chronometer);
        mockTime = findViewById(R.id.mock_time);
        mockSubmit = findViewById(R.id.mock_submit);

        boolean mockStatus = getIntent().getBooleanExtra("mockStatus", false);
        if(!mockStatus){
            mockTime.setVisibility(View.GONE);
            mockSubmit.setVisibility(View.GONE);
          
            boolean newRoute = getIntent().getBooleanExtra("skip route", false);

        if(newRoute){
            //switch to the fragment
            Log.d("NEW ROUTE", "it's a new route so skipping onto information fragment");
            Fragment informationFragment = new InformationFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.walk_screen_container, informationFragment, "INFO FRAG").commit();
            modifyForEmptyStats();
            return;
        }
          
        final String fitnessServiceKey = getIntent().getStringExtra(FITNESS_SERVICE_KEY);
        fitnessService = FitnessServiceFactory.create(fitnessServiceKey, this);
        String routeName = getIntent().getStringExtra("route name");


        if(routeName != null){
            TextView routeNameTextView = findViewById(R.id.textView_routeName);
            routeNameTextView.setText(routeName);
        }

        updateSteps = new TimerTask() {
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
                        Log.d("WalkInProgress: updateSteps()","SETTING THE TEXT VIEWS APPROPRIATELY");
                        setStepTextView(stepCount - stepCountOnStart);
                        setMilesTextView(stepCount - stepCountOnStart);
                    }
                });
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
                Fragment informationFragment = new InformationFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.walk_screen_container, informationFragment, "INFO FRAG").commit();
            }
        });

        chronometer.start();

        fitnessService.setup();
        }

        else{
            textSteps.setText("0");
            textMiles.setText("0.00");
            chronometer.setVisibility(View.GONE);

            mockSubmit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    SharedPreferences sharedPreferences = getSharedPreferences("mock", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String str = mockTime.getText().toString();
                    editor.putString("time", str);
                    editor.apply();
                }
            });

            final Button stopWalk = (Button) findViewById(R.id.btn_STOP);
            stopWalk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchActivity();
                    finish();

                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //If authentication was required during google fit setup, this will be called after the user authenticates
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
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void launchActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("mocking", true);
        startActivity(intent);
    }
  
    public void modifyForEmptyStats(){
        TextView routeName = findViewById(R.id.textView_routeName);
        TextView steps = findViewById(R.id.tv_WalkScreen);
        TextView miles = findViewById(R.id.tv_Miles);
        Button stopButton = findViewById(R.id.btn_STOP);

        routeName.setVisibility(View.GONE);
        stopButton.setVisibility(View.GONE);
        steps.setText("0");
        miles.setText("0.00");
    }

}
