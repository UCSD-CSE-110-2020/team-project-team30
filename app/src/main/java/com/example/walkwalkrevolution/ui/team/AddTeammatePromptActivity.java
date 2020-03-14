package com.example.walkwalkrevolution.ui.team;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.TeamID;
import com.example.walkwalkrevolution.appdata.UserID;
import com.google.firebase.firestore.auth.User;

public class AddTeammatePromptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teammate_prompt);
        findViewById(R.id.prompt).setVisibility(View.VISIBLE);
        ImageView sendInvite = (ImageView) findViewById(R.id.imageView_send);
        EditText gmailEditText = findViewById(R.id.editText_gmail);
        EditText lastName = findViewById(R.id.editText_last_name);
        EditText firstName = findViewById(R.id.editText_first_name);
        lastName.setVisibility(View.GONE);
        firstName.setVisibility(View.GONE);
        sendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allRequiredInfo = checkAllRequiredInfo();
                if(allRequiredInfo) {
                    ApplicationStateInteractor appdata = MainActivity.getAppDataInteractor();
                    if(!appdata.isUserEmailTaken(gmailEditText.getText().toString())){
                        displayMessage("The User does not exist!", v);
                    }
                    else{
                        String email = gmailEditText.getText().toString();
                        UserID otherID = new UserID(email);
                        UserID myID = appdata.getLocalUserID();
                        TeamID myTeam = appdata.getUsersTeamID(myID);
                        TeamID otherTeam = appdata.getUsersTeamID(otherID);
                        if(myID.equals(otherID)){
                            displayMessage("Don't invite yourself!", v);
                        }
                        if(myTeam == null && otherTeam != null){
                            displayMessage("User already have team!", v);
                        }
                        if((myTeam != null) && (otherTeam != null)){
                            if(!(myTeam.toString().equals(otherTeam.toString()))) {
                                displayMessage("User already have team!", v);
                            }else{
                                displayMessage("User already in your team!", v);
                            }
                        }
                        //If I don't have team and other don't have team, set id to my team and add
                        if(myTeam == null && otherTeam == null && !myID.equals(otherID)){
                            myTeam = new TeamID(appdata.getLocalUserID().toString());
                            appdata.getUserData(myID).setTeamID(myTeam);
                            appdata.addUserToTeam(myID, myTeam);
                            appdata.inviteUserToTeam(otherID, myTeam);
                            setAnimation(sendInvite);
                            findViewById(R.id.prompt).setVisibility(View.GONE);
                            displayMessage("Invited " + email + "!", v);
                            finish();
                        }
                        if(myTeam != null && otherTeam == null) {
                            appdata.inviteUserToTeam(new UserID(email), myTeam);
                            setAnimation(sendInvite);
                            findViewById(R.id.prompt).setVisibility(View.GONE);
                            displayMessage("Invited " + email + "!", v);
                            finish();
                        }
                    }
                }
                else{
                    displayMessage("Email are required", v);
                }
            }
        });
    }

    public boolean checkAllRequiredInfo(){
        EditText gmailEditText = findViewById(R.id.editText_gmail);
        String gmailInputText = gmailEditText.getText().toString();
        if(gmailInputText.trim().isEmpty()){
            return false;
        }
        return true;
    }

    public void displayMessage(String message, View v){
        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void setAnimation(ImageView widget){
        widget.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.nav_default_enter_anim));
    }
}
