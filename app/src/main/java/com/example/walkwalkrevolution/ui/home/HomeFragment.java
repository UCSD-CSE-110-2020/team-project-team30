package com.example.walkwalkrevolution.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.Fitness.FitnessService;
import com.example.walkwalkrevolution.Fitness.FitnessServiceFactory;
import com.example.walkwalkrevolution.Fitness.GoogleFitAdapter;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.ui.WalkInProgress;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private String fitnessServiceKey = "GOOGLE_FIT";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_Progress);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button btn_GoToWalk = root.findViewById(R.id.btn_GoToWalk);
        btn_GoToWalk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                launchActivity();
            }
        });

        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(WalkInProgress walkInProgress) {
                return new GoogleFitAdapter(walkInProgress);
            }
        });

        return root;
    }

    /*public void update(Observable o, Object arg){
        stepCount = (long) arg;
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                textSteps.setText(String.valueOf(stepCount));
            }
        });
    }*/

    private void launchActivity() {
        Intent intent = new Intent(getActivity(), WalkInProgress.class);
        intent.putExtra(WalkInProgress.FITNESS_SERVICE_KEY, fitnessServiceKey);
        startActivity(intent);
    }

    public void setFitnessServiceKey(String fitnessServiceKey){
        this.fitnessServiceKey = fitnessServiceKey;
    }

}