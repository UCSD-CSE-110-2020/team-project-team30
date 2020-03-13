package com.example.walkwalkrevolution.ui.team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.TeamID;
import com.example.walkwalkrevolution.appdata.UserID;

public class JoinTeamPromptActivity extends AppCompatActivity {

    public String result;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team_prompt);

        TextView message = findViewById(R.id.tv_invite_message);
        Button accInvite = findViewById(R.id.btn_acc_invite);
        Button decInvite = findViewById(R.id.btn_dec_invite);

        ApplicationStateInteractor appdata = MainActivity.getAppDataInteractor();
        UserID thisID = appdata.getLocalUserID();
        TeamID teamfromInvite = appdata.getUserTeamInviteStatus(thisID);

        accInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appdata.addUserToTeam(thisID, teamfromInvite);
                displayMessage("Successfully Join Team!");
                goToHome();
            }
        });

        decInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appdata.resetUserTeamInvite(thisID);
                displayMessage("Invitation Decline");
                goToHome();
            }
        });

    }

    public void displayMessage(String message){
        result = message;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void goToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
