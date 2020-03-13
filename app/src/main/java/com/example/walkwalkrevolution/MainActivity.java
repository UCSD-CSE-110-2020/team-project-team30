package com.example.walkwalkrevolution;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.FirebaseInteractor;
import com.example.walkwalkrevolution.appdata.TeamID;
import com.example.walkwalkrevolution.appdata.UserData;
import com.example.walkwalkrevolution.appdata.UserID;
import com.example.walkwalkrevolution.ui.team.JoinTeamPromptActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;


public class MainActivity extends AppCompatActivity{

    private static ApplicationStateInteractor appdata;
    private static UserData thisUser;
    private static UserID thisID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_team, R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart) {
            showEnterHeight();
        }

        //checking if was switched to from pressing "done"
        String stringFromPrevActivity;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                stringFromPrevActivity = null;
            } else {
                stringFromPrevActivity = extras.getString("routeSaved");
            }
        } else {
            stringFromPrevActivity = (String) savedInstanceState.getSerializable("routeSaved");
        }

        if (stringFromPrevActivity != null) {
            Toast.makeText(this, stringFromPrevActivity, Toast.LENGTH_SHORT).show();
            Log.d("MainActivity", "Entered mainActivity from Done button");
        } else {
            Log.d("MainActivity", "First time in MainActivity, initializing AppData");

            RouteStorage.init(this.getApplicationContext());

            // This method is for testing only during development. Remove in production
            addDefaultRoutesToRouteStorage();

            FirebaseApp.initializeApp(this);
            appdata = new FirebaseInteractor(this.getApplicationContext());

            // TODO When the user logs in, that's what should dictate the current_user_id field
            if (appdata.getLocalUserEmail() == null) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            } else {
                Log.d("Login User: ", appdata.getLocalUserEmail());
            }
        }
    }

    private void showEnterHeight(){
        EditText input = new EditText(this);
        input.setHint("Inches in whole number");
        new AlertDialog.Builder(this)
                .setTitle("Enter Height")
                .setMessage("The information is obtained to accurately calculate your steps.")
                .setView(input)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("height", input.getText().toString());
                        editor.putBoolean("firstStart", false);
                        editor.apply();
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    /**
     * This is called as a testing method to give some initial routes for testing purposes.
     * Remove manually when pushing to production
     */
    private void addDefaultRoutesToRouteStorage() {
        Log.d("MainActivity", "Populating RouteStorage with default routes inside MainActivity onCreate");
        List<Route> routeList = RouteStorage.getRoutes();

        // Only initialize these if the list of routes is empty
        if (routeList.size() == 0) {
            Log.d("MainActivity", "Populating RouteStorage with default routes inside MainActivity onCreate");

            Route route2 = new Route("GliderPort Beach", "02/04/2020", "The Village");
            Route route1 = new Route("Birmingham Park", "02/07/2020", "George house");
            Route route3 = new Route("Arlington Park", "02/03/2020", "Julian house");

            RouteStorage.addRoute(route1);
            RouteStorage.addRoute(route2);
            RouteStorage.addRoute(route3);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static ApplicationStateInteractor getAppDataInteractor() {
        return appdata;
    }
}
