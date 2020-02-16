package com.example.walkwalkrevolution;

import androidx.test.espresso.Root;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.walkwalkrevolution.Fitness.FitnessService;
import com.example.walkwalkrevolution.Fitness.FitnessServiceFactory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WalkInProgressEspressoTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //Test transition from Home to Walk in Progress
    @Test
    public void EnterWalkInProgressEspressoTest() {
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(WalkInProgress walkInProgressActivity) {
                return new TestFitnessService(walkInProgressActivity);
            }
        });

        ViewInteraction button = onView(
                allOf(withId(R.id.btn_GoToWalk),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_GoToWalk), withText("Go To Walk"),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_Miles),
                        isDisplayed()));
        textView.check(matches(withText("-2.61")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_WalkScreen),
                        isDisplayed()));
        textView2.check(matches(withText("-10000")));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.btn_STOP), withText("STOP"),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction chronometer = onView(
                allOf(withId(R.id.chronometer),
                        isDisplayed()));
        chronometer.check(matches(isDisplayed()));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_STOP),
                        isDisplayed()));
        appCompatButton2.perform(click());
    }

    private class TestFitnessService implements FitnessService {
        private static final String TAG = "[TestFitnessService]: ";
        private WalkInProgress walkInProgressActivity;

        public TestFitnessService(WalkInProgress walkInProgressActivity) {
            this.walkInProgressActivity = walkInProgressActivity;
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
        }
    }


}
