package com.example.walkwalkrevolution.ui.information;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.RouteStorage;

import java.util.List;

public class DescriptionFragment extends Fragment {

    private final String ROUTE_NUM = "ROUTE_NUM";
    private DescriptionViewModel mViewModel;
    private View root;
    private TextView routeName;
    private TextView routeStart;
    private TextView routeMT;
    private TextView routeFeatures;
    private TextView routeNotes;
    private Button start;

    public static DescriptionFragment newInstance() {
        return new DescriptionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(DescriptionViewModel.class);
        root = inflater.inflate(R.layout.fragment_description, container, false);

        Intent intent = getActivity().getIntent();
        int position = intent.getIntExtra(ROUTE_NUM, 0);
        List<Route> routeList = RouteStorage.getRoutes();
        Route route = routeList.get(0);
        String name = route.getName();
        String start = route.getStart();
//        String mt = route.get
        String features = "";
        /*
                String.format("loop: %d, flatHilly: %d, streetTrail: %d, even: %d, difficulty: %d}",
                route.getFeatureLoop(), route.getFeatureFlatHilly(), route.getFeatureStreetTrail(),
                route.getFeatureEven(), route.getFeatureDifficulty());
         */
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

        routeName = root.findViewById(R.id.descriptionName);
        routeStart = root.findViewById(R.id.descriptionStart);
        routeMT = root.findViewById(R.id.descriptionMilesTime);
        routeFeatures = root.findViewById(R.id.descriptionFeatures);
        routeNotes = root.findViewById(R.id.descriptionNote);
        routeName.setText(name);
        routeStart.setText(start);
        routeFeatures.setText(features);
        routeNotes.setText(note);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DescriptionViewModel.class);
        // TODO: Use the ViewModel
    }

}
