package com.example.walkwalkrevolution;


import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.MockInteractor;
import com.example.walkwalkrevolution.appdata.TeamID;
import com.example.walkwalkrevolution.appdata.UserData;
import com.example.walkwalkrevolution.appdata.UserID;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

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

    private UserID ariannaID;
    private UserID ellenID;
    private UserID deonID;
    private UserID saraID;
    private TeamID teamID;
    private Route r4;
    private ApplicationStateInteractor appdata;

    @Before
    public void setUp() {
        System.out.println(GIVEN);
        SharedPreferences sharedPreferences =
                InstrumentationRegistry.getInstrumentation().getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("height", "65");
        editor.putString("current_user_id", "amartinez@gmail.com");
        editor.apply();

        intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        intent.putExtra(MainActivity.INTENT_KEY_FIREBASE_INTERACTOR, MainActivity.INTENT_USE_MOCK_INTERACTOR);

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            mainActivity = activity;
            appdata = ((MainActivity) mainActivity).getAppDataInteractor();

            UserData ariannaUser = new UserData("amartinez@gmail.com", "notAdmin", "Arianna", "Martinez");
            UserData userEllen   = new UserData("eanderson@gmail.com", "notAdmin", "Ellen", "Anderson");
            UserData userDeondre = new UserData("dwilliams@gmail.com", "notAdmin", "Deondre", "Williams");
            UserData userSara    = new UserData("ssmith@gmail.com", "notAdmin", "Sara", "Smith");

            ariannaID = ariannaUser.getUserID();
            ellenID   = userEllen.getUserID();
            deonID    =  userDeondre.getUserID();
            saraID    =  userSara.getUserID();

            teamID = new TeamID(ariannaID.toString());
            appdata.addUserToDatabase(ariannaID, ariannaUser);
            appdata.addUserToDatabase(ellenID, userEllen);
            appdata.addUserToDatabase(deonID, userDeondre);
            appdata.addUserToDatabase(saraID, userSara);

            appdata.addUserToTeam(ariannaID, teamID);
            appdata.addUserToTeam(ellenID, teamID);
            appdata.addUserToTeam(deonID, teamID);
            appdata.addUserToTeam(saraID, teamID);

            BottomNavigationItemView button = mainActivity.findViewById(R.id.navigation_team);

            setTeamFragment();
            System.out.println(WHEN);
        });
    }

    @Test
    public void addButtonLocated_NoTeammatesMessageDisplayed(){
        String noTeammatesText = "No Teammates";
        if (teamFragment != null) {
            TextView noTeammatesTextView = teamFragment.getView().findViewById(R.id.textView_no_teammates);
            noTeammatesText = noTeammatesTextView.getText().toString();
        }
        assertEquals("No Teammates", noTeammatesText);
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