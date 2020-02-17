package com.example.walkwalkrevolution.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.Fitness.FitnessService;
import com.example.walkwalkrevolution.Fitness.FitnessServiceFactory;
import com.example.walkwalkrevolution.Fitness.GoogleFitAdapter;
import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.WalkInProgress;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private boolean mocking;
    private View root;
    public Context mContext;
    TextView textSteps;
    TextView mockSteps;
    TextView textMiles;
    boolean inMock = false;
    boolean firstClickMock = true;

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

        mocking = getActivity().getIntent().getBooleanExtra("mocking", false);
        textSteps = root.findViewById(R.id.text_Step);
        textMiles = root.findViewById(R.id.text_Miles);
        mockSteps = root.findViewById(R.id.btn_mockStep);
        mockSteps.setVisibility(View.GONE);

        Button btn_GoToWalk = root.findViewById(R.id.btn_GoToWalk);
        btn_GoToWalk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!inMock) {
                    launchActivity();
                }else {
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences("mock", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("totalSteps", textSteps.getText().toString());
                    editor.putString("totalMiles", textMiles.getText().toString());
                    editor.apply();
                    Intent mockIntent = new Intent(getActivity(), WalkInProgress.class);
                    mockIntent.putExtra("mockStatus", inMock);
                    startActivity(mockIntent);
                }
            }
        });

        Button btn_mock = root.findViewById(R.id.btn_goToMock);
        btn_mock.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if(firstClickMock) {
                    inMock = true;
                    mockSteps.setVisibility(View.VISIBLE);
                    firstClickMock = false;
                    Toast.makeText(mContext, "Mocking enabled", Toast.LENGTH_SHORT).show();
                } else{
                    inMock = false;
                    mockSteps.setVisibility(View.GONE);
                    firstClickMock = true;
                    Toast.makeText(mContext, "Mocking disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mockSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = textSteps.getText().toString();
                int total = Integer.valueOf(str.split(" ")[0]);
                textSteps.setText(total+500+" Steps");
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("prefs", MODE_PRIVATE);
                String heightStr = sharedPreferences.getString("height", "");
                int height = Integer.valueOf(heightStr);
                double stride = (height*0.413)/63360;
                double result = ((total+500)*stride);
                textMiles.setText(String.format("%.2f", result) + " Miles");
            }
        });

        FitnessServiceFactory.put(fitnessServiceKey, new FitnessServiceFactory.BluePrint() {
            @Override
            public FitnessService create(WalkInProgress walkInProgress) {
                return new GoogleFitAdapter(walkInProgress);
            }
        });

        if(!mocking) {
            display(root);
        } else{
            displayMock(root);
        }

        return root;
    }

    private void launchActivity() {
        Intent intent = new Intent(getActivity(), WalkInProgress.class);
        intent.putExtra(WalkInProgress.FITNESS_SERVICE_KEY, fitnessServiceKey);
        startActivity(intent);
    }

    public void setFitnessServiceKey(String fitnessServiceKey){
        this.fitnessServiceKey = fitnessServiceKey;
    }


    public void display(View view){

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("last_walk", MODE_PRIVATE);
        String steps = sharedPreferences.getString("steps", "0");
        String miles = sharedPreferences.getString("miles", "0.00");
        String time = sharedPreferences.getString("time", "0");

        TextView displayTotalSteps = (TextView) root.findViewById(R.id.text_Step);
        TextView displayTotalMiles = (TextView) root.findViewById(R.id.text_Miles);

        displayTotalMiles.setText(miles+" Miles");
        displayTotalSteps.setText(steps+" Steps");


        TextView displaySteps = (TextView) root.findViewById(R.id.tv_last_steps);
        TextView displayMiles = (TextView) root.findViewById(R.id.tv_last_miles);
        TextView displayTime = (TextView) root.findViewById(R.id.tv_last_time);

        displayMiles.setText(miles+" Miles");
        displaySteps.setText(steps+" Steps");
        displayTime.setText(time + " ms");

    }

    public void displayMock(View view){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("mock", MODE_PRIVATE);
        String steps = sharedPreferences.getString("totalSteps", "");
        String miles = sharedPreferences.getString("totalMiles", "");
        String mockTime = sharedPreferences.getString("time", "");

        TextView displayTotalSteps = (TextView) root.findViewById(R.id.text_Step);
        TextView displayTotalMiles = (TextView) root.findViewById(R.id.text_Miles);
        displayTotalSteps.setText(steps);
        displayTotalMiles.setText(miles);

        TextView displayTime = (TextView) root.findViewById(R.id.tv_last_time);
        displayTime.setText(mockTime+" mins");

    }

    public void setStepCount(long stepCount) {
        textSteps.setText(String.valueOf(stepCount));
    }
}