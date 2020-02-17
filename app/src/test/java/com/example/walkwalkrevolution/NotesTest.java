package com.example.walkwalkrevolution;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.walkwalkrevolution.Fitness.FitnessService;
import com.example.walkwalkrevolution.Fitness.FitnessServiceFactory;
import com.example.walkwalkrevolution.ui.information.FavoriteFragment;
import com.example.walkwalkrevolution.ui.information.FeaturesFragment;
import com.example.walkwalkrevolution.ui.information.InformationFragment;
import com.example.walkwalkrevolution.ui.information.NotesFragment;

import org.apache.tools.ant.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.truth.Truth.assertThat;
import static junit.framework.TestCase.assertEquals;

@RunWith(value = AndroidJUnit4.class)
public class NotesTest {
    private static final String TEST_SERVICE = "TEST_SERVICE";

    private Intent intent;
    private long nextStepCount;
    private long nextMile;
    private AppCompatActivity walkActivity;
    private InformationFragment infoFragment;
    private FeaturesFragment featuresFragment;
    private FavoriteFragment favoriteFrag;
    private Button stopButton;
    private NotesFragment notesFragment;

    @Before
    public void setUp() {
        FitnessServiceFactory.put(TEST_SERVICE, TestFitnessService::new);
        intent = new Intent(ApplicationProvider.getApplicationContext(), WalkInProgress.class);
        intent.putExtra(WalkInProgress.FITNESS_SERVICE_KEY, TEST_SERVICE);

        SharedPreferences sharedPreferences =
                InstrumentationRegistry.getInstrumentation().getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("height", "65");
        editor.apply();

        //going into the new walk or route
        ActivityScenario<WalkInProgress> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            walkActivity = activity;
            stopButton = activity.findViewById(R.id.btn_STOP);
            stopButton.performClick();
            infoFragment =
                    (InformationFragment) walkActivity.getSupportFragmentManager().findFragmentByTag("INFO FRAG");
            EditText routeName = infoFragment.getView().findViewById(R.id.editText_route_name);
            routeName.setText("TEST ROUTE");
            Button enterMoreInfo = infoFragment.getView().findViewById(R.id.button_enter_more_info);
            enterMoreInfo.performClick();
            featuresFragment =
                    (FeaturesFragment) walkActivity.getSupportFragmentManager().findFragmentByTag("FEATURES FRAG");
            clickAllTheButtons();
            favoriteFrag =
                    (FavoriteFragment) walkActivity.getSupportFragmentManager().findFragmentByTag("FAVORITE FRAG");
            ImageView filledStar = favoriteFrag.getView().findViewById(R.id.imageView_star_filled);
            filledStar.performClick();

            ImageView nextButton = favoriteFrag.getView().findViewById(R.id.imageView_next);
            nextButton.performClick();

            notesFragment =
                    (NotesFragment) walkActivity.getSupportFragmentManager().findFragmentByTag("NOTES FRAG");

        });
    }

    @Test
    public void favoriteFragmentWasCreated(){
        assertEquals(false, notesFragment == null);
    }

    @Test
    public void textAppearsInTextBox(){
        EditText notesBox = notesFragment.getView().findViewById(R.id.editText_routeNotes);
        notesBox.setText("This is some text");
        assertEquals(notesBox.getText().toString(), "This is some text");
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

    private void clickAllTheButtons(){
        RadioGroup firstGroup = (RadioGroup) featuresFragment.getView().findViewById(R.id.rg_loop);
        RadioGroup secondGroup = (RadioGroup) featuresFragment.getView().findViewById(R.id.rg_flatHilly);
        RadioGroup thirdGroup = (RadioGroup) featuresFragment.getView().findViewById(R.id.rg_streetTrail);
        RadioGroup fourthGroup = (RadioGroup) featuresFragment.getView().findViewById(R.id.rg_even);
        RadioGroup fifthGroup = (RadioGroup) featuresFragment.getView().findViewById(R.id.rg_difficulty);
        ArrayList<RadioGroup> groups = new ArrayList<RadioGroup>();
        groups.add(firstGroup);
        groups.add(secondGroup);
        groups.add(thirdGroup);
        groups.add(fourthGroup);
        groups.add(fifthGroup);
        for(int index = 0; index < groups.size(); index++){
            RadioGroup currGroup = groups.get(index);
            RadioButton currFirstRadio = (RadioButton) currGroup.getChildAt(0);
            currFirstRadio.performClick();
        }
        ImageView nextButton = featuresFragment.getView().findViewById(R.id.imageView_next);
        nextButton.performClick();
    }

}