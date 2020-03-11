package com.example.walkwalkrevolution.ui.team;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.walkwalkrevolution.R;

public class AddTeammatePromptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teammate_prompt);
        findViewById(R.id.prompt).setVisibility(View.VISIBLE);
        ImageView sendInvite = (ImageView) findViewById(R.id.imageView_send);
        sendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allRequiredInfo = checkAllRequiredInfo();
                if(allRequiredInfo) {
                    //ADD CODE HERE FOR OBTAINING INFO INPUTTED AND STORING IN FIREBASE!!!
                    setAnimation(sendInvite);
                    EditText firstNameEditText = (EditText) findViewById(R.id.editText_first_name);
                    String firstName = firstNameEditText.getText().toString();
                    Log.d("Invitation sent to", firstName);
                    findViewById(R.id.prompt).setVisibility(View.GONE);
                    displayMessage("Invited " + firstName + "!", v);
                    finish();
                }
                else{
                    displayMessage("First name and email are required", v);
                }
            }
        });
    }

    public boolean checkAllRequiredInfo(){
        EditText firstNameEditText = findViewById(R.id.editText_first_name);
        EditText gmailEditText = findViewById(R.id.editText_gmail);
        String firstNameInputText = firstNameEditText.getText().toString();
        String gmailInputText = gmailEditText.getText().toString();
        if(firstNameInputText.trim().isEmpty() || gmailInputText.trim().isEmpty()){
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
