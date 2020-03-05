package com.example.walkwalkrevolution.ui.routes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.RouteStorage;
import com.example.walkwalkrevolution.Teammate;
import com.example.walkwalkrevolution.WalkInProgress;
import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.MockInteractor;
import com.example.walkwalkrevolution.appdata.UserID;

import java.util.ArrayList;
import java.util.List;

public class TeamRoutesFragment extends Fragment {
    private ViewModel teamRoutesViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        teamRoutesViewModel =
                ViewModelProviders.of(this).get(TeamRoutesViewModel.class);
        root = inflater.inflate(R.layout.fragment_team_routes, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("TeamRoutesFragment", "onActivityCreated called");

        ListView listView = (ListView) root.findViewById(R.id.routesListView);
        //Route Storage should take a parameter to choose between My Routes vs. Team Routes!-------
        //----------------TEAM ROUTE STORAGE IMPLEMENTATION HERE-----------------------------------

        List<Pair<Route, Teammate>> pairedList = new ArrayList<Pair<Route, Teammate>>();
        /*
        Route routeExample = new Route("The park", "03/02/20", "My House");
        Teammate teammateExample = new Teammate("Julian", "Alberto");

        Pair<Route, Teammate> pairExample =
                new Pair<Route, Teammate>(routeExample, teammateExample);
        pairedList.add(pairExample);
        */
/*
        MockInteractor.dummyAddUserRoute(new UserID("Amy@Zhu.com"),
                                         new Route("Geisel", "01/01/2020", "gate"));
        MockInteractor.dummyAddUserRoute(new UserID("Amy@Zhu.com"),
                new Route("BioMedLib", "01/02/2020", "rear"));

        MockInteractor.dummyAddUserRoute(new UserID("Linda@Zhu.com"),
                new Route("Geisel", "01/03/2020", "gate"));
        MockInteractor.dummyAddUserRoute(new UserID("Linda@Zhu.com"),
                new Route("BioMedLib", "04/02/2020", "rear"));
*/

        ApplicationStateInteractor firebase = new MockInteractor();
        List<Teammate> teammatesList = firebase.getTeammates(new UserID(firebase.getMyEmail()));
        //Log.d("000", String.valueOf(teammatesList.size()));
        for(Teammate teammate : teammatesList) {
            List<Route> routes = firebase.getUserRoutes(new UserID(teammate.getEmail()));

           // Log.d("xxx", String.valueOf(routes.size()));
            for(Route route : routes) {
               // Log.d("YYY", route.getName());
                Pair<Route, Teammate> p = new Pair<Route, Teammate>(route, teammate);
                pairedList.add(p);
            }
        }

        ArrayAdapter myAdapter = new TeamRouteAdapter(root.getContext(), pairedList);
        listView.setAdapter(myAdapter);
    }

    private void launchActivity() {
        Intent intent = new Intent(getActivity(), WalkInProgress.class);
        intent.putExtra("skip route", true);
        startActivity(intent);
    }
}
