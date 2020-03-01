package com.example.walkwalkrevolution;

import android.content.Intent;
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

import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.UserID;
import com.example.walkwalkrevolution.appdata.WalkPlan;

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
                //Switch to WalkPlan Screen (Not Implemented yet)
                finish();
            }
        });
    }

}
