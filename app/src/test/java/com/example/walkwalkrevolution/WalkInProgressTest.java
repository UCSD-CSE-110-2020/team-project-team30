package com.example.walkwalkrevolution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.walkwalkrevolution.Fitness.FitnessService;
import com.example.walkwalkrevolution.Fitness.FitnessServiceFactory;
import com.example.walkwalkrevolution.ui.home.HomeFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.*;

import static org.junit.Assert.*;
import static com.google.common.truth.Truth.assertThat;
import androidx.test.platform.app.InstrumentationRegistry;

@RunWith(value = AndroidJUnit4.class)
public class WalkInProgressTest {

    private static final String TEST_SERVICE = "TEST_SERVICE";

    private Intent intent;
    private long nextStepCount;
    private long nextMile;

    @Before
    public void setUp() {
        FitnessServiceFactory.put(TEST_SERVICE, TestFitnessService::new);
        intent = new Intent(ApplicationProvider.getApplicationContext(), WalkInProgress.class);
        intent.putExtra(WalkInProgress.FITNESS_SERVICE_KEY, TEST_SERVICE);

        SharedPreferences sharedPreferences = InstrumentationRegistry.getInstrumentation().getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("height", "65");
        editor.apply();
    }


    //Test initial state of the stats
    @Test
    public void testBeginingOfNewwalk() {
        ActivityScenario<WalkInProgress> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {

            TextView textSteps = activity.findViewById(R.id.tv_WalkScreen);
            TextView textMiles = activity.findViewById(R.id.tv_Miles);
            assertThat(textSteps.getText().toString()).isEqualTo("0");
            assertThat(textMiles.getText().toString()).isEqualTo("0.00");
        });
    }
  
    //Test the initial stage of the home screen
    @Test
    public void testIntentionalBefore(){
        nextStepCount = 0;
        ActivityScenario<WalkInProgress> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textSteps = activity.findViewById(R.id.tv_Miles);
            activity.setMilesTextView(nextStepCount);
            activity.setStepTextView(nextStepCount);
        });

        try(ActivityScenario<MainActivity> scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.moveToState(Lifecycle.State.CREATED);
            scenario1.onActivity(activity -> {
                TextView textSteps1 = activity.findViewById(R.id.tv_last_steps);
                TextView textSteps2 = activity.findViewById(R.id.tv_last_miles);
                TextView textSteps3 = activity.findViewById(R.id.tv_last_time);
                assertThat(textSteps1.getText().toString()).isEqualTo("0 Steps");
                assertThat(textSteps2.getText().toString()).isEqualTo("0.00 Miles");
                assertThat(textSteps3.getText().toString()).isEqualTo("0 ms");
            });
        }
    }

    // 8
    @Test
    public void testIntentionalAfter(){
        nextStepCount = 1337;
        ActivityScenario<WalkInProgress> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            activity.setMilesTextView(nextStepCount);
            activity.setStepTextView(nextStepCount);
            Button b = activity.findViewById(R.id.btn_STOP);
        });

        try(ActivityScenario<MainActivity> scenario1 = ActivityScenario.launch(MainActivity.class)) {
            scenario1.moveToState(Lifecycle.State.CREATED);
            scenario1.onActivity(activity -> {
                TextView textSteps1 = activity.findViewById(R.id.tv_last_steps);
                TextView textSteps2 = activity.findViewById(R.id.tv_last_miles);
                TextView textSteps3 = activity.findViewById(R.id.tv_last_time);
                assertThat(textSteps3.getText().toString()).isEqualTo("0 ms");
            });
        }
    }

    private class TestFitnessService implements FitnessService {
        private static final String TAG = "[TestFitnessService]: ";
        private WalkInProgress stepCountActivity;

        public TestFitnessService(WalkInProgress stepCountActivity) {
            this.stepCountActivity = stepCountActivity;
        }

        @Override
        public int getRequestCode() {
            return 0;
        }

        @Override
        public void setup() {
            System.out.println(TAG + "setup");
        }

        @Override
        public void updateStepCount() {
            System.out.println(TAG + "updateStepCount");
            stepCountActivity.setStepCount(nextStepCount);
        }
    }
}