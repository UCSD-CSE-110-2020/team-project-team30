package com.example.walkwalkrevolution;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.walkwalkrevolution.appdata.FirebaseInteractor;
import com.example.walkwalkrevolution.appdata.MockInteractor;
import com.google.firebase.FirebaseApp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.os.Looper.getMainLooper;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;


@RunWith(value = AndroidJUnit4.class)
public class LoginTest {
    private Intent intent;
    ActivityScenario<Login> scenario;
    @Before
    public void setUp(){
//        shadowOf(getMainLooper()).idle();
        intent = new Intent(ApplicationProvider.getApplicationContext(), Login.class);
    }
    @Test
    public void onCreate() {
        scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            activity.loginFirebase = new MockInteractor();
//            FirebaseApp.initializeApp(activity);
//            activity.loginFirebase = new FirebaseInteractor(activity);
            EditText loginEmail = activity.findViewById(R.id.login_email);
            Button loginLogin = activity.findViewById(R.id.login_login);
            loginEmail.setText("For test purpose");
            loginLogin.performClick();
            assertEquals(activity.message, "Email is not correct.");
        });
    }
    @After
    public void des(){
        scenario.close();
    }
}