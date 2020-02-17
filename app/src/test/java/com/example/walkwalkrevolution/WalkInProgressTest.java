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


    //1
    @Test
    public void testBeginingOfNewwalk() {
        ActivityScenario<WalkInProgress> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {

            TextView textSteps = activity.findViewById(R.id.tv_WalkScreen);
            assertThat(textSteps.getText().toString()).isEqualTo("-10000");
        });
    }

    //2
    @Test
    public void testBeginingOfMile() {
        ActivityScenario<WalkInProgress> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {

            TextView textSteps = activity.findViewById(R.id.tv_Miles);
            assertThat(textSteps.getText().toString()).isEqualTo("-4.24");
        });
    }

    //3
    @Test
    public void testStepCount() {
        nextStepCount = 1337;
        ActivityScenario<WalkInProgress> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textSteps = activity.findViewById(R.id.tv_WalkScreen);
            activity.setStepTextView(nextStepCount);
            assertThat(textSteps.getText().toString()).isEqualTo("1337");
        });
    }


    //4
    @Test
    public void testMileCount() {
        nextStepCount = 1337;
        ActivityScenario<WalkInProgress> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView textSteps = activity.findViewById(R.id.tv_Miles);
            activity.setMilesTextView(nextStepCount);
            assertThat(textSteps.getText().toString()).isEqualTo("0.57");
        });
    }


    // 5
    @Test
    public void testHomesStep() {
        try(ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.moveToState(Lifecycle.State.CREATED);
            scenario.onActivity(activity -> {
                TextView textSteps = activity.findViewById(R.id.text_Step);
                assertThat(textSteps.getText().toString()).isEqualTo("0 Steps");
            });
        }
    }

    // 6
    @Test
    public void testHomeMile() {
        try(ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.moveToState(Lifecycle.State.CREATED);
            scenario.onActivity(activity -> {
                TextView textSteps = activity.findViewById(R.id.text_Miles);
                assertThat(textSteps.getText().toString()).isEqualTo(" Miles");
            });
        }
    }

    // 7
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
                assertThat(textSteps1.getText().toString()).isEqualTo(" Steps");
                assertThat(textSteps2.getText().toString()).isEqualTo(" Miles");
                assertThat(textSteps3.getText().toString()).isEqualTo(" ms");
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