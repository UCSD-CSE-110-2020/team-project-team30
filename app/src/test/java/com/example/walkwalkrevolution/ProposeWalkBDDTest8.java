package com.example.walkwalkrevolution;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.walkwalkrevolution.appdata.WalkPlan;
import com.google.android.material.tabs.TabLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.truth.Truth.assertThat;
import static junit.framework.TestCase.assertEquals;

@RunWith(value = AndroidJUnit4.class)
  public class ProposeWalkBDDTest8 {

    private UserID ariannaID;
    private UserID ellenID;
    private UserID deonID;
    private UserID saraID;
    private TeamID teamID;
    private Route r4;

    private Intent intent;
    private AppCompatActivity mainActivity;
    private Fragment teamRoutesFragment;
    private Button addTeammate;

    private ApplicationStateInteractor appdata;

    @Before
    public void setUp() {
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

        Route r1 = new Route("Gliderport Loop", "01/22/2020", "The Village");
        r4 = new Route("Tecolote Canyon", "04/22/2020", "Geisel");

        appdata.addUserRoute(ariannaID, r1);
        appdata.addUserRoute(saraID, r4);
      });
    }

    @Test
    public void testProposeWalk(){
      List<UserID> teamMembers = new ArrayList<>();
      teamMembers.add(deonID);
      teamMembers.add(ellenID);
      teamMembers.add(saraID);

      assertThat(appdata.getWalkPlanExists(teamID)).isFalse();

      WalkPlan walkPlan = new WalkPlan(r4, "04/01/2020", "16:20", ariannaID, teamID, teamMembers);
      appdata.addWalkPlan(walkPlan);

      assertThat(appdata.getWalkPlanExists(teamID)).isTrue();
    }
  }
