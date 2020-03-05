package com.example.walkwalkrevolution;


import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.walkwalkrevolution.appdata.MockInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static junit.framework.TestCase.assertEquals;

@RunWith(value = AndroidJUnit4.class)
public class TeammatesExistBDDTest2 {

    private Intent intent;
    private AppCompatActivity mainActivity;
    private Fragment teamFragment;
    private Button addTeammate;
    final String GIVEN = "Given that I am on the home screen\n" +
                        "And that I don't have anyone on my team\n";
    final String WHEN = "When I tap on the the team icon\n";
    final String THEN = "Then I am displayed No Teammates\n";

    @Before
    public void setUp() {
        System.out.println(GIVEN);
        SharedPreferences sharedPreferences =
                InstrumentationRegistry.getInstrumentation().getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("height", "65");
        editor.apply();

        intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            mainActivity = activity;
            mainActivity.findViewById(R.id.navigation_team).performClick(); //click "team" icon
            setTeamFragment();
            System.out.println(WHEN);
        });
    }

    @Test
    public void addButtonLocated_NoTeammatesMessageDisplayed(){
        assert(addTeammate != null);

        TextView noTeammatesTextView = teamFragment.getView().findViewById(R.id.textView_no_teammates);
        String noTeammatesText = noTeammatesTextView.getText().toString();
        assertEquals("No Teammates", noTeammatesText);
        assertEquals(View.VISIBLE, noTeammatesTextView.getVisibility());
        System.out.println(THEN);

    }

    public void setTeamFragment(){
        List<Fragment> fragments =  mainActivity.getSupportFragmentManager().getFragments();
        for(int i = 0; i < fragments.size(); i++){
            addTeammate = fragments.get(i).getView().findViewById(R.id.button_add_new_teammate);
            if(addTeammate != null){
                teamFragment = fragments.get(i);
                break;
            }
        }
    }

}