package com.example.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.FirebaseInteractor;
import com.example.walkwalkrevolution.appdata.UserData;
import com.example.walkwalkrevolution.appdata.UserID;

import java.util.MissingFormatArgumentException;

public class Register extends AppCompatActivity {
    EditText EMAIL, FIRST_NAME, LAST_NAME;
    Button register;
    ApplicationStateInteractor loginFirebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginFirebase = MainActivity.getAppDataInteractor();
        EMAIL = findViewById(R.id.register_email);
        FIRST_NAME = findViewById(R.id.register_first_name);
        LAST_NAME = findViewById(R.id.register_last_name);
        register = findViewById(R.id.register_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = EMAIL.getText().toString();
                String firstName = FIRST_NAME.getText().toString();
                String lastName = LAST_NAME.getText().toString();

                if(emailAddress.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email is required", Toast.LENGTH_SHORT).show();
                }
                else if(firstName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "First Name is required", Toast.LENGTH_SHORT).show();
                }
                else if(lastName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Last Name is required", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(loginFirebase.isUserEmailTaken(emailAddress)){
                        Toast.makeText(getApplicationContext(), "This email has been registered.", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        UserData userData = new UserData(emailAddress, "PASSWORD", firstName, lastName);
                        UserID userID = new UserID(emailAddress);
                        loginFirebase.addUserToDatabase(userID, userData);
                        loginFirebase.setLocalUserEmail(emailAddress);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }
            }
        });
    }
}
