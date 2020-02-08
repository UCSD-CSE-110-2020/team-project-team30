package com.example.walkwalkrevolution.ui.routes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RoutesFragment extends Fragment {
    private String[] myStringArray;
    private RoutesViewModel routesViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        routesViewModel =
                ViewModelProviders.of(this).get(RoutesViewModel.class);
        root = inflater.inflate(R.layout.fragment_routes, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //later, have array of routes objects! i.e ArrayList<Routes>()
        //need to figure out how to dynamically set the height

        Route route1 = new Route("Arlington Park", "02/07/20");
        Route route2 = new Route("Birmingham Ave", "02/03/20");
        Route route3 = new Route("Colorado Trail", "02/04/20");
        Route route4 = new Route("Dolmant Grove", "02/01/20");
        Route route5 = new Route("Desert's Garden", "01/11/20");
        Route route6 = new Route("Erlington Hiking Trail", "01/21/20");
        Route route7 = new Route("Fisherman's Meadows", "02/05/20");
        Route route8 = new Route("Figeroa's Lane", "01/24/20");
        Route route9 = new Route("Goodie's Curve", "01/12/20");
        Route route10 = new Route("Matrix Inverse", "01/06/20");
        Route route11 = new Route("Julian's Apartment", "01/28/20");
        Route route12 = new Route("Team30 Clubhouse", "02/07/20");
        /*
        String[] routeStringArray = new String[] {route1.toString(),
                route2.toString(), route3.toString(), route4.toString(), route5.toString(), route6.toString(),
                route7.toString(), route8.toString(), route9.toString(), route10.toString(), route11.toString(),
                route12.toString()};
        ListView listView = (ListView) root.findViewById(R.id.routesListView);
        listView.setAdapter(new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_list_item_1 , routeStringArray));
        */
        super.onCreate(savedInstanceState);

        ListView listView = (ListView) root.findViewById(R.id.routesListView);
        ArrayList<Route> routeList = new ArrayList<Route>();
        routeList.add(route1);
        routeList.add(route2);
        routeList.add(route3);
        routeList.add(route4);
        routeList.add(route5);
        routeList.add(route6);
        routeList.add(route7);
        routeList.add(route8);
        routeList.add(route9);
        routeList.add(route10);
        routeList.add(route11);
        routeList.add(route12);

        ArrayAdapter myAdapter = new RouteAdapter(root.getContext(), routeList);
        listView.setAdapter(myAdapter);

    }

}