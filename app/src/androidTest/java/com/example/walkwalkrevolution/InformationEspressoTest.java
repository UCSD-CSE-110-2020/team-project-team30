package com.example.walkwalkrevolution;

import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;

import androidx.test.espresso.Root;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.walkwalkrevolution.Fitness.FitnessService;
import com.example.walkwalkrevolution.Fitness.FitnessServiceFactory;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class InformationEspressoTest {

    private static final String TEST_SERVICE = "TEST_SERVICE";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //Test transition to enter inforamtion with no entering information
    @Test
    public void EnterInformationEspressoTest() {
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(WalkInProgress walkInProgressActivity) {
                return new InformationEspressoTest.TestFitnessService(walkInProgressActivity);
            }
        });

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_GoToWalk), withText("Go To Walk"),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction button2 = onView(
                allOf(withId(R.id.btn_STOP), withText("STOP"),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_STOP),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction button3 = onView(
                allOf(withId(R.id.button_done),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_done),
                        isDisplayed()));
        appCompatButton3.perform(click());

        onView(withText("Route name is required")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button_enter_more_info),
                        isDisplayed()));
        appCompatButton4.perform(click());

        onView(withText("Route name is required")).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    //Test transition to enter inforamtion with entering information
    @Test
    public void EnterMoreFeatureEspressoTest() {
        FitnessServiceFactory.put(TEST_SERVICE, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(WalkInProgress walkInProgressActivity) {
                return new InformationEspressoTest.TestFitnessService(walkInProgressActivity);
            }
        });

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btn_GoToWalk), withText("Go To Walk"),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btn_STOP),
                        isDisplayed()));
        appCompatButton2.perform(click());


        ViewInteraction textView = onView(
                allOf(withId(R.id.editText_route_name),
                        isDisplayed()));
        textView.perform(click(),
                replaceText("PseudoRoute"));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_enter_more_info),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction imageView = onView(
                allOf(withId(R.id.imageView_next),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));

        ViewInteraction radioButton = onView(
                allOf(withId(R.id.radioButton_easy),
                        isDisplayed()));
        radioButton.perform(click());

        ViewInteraction radioButton2 = onView(
                allOf(withId(R.id.radioButton_flat),
                        isDisplayed()));
        radioButton2.perform(click());

        ViewInteraction radioButton3 = onView(
                allOf(withId(R.id.radioButton_trail),
                        isDisplayed()));
        radioButton3.perform(click());

        ViewInteraction radioButton4 = onView(
                allOf(withId(R.id.radioButton_loop),
                        isDisplayed()));
        radioButton4.perform(click());

        ViewInteraction radioButton5 = onView(
                allOf(withId(R.id.radioButton_uneven),
                        isDisplayed()));
        radioButton5.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.imageView_next),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction radioButton6 = onView(
                allOf(withId(R.id.imageView_star_filled),
                        isDisplayed()));
        radioButton6.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.imageView_next),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction button = onView(
                allOf(withId(R.id.button_done),
                        isDisplayed()));
        button.perform(click());

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

    public class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    return true;
                }
            }
            return false;
        }
    }

}
