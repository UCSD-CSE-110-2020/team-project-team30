package com.example.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

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
        List<Route> routeList = RouteStorage.getRoutes();
        Route route = routeList.get(position);
        String name = route.getName();
        String start = route.getStart();
        String features = "";
        if(route.getFeatureLoop() != -1){
            features = features + "loop\n";
        }
        if(route.getFeatureFlatHilly() != -1){
            features = features + "Flat Hilly\n";
        }
        if(route.getFeatureStreetTrail() != -1){
            features = features + "Street Trail\n";
        }
        if(route.getFeatureEven() != -1){
            features = features + "Even\n";
        }
        features = features + "Difficulty: " + String.valueOf(route.getFeatureDifficulty());
        String note = route.getNotes();

        routeName = findViewById(R.id.descriptionName);
        routeMT = findViewById(R.id.descriptionMilesTime);
        routeFeatures = findViewById(R.id.descriptionFeatures);
        routeNotes = findViewById(R.id.descriptionNote);
        routeName.setText(name);
        routeFeatures.setText(features);
        routeNotes.setText(note);
    }

    public void startWalk(View view) {
        Intent intent = new Intent(DescriptionActivity.this, WalkInProgress.class);
        intent.putExtra("FITNESS_SERVICE_KEY", "GOOGLE_FIT");
        startActivity(intent);
    }
}
