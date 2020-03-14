package com.example.walkwalkrevolution;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.FirebaseInteractor;
import com.example.walkwalkrevolution.appdata.MockInteractor;
import com.google.firebase.FirebaseApp;

import org.apache.tools.ant.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.os.Looper.getMainLooper;
import static androidx.lifecycle.Lifecycle.State.DESTROYED;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;


@RunWith(value = AndroidJUnit4.class)
public class LoginTest {
    private Intent intent;
    ActivityScenario<Login> scenario;
    @Before
    public void setUp(){
        intent = new Intent(ApplicationProvider.getApplicationContext(), Login.class);
        //Change the app into test Mode before the test
        MainActivity.changeTheTestMode();
    }
    @Test
    public void onCreate() {
        List<String> events = new ArrayList<>();
        events.add("before");
        new Handler(Looper.getMainLooper()).post(() -> events.add("after"));
        events.add("between");

        scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            // Initialize the firebase and get the instance
            FirebaseApp.initializeApp(activity);
            activity.loginFirebase = new FirebaseInteractor(activity);

            // Do the test
            EditText loginEmail = activity.findViewById(R.id.login_email);
            Button loginLogin = activity.findViewById(R.id.login_login);
            loginEmail.setText("For test purpose");
            loginLogin.performClick();
            FirebaseApp.clearInstancesForTest();
            assertEquals(activity.message, "Email is not correct.");
        });
        scenario.moveToState(DESTROYED);
    }
    @After
    public void des(){
        MainActivity.changeTheTestMode();
        scenario.close();
    }
}