package com.example.walkwalkrevolution.ui.team;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;

import java.time.Duration;

//Activity only appear for the invited person(link to addteammatepromptactivity
public class JoinTeamPromptActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_team_prompt);

        Button accInvite = findViewById(R.id.btn_acc_invite);
        Button decInvite = findViewById(R.id.btn_dec_invite);

        accInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send it to firebase so it will show up in the team
                displayMessage("Successfully Join Team!");
                goToHome();
            }
        });

        decInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send it to firebase so it won't show up in the team
                displayMessage("Invitation Decline");
                goToHome();

            }
        });

    }

    public void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void goToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
