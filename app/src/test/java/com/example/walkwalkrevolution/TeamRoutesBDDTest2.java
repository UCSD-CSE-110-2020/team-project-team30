package com.example.walkwalkrevolution;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.walkwalkrevolution.appdata.MockInteractor;
import com.example.walkwalkrevolution.appdata.UserID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Context.MODE_PRIVATE;
import static junit.framework.TestCase.assertEquals;

@RunWith(value = AndroidJUnit4.class)
public class TeamRoutesBDDTest2 {

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

    @Before
    public void setUp() {
        System.out.println(GIVEN);
        SharedPreferences sharedPreferences =
                InstrumentationRegistry.getInstrumentation().getContext().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("height", "65");
        editor.apply();
        MockInteractor.dummySetEmail("myemail@gmail.com");
        MockInteractor.dummyAddTeammates(new Teammate("Amy", "Zhu", Color.LTGRAY), "Zhu@gmail.com");
        MockInteractor.dummyAddUserRoute(new UserID("Zhu@gmail.com"), new Route("Geisel", "01/01/2020", "gate"));

        intent = new Intent(ApplicationProvider.getApplicationContext(), MainActivity.class);
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            mainActivity = activity;
            System.out.println(WHEN);
        });
    }

    @Test
    public void checkNonExistingFriendsUserIDForRoutes(){
        //We were unable to finish the last test!
        //assertEquals("", MockInteractor.dummyGetTeammateEmail("nonexisting@gamil.com"));
    }
}