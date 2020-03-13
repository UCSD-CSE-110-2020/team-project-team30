package com.example.walkwalkrevolution.ui.WalkPlan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.TeamID;
import com.example.walkwalkrevolution.appdata.UserID;
import com.example.walkwalkrevolution.appdata.WalkPlan;

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

        ApplicationStateInteractor appdata = MainActivity.getAppDataInteractor();
        UserID proposer = appdata.getLocalUserID();
        TeamID teamID = new TeamID(appdata.getUsersTeamID(proposer).toString());

        Button send = findViewById(R.id.btn_send_propose);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!appdata.getWalkPlanExists(teamID)) {
                    time = timeText.getText().toString();
                    Route selectedRoute = new Route(routeName, date, routeName);
                    WalkPlan newPlan = new WalkPlan(selectedRoute, date, time, proposer, teamID, appdata.getTeamMemberIDs(proposer));
                    MainActivity.getAppDataInteractor().addWalkPlan(newPlan);
                }else{
                    Toast.makeText(getApplicationContext(), "There's already a planned Walk!", Toast.LENGTH_SHORT).show();
                }
                goToHome();
            }
        });
    }

    public void goToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
