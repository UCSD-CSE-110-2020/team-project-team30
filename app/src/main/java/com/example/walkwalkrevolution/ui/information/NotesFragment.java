package com.example.walkwalkrevolution.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.RouteStorage;
import com.example.walkwalkrevolution.ui.routes.RoutesFragment;

public class NotesFragment extends Fragment {
    private NotesViewModel notesViewModel;
    private View root;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel =
                ViewModelProviders.of(this).get(NotesViewModel.class);
        root = inflater.inflate(R.layout.fragment_notes, container, false);
        setDoneWithAllInformation();

        return root;
    }

    public void setDoneWithAllInformation(){
        Button doneButton = root.findViewById(R.id.button_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CODE HERE TO SAVE THE NOTES
                getActivity().finish(); //destroy walk screen activity to go back to main activity
                launchActivity();
            }
        });
    }

    private void launchActivity() {

        // Retrieve all the options about the route
        Bundle routeOptions = getArguments();
        String routeName = routeOptions.getString("routeName", "");
        String routeDate = routeOptions.getString("routeDate", "");
        String routeStartLoc = routeOptions.getString("routeStartLoc", "");
        int featureLoop = routeOptions.getInt("featureLoop", -1);
        int featureFlatHilly = routeOptions.getInt("featureFlatHilly", -1);
        int featureStreetTrail = routeOptions.getInt("featureStreetTrail", -1);
        int featureEven = routeOptions.getInt("featureEven", -1);
        int featureDifficulty = routeOptions.getInt("featureDifficulty", -1);
        boolean favorite = routeOptions.getBoolean("favorite", false);

        String notes = ((EditText) root.findViewById(R.id.editText_routeNotes)).getText().toString();

        Route route = new Route(routeName, routeDate, routeStartLoc);
        route.setFeatureLoop(featureLoop);
        route.setFeatureFlatHilly(featureFlatHilly);
        route.setFeatureStreetTrail(featureStreetTrail);
        route.setFeatureEven(featureEven);
        route.setFeatureDifficulty(featureDifficulty);
        route.setFavorite(favorite);
        route.setNotes(notes);
        RouteStorage.addRoute(route);

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("STRING_I_NEED", "PRESSED DONE");
        startActivity(intent);
    }
}
