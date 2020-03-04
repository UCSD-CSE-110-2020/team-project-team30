package com.example.walkwalkrevolution.ui.WalkPlan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.UserID;
import com.example.walkwalkrevolution.appdata.WalkPlan;
import com.example.walkwalkrevolution.ui.WalkPlan.WalkPlanFragment;
import com.example.walkwalkrevolution.ui.information.NotesFragment;
import com.example.walkwalkrevolution.ui.information.NotesViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProposeWalkPromptActivity extends AppCompatActivity {
    String date;
    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose_walk_prompt);
        TextView name = findViewById(R.id.tv_propose_name);
        EditText timeText = findViewById(R.id.text_time_propose);

        Intent intent = getIntent();
        String routeName = intent.getStringExtra("route name");
        name.setText(routeName);

        CalendarView calender = findViewById(R.id.calendarView2);
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = "Date is : " + dayOfMonth +" / " + month + " / " + year;
            }
        });

        Button send = findViewById(R.id.btn_send_propose);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set up a new WalkPlan
                time = timeText.getText().toString();
                //How to get the currentRoute?
                //WalkPlan newPlan = new WalkPlan(route, date, time, new UserID("jiz546@ucsd.edu"));
                //Put it into firebase
                //ApplicationStateInteractor appdata = null;
                SharedPreferences sharedPreferences = getSharedPreferences("propose", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("proposewalk", true);
                editor.apply();
                //Switch to WalkPlan Screen (Not Implemented yet)
                launchWalkPlan();
            }
        });
    }

    public void launchWalkPlan(){
        //WalkPlanFragment fragment = WalkPlanFragment.newInstance();
        //WalkPlanFragment fragment = new WalkPlanFragment();
        //getSupportFragmentManager().beginTransaction().add(R.id.activity_propose_walk_prompt, fragment, WalkPlanFragment.TAG).commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
