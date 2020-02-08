package com.example.walkwalkrevolution.ui.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

import org.w3c.dom.Text;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    public Context mContext;
    TextView textSteps;

    private String fitnessServiceKey = "GOOGLE_FIT";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_Progress);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        textSteps = root.findViewById(R.id.text_Step);

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


        display(root);

//        AsyncTaskRunner runner = new AsyncTaskRunner();
//        runner.execute("0");
        final Handler r = new Handler();
        r.post(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("last_walk", MODE_PRIVATE);
                String steps = sharedPreferences.getString("steps", "");
                TextView displayTotalSteps = (TextView) root.findViewById(R.id.text_Step);
                displayTotalSteps.setText(steps);
                r.postDelayed(this, 1000);
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
        int step = 0;
        textSteps = root.findViewById(R.id.text_Step);
        Intent intent = new Intent(getActivity(), WalkInProgress.class);
        step = Integer.parseInt(textSteps.getText().toString());
        intent.putExtra(WalkInProgress.STEP_COUNT, step);
        intent.putExtra(WalkInProgress.FITNESS_SERVICE_KEY, fitnessServiceKey);
        startActivity(intent);
    }

    public void setFitnessServiceKey(String fitnessServiceKey){
        this.fitnessServiceKey = fitnessServiceKey;
    }


    public void display(View view){

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("last_walk", MODE_PRIVATE);
        String steps = sharedPreferences.getString("steps", "");
        String miles = sharedPreferences.getString("miles", "");
        String time = sharedPreferences.getString("time", "");
        TextView displaySteps = (TextView) root.findViewById(R.id.tv_last_steps);
        TextView displayMiles = (TextView) root.findViewById(R.id.tv_last_miles);
        TextView displayTime = (TextView) root.findViewById(R.id.tv_last_time);
        TextView displayTotalSteps = (TextView) root.findViewById(R.id.text_Step);
        TextView displayTotalMiles = (TextView) root.findViewById(R.id.text_Miles);
        displayTotalMiles.setText(miles+"Miles");
        displayTotalSteps.setText(steps);
        displayMiles.setText(miles+"Miles");
        displaySteps.setText(steps+"Steps");
        displayTime.setText(time + "ms");
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            try {
                int time = Integer.parseInt(params[0])*100;

                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";

            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onProgressUpdate(String... text) {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("last_walk", MODE_PRIVATE);
            String steps = sharedPreferences.getString("steps", "");
            TextView displayTotalSteps = (TextView) root.findViewById(R.id.text_Step);
            displayTotalSteps.setText(steps);
        }
    }

//    public void setStepCount(long stepCount) {
//        textSteps.setText(String.valueOf(stepCount));
//    }

}