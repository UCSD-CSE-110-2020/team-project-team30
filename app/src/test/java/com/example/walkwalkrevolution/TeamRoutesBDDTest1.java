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
import static junit.framework.TestCase.assertEquals;

@RunWith(value = AndroidJUnit4.class)
public class TeamRoutesBDDTest1 {

    private Intent intent;
    private AppCompatActivity mainActivity;
    private Fragment teamRoutesFragment;
    private Button addTeammate;
    final String GIVEN = "Given that I am on the home screen\n" +
                        "And that I have a friend on my team, Amy Zhu\n" +
                        "And that she has two routes saved on her app";
    final String WHEN = "When I tap on the the routes icon\n" +
                        "And when I select the Team Routes tab\n";
    final String THEN = "Then I see the icon for Amy Zhu\n" +
                        "And I see her two routes\n";

    private ApplicationStateInteractor appdata;

    @Before
    public void setUp() {
        System.out.println(GIVEN);
        SharedPreferences sharedPreferences =
                InstrumentationRegistry.getInstrumentation().getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("height", "65");
        editor.putString("current_user_id", "jiz546@ucsd.edu");
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
            UserData userSara = new UserData("ssmith@gmail.com", "notAdmin", "Sara", "Smith");
            UserData userJiayi = new UserData("jiz546@ucsd.edu", "sandiego", "Jiayi", "Zhang");

            UserID ariannaID = ariannaUser.getUserID();
            UserID ellenID  = userEllen.getUserID();
            UserID deonID   =  userDeondre.getUserID();
            UserID saraID   =  userSara.getUserID();
            UserID jiayiID = userJiayi.getUserID();

            TeamID teamID = new TeamID(ariannaID.toString());
            appdata.addUserToDatabase(ariannaID, ariannaUser);
            appdata.addUserToDatabase(ellenID, userEllen);
            appdata.addUserToDatabase(deonID, userDeondre);
            appdata.addUserToDatabase(saraID, userSara);
            appdata.addUserToDatabase(jiayiID, userJiayi);

            appdata.addUserToTeam(ariannaID, teamID);
            appdata.addUserToTeam(ellenID, teamID);
            appdata.addUserToTeam(deonID, teamID);
            appdata.addUserToTeam(saraID, teamID);
            appdata.addUserToTeam(jiayiID, teamID);

            Route r1 = new Route("Pepper Canyon", "01/22/2020", "PCYNH");
            Route r2 = new Route("Gilman Drive", "01/25/2020", "Starbucks PC");
            Route r3 = new Route("Warren Dorms", "01/22/2020", "PCYNH");
            Route r4 = new Route("Tecolote Canyon", "04/22/2020", "Appfolio");

            r3.setFeatureStreetTrail(2);
            r3.setFeatureFlatHilly(1);
            r3.setFeatureEven(2);
            r3.setFeatureDifficulty(3);
            r3.setFeatureLoop(1);
            r3.setNotes("Very scenic, great canyon view, dorms look nice");
            r3.setFavorite(true);

            appdata.addUserRoute(ariannaID, r1);
            appdata.addUserRoute(deonID, r2);
            appdata.addUserRoute(ellenID, r3);
            appdata.addUserRoute(saraID, r4);

            appdata.addExtraFavRoutes(saraID, r1);

            List<UserID> teamMembers = new ArrayList<>();
            teamMembers.add(deonID);
            teamMembers.add(ellenID);
            teamMembers.add(saraID);
            teamMembers.add(jiayiID);

            WalkPlan walkPlan = new WalkPlan(r3, "04/01/2020", "16:20", ariannaID, teamID, teamMembers);
            appdata.addWalkPlan(walkPlan);
            System.out.println(WHEN);
        });
    }

    @Test
    public void checkFriendsUserIDForRoutes(){
        assert(appdata.getLocalUserID() != null);
    }
}
