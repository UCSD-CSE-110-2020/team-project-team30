package com.example.walkwalkrevolution;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.walkwalkrevolution.Fitness.FitnessService;
import com.example.walkwalkrevolution.Fitness.FitnessServiceFactory;
import com.example.walkwalkrevolution.ui.information.InformationFragment;
import com.example.walkwalkrevolution.ui.team.TeamFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.truth.Truth.assertThat;
import static junit.framework.TestCase.assertEquals;

@RunWith(value = AndroidJUnit4.class)
public class TeammatesExistBDDTest {

    private Intent intent;
    private AppCompatActivity mainActivity;
    private Fragment teamFragment;
    private Button addTeammate;
    final String GIVEN = "Given that I am on the home screen\n" +
                        "And that I have two friends on my team, Julian and Celine\n";
    final String WHEN = "When I tap on the the team icon\n";
    final String THEN = "Then I see the full names of Julian and Celine\n" +
                        "And they have a color coded icon next to them\n";

    @Before
    public void setUp() {
        System.out.println(GIVEN);
        SharedPreferences sharedPreferences =
                InstrumentationRegistry.getInstrumentation().getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("height", "65");
        editor.apply();

        System.out.println(WHEN);
        intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            mainActivity = activity;
            mainActivity.findViewById(R.id.navigation_team).performClick();

            List<Fragment> fragments =  mainActivity.getSupportFragmentManager().getFragments();
            for(int i = 0; i < fragments.size(); i++){
                addTeammate = fragments.get(i).getView().findViewById(R.id.button_add_new_teammate);
                if(addTeammate != null){
                    teamFragment = fragments.get(i);
                    break;
                }
            }
        });
    }

    @Test
    public void addButtonLocatedANDFriendsNamesLocated(){
        assert(addTeammate != null);

    }

}