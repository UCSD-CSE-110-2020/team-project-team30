package com.example.walkwalkrevolution.ui.team;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.RouteStorage;
import com.example.walkwalkrevolution.Teammate;
import com.example.walkwalkrevolution.ui.routes.RouteAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {
    private String[] myStringArray;
    private TeamViewModel teamViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        teamViewModel =
                ViewModelProviders.of(this).get(TeamViewModel.class);
        root = inflater.inflate(R.layout.fragment_team, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("TeamFragment", "onActivityCreated called");

        ListView listView = (ListView) root.findViewById(R.id.teamListView);
        //ROUTE STORAGE/FIREBASE IMPLEMENTATION HERE FOR LEADING TEAMMATES
        //List<Teammate> teammates = RouteStorage.getTeammates();

        /*
        for (Route r : routeList)
            Log.d("RoutesFragment", String.format("Route in list: %s", r.toString()));
        */

        List<Teammate> teammatesList = new ArrayList<Teammate>();
        Teammate me = new Teammate("Julian", "Alberto");
        Teammate Celine = new Teammate("Celine", "Hernandez");
        Teammate example = new Teammate("First", "Last");
        teammatesList.add(me);
        teammatesList.add(Celine);
        //populating the list
        for(int i = 0; i < 5; i++){
            teammatesList.add(example);
        }

        ArrayAdapter myAdapter = new TeammateAdapter(root.getContext(), teammatesList);
        listView.setAdapter(myAdapter);

        Button addNewTeammateButton = (Button) root.findViewById(R.id.button_add_new_teammate);
        addNewTeammateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addNewTeammateButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.nav_default_enter_anim));
                launchPromptActivity();
            }
        });
    }

    public void launchPromptActivity(){
        Intent intent = new Intent(getActivity(), AddTeammatePromptActivity.class);
        startActivity(intent);
    }
}
