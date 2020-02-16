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
    private boolean routeExists;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        informationViewModel =
                ViewModelProviders.of(this).get(InformationViewModel.class);
        root = inflater.inflate(R.layout.fragment_information, container, false);


        Intent currentIntent = getActivity().getIntent();
        //boolean defaultValue = false;
        routeExists = currentIntent.getBooleanExtra("route exists", false);

        if(routeExists){
            onlyDisplayDoneButton();
            performContinuationCheck(false); //check whether use can proceed
        }
        else{
            performContinuationCheck(true); //check whether use can proceed
        }

        return root;
    }

    public void performContinuationCheck(boolean performCheck){
        Button doneButton = root.findViewById(R.id.button_done);
        doneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean allowed = true;
                String routeName = ((TextView) root.findViewById(R.id.editText_route_name)).getText().toString();
                String routeStartLoc = ((TextView) root.findViewById(R.id.editText_starting_location)).getText().toString();
                String routeDate = "TODO Date";

                if(performCheck){
                    allowed = allowedToBeDone();
                }
                if(allowed){
                    finishWalkAndResumeRoutesActivity(routeName, routeDate, routeStartLoc);
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
                String routeName = ((TextView) root.findViewById(R.id.editText_route_name)).getText().toString();
                String routeStartLoc = ((TextView) root.findViewById(R.id.editText_starting_location)).getText().toString();
                String routeDate = "TODO Date";

                if(allowed){
                    Fragment featuresFragment = new FeaturesFragment();
                    TextView steps = getActivity().findViewById(R.id.tv_WalkScreen);
                    TextView miles = getActivity().findViewById(R.id.tv_Miles);
                    Chronometer chronometer = getActivity().findViewById(R.id.chronometer);
                    steps.setVisibility(View.GONE);
                    miles.setVisibility(View.GONE);
                    chronometer.setVisibility(View.GONE);

                    // Pass information from this route to next fragment
                    Bundle routeOptions = new Bundle();
                    routeOptions.putString("routeName", routeName);
                    routeOptions.putString("routeDate", routeDate);
                    routeOptions.putString("routeStartLoc", routeStartLoc);
                    featuresFragment.setArguments(routeOptions);

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

    private void finishWalkAndResumeRoutesActivity(String routeName, String dateStarted, String startLoc) {
        // Create a basic route with no additional information
        Route route = new Route(routeName, dateStarted, startLoc);
        RouteStorage.addRoute(route);

        Intent intent = new Intent(getActivity(), MainActivity.class);
        if(routeExists){
            TextView routeNameTextView = getActivity().findViewById(R.id.textView_routeName);
            String routeSavedMessage = routeNameTextView.getText().toString() + " saved";
            intent.putExtra("routeSaved", routeSavedMessage);
        }
        else{
            EditText newRouteNameEditText = root.findViewById(R.id.editText_route_name);
            String routeSavedMessage = newRouteNameEditText.getText().toString() + " saved";
            intent.putExtra("routeSaved", routeSavedMessage);
        }
        startActivity(intent);
    }

    private void onlyDisplayDoneButton(){
        EditText routeNameEditText = root.findViewById(R.id.editText_route_name);
        EditText startingLocationEditText = root.findViewById(R.id.editText_starting_location);
        Button enterMoreInfo = root.findViewById(R.id.button_enter_more_info);
        routeNameEditText.setVisibility(View.GONE);
        startingLocationEditText.setVisibility(View.GONE);
        enterMoreInfo.setVisibility(View.GONE);
    }
}
