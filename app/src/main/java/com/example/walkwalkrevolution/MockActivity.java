package com.example.walkwalkrevolution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.walkwalkrevolution.ui.home.HomeFragment;

import java.util.Calendar;

public class MockActivity extends AppCompatActivity{
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mock);

            TextView textSteps = findViewById(R.id.text_Step);
            EditText mockTime = findViewById(R.id.setTime);

            //Botton to mock total steps on the screen
            Button btn_MockStep = findViewById(R.id.MockSteps);
            btn_MockStep.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    /*Intent intent = new Intent(MockActivity.this, HomeFragment.class);
                    intent.putExtra("Extra Step", 500);
                    startActivity(intent);*/
                    String str = textSteps.getText().toString();
                    String[] content = str.split(" ");
                    int currentTotal = Integer.valueOf(content[0]);
                    textSteps.setText(String.valueOf(currentTotal+500));
                }
            });

            //Button to mock time for testing purpose
            Button btn_MockTime = findViewById(R.id.Submit);
            btn_MockTime.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    long fakeTime = Long.valueOf(mockTime.getText().toString());
                    Calendar mockCal = new MockTime(fakeTime);
                }
            });



        }

}
