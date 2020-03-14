package com.example.walkwalkrevolution;


import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.walkwalkrevolution.Fitness.FitnessServiceFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.truth.Truth.assertThat;

@RunWith(value = AndroidJUnit4.class)
public class HomeFragmentTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";

    private Intent intent;
    private long nextStepCount;
    private long nextMile;

    @Before
    public void setUp() {

        SharedPreferences sharedPreferences = InstrumentationRegistry.getInstrumentation().getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("height", "65");
        editor.apply();
    }

    //Original State of the HomeFragment
    @Test
    public void testHome() {
        intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.INTENT_KEY_FIREBASE_INTERACTOR, MainActivity.INTENT_USE_MOCK_INTERACTOR);
        try(ActivityScenario<MainActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.moveToState(Lifecycle.State.CREATED);
            scenario.onActivity(activity -> {
                TextView textSteps = activity.findViewById(R.id.text_Step);
                TextView textMiles = activity.findViewById(R.id.text_Miles);
                TextView lastSteps = activity.findViewById(R.id.tv_last_steps);
                TextView lastMiles = activity.findViewById(R.id.tv_last_miles);
                TextView lastTime = activity.findViewById(R.id.tv_last_time);
                assertThat(textSteps.getText().toString()).isEqualTo("0 Steps");
                assertThat(lastSteps.getText().toString()).isEqualTo("0 Steps");
                assertThat(lastTime.getText().toString()).isEqualTo("0 ms");
            });
        }
    }


    //Testing the mock button
    @Test
    public void testMockButton() {
        intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.INTENT_KEY_FIREBASE_INTERACTOR, MainActivity.INTENT_USE_MOCK_INTERACTOR);
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                Button mock = activity.findViewById(R.id.btn_goToMock);
                Button mockStep = activity.findViewById(R.id.btn_mockStep);
                TextView totalStep = activity.findViewById(R.id.text_Step);
                mock.performClick();
                assertThat(mockStep.getVisibility()).isEqualTo(View.VISIBLE);
                mockStep.performClick();
                assertThat(totalStep.getText().toString()).isEqualTo("500 Steps");
                mock.performClick();
                assertThat(mockStep.getVisibility()).isEqualTo(View.GONE);
            });
        }
    }

    //Test that if performing the click switch the activity
    @Test
    public void testStartWalk() {
        intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.INTENT_KEY_FIREBASE_INTERACTOR, MainActivity.INTENT_USE_MOCK_INTERACTOR);
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                Button startWalk = activity.findViewById(R.id.btn_GoToWalk);
                startWalk.performClick();
                assertThat(scenario.getState()).isEqualTo(Lifecycle.State.RESUMED);
            });
        }
    }


}
