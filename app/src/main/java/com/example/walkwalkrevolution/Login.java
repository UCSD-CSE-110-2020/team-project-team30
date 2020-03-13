package com.example.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.FirebaseInteractor;
import com.google.firebase.FirebaseApp;

public class Login extends AppCompatActivity {
    EditText loginEmail;
    Button loginLogin;
    TextView loginCreate;
    ApplicationStateInteractor loginFirebase;
    public String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.login_email);
        loginLogin = findViewById(R.id.login_login);
        loginCreate = findViewById(R.id.login_create);

        loginFirebase = MainActivity.getAppDataInteractor();

        loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email is required", Toast.LENGTH_SHORT).show();
                    message = "Email is required";
                }
                else{
                    if(loginFirebase.isUserEmailTaken(email)){
                        message = "Logged in!";
                        loginFirebase.setLocalUserEmail(email);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Email is not correct.", Toast.LENGTH_SHORT).show();
                        message = "Email is not correct.";
                    }
                }
            }
        });

        loginCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
}
