package com.example.walkwalkrevolution.ui.information;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.RouteStorage;
import com.example.walkwalkrevolution.ui.home.HomeFragment;

import java.util.ArrayList;

public class InformationFragment extends Fragment {
    private String[] myStringArray;
    private InformationViewModel informationViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        informationViewModel =
                ViewModelProviders.of(this).get(InformationViewModel.class);
        root = inflater.inflate(R.layout.fragment_information, container, false);

        performContinuationCheck();

        return root;
    }

    public void performContinuationCheck(){
        Button doneButton = root.findViewById(R.id.button_done);
        doneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean allowed = allowedToBeDone();
                if(allowed){
                    finishWalkAndResumeRoutesActivity();
                }
                else{
                    Toast.makeText(getContext(), "Route name is required", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button enterMoreInfoButton = root.findViewById(R.id.button_enter_more_info);
        enterMoreInfoButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean allowed = allowedToBeDone();
                if(allowed){
                    Fragment featuresFragment = new FeaturesFragment();
                    TextView steps = getActivity().findViewById(R.id.tv_WalkScreen);
                    TextView miles = getActivity().findViewById(R.id.tv_Miles);
                    Chronometer chronometer = getActivity().findViewById(R.id.chronometer);
                    steps.setVisibility(View.GONE);
                    miles.setVisibility(View.GONE);
                    chronometer.setVisibility(View.GONE);
                    getFragmentManager().beginTransaction().replace(R.id.walk_screen_container, featuresFragment).commit();
                }
                else{
                    Toast.makeText(getContext(), "Route name is required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean allowedToBeDone(){
        EditText routeName = root.findViewById(R.id.editText_route_name);
        String routeNameInputText = routeName.getText().toString();
        if(routeNameInputText.trim().isEmpty()){
            System.out.println("ALLOWED IS FALSE!");
            return false;
        }
        return true;
    }

    private void finishWalkAndResumeRoutesActivity() {
        createRouteAndSave();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        String strName = "PRESSED DONE";
        intent.putExtra("STRING_I_NEED", strName);
        startActivity(intent);
    }

    public void createRouteAndSave() {
        TextView stepsView = (TextView) root.findViewById(R.id.tv_WalkScreen);
        TextView milesView = (TextView) root.findViewById(R.id.tv_Miles);

        String routeName = ((TextView) root.findViewById(R.id.editText_route_name)).getText().toString();
        String routeStartLoc = ((TextView) root.findViewById(R.id.editText_starting_location)).getText().toString();

        Route route = new Route(routeName, "TODO Date", routeStartLoc);
        RouteStorage.addRoute(route);
    }

}
