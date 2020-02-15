package com.example.walkwalkrevolution;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.walkwalkrevolution.ui.routes.RoutesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        SharedPreferences prefs = getSharedPreferences("prefs",  MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if(firstStart) {
            showEnterHeight();
        }

        //checking if was switched to from pressing "done"
        String stringFromPrevActivity;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                stringFromPrevActivity = null;
            } else {
                stringFromPrevActivity = extras.getString("STRING_I_NEED");
            }
        } else {
            stringFromPrevActivity = (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }

        if(stringFromPrevActivity != null && stringFromPrevActivity.equals("PRESSED DONE")){
            //switch to routes screen

            Toast.makeText(this, stringFromPrevActivity, Toast.LENGTH_SHORT).show();
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

}
