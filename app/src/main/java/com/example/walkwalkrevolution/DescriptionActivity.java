package com.example.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.walkwalkrevolution.ui.WalkPlan.ProposeWalkPromptActivity;

public class DescriptionActivity extends AppCompatActivity {

    private final String ROUTE_NUM = "ROUTE_NUM";
    private TextView routeName;
    private TextView routeStart;
    private TextView routeMT;
    private TextView routeFeatures;
    private TextView routeNotes;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();
        position = intent.getIntExtra(ROUTE_NUM, 0);
        String name = intent.getStringExtra("route name");
        //List<Route> routeList = RouteStorage.getRoutes();
        //Route route = routeList.get(position);

        routeName = findViewById(R.id.descriptionName);
        routeName.setText(name);

        ImageView startButton = findViewById(R.id.imageView_start);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startWalk(routeName.getText().toString());
                startButton.setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY );
            }
        });

        Button proposeButton = findViewById(R.id.btn_propose);
        proposeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                launchProposeActivity();
            }
        });

        startButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.nav_default_enter_anim));
    }

    public void startWalk(String routeName) {
        Intent intent = new Intent(DescriptionActivity.this, WalkInProgress.class);
        intent.putExtra("FITNESS_SERVICE_KEY", "GOOGLE_FIT");
        intent.putExtra("route name", routeName);
        intent.putExtra("skip route", false);
        intent.putExtra("route exists", true);
        Toast.makeText(this, "Starting: " + routeName, Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    public void launchProposeActivity(){
        Intent intent = new Intent(this, ProposeWalkPromptActivity.class);
        intent.putExtra("route name", routeName.getText().toString());
        startActivity(intent);
    }
}
