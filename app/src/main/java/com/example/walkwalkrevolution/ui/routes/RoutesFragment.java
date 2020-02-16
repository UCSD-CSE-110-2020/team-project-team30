package com.example.walkwalkrevolution.ui.routes;

import android.os.Bundle;
import android.util.Log;
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
import com.example.walkwalkrevolution.RouteStorage;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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

        Log.d("RoutesFragment", "onActivityCreated called");

        ListView listView = (ListView) root.findViewById(R.id.routesListView);
        List<Route> routeList = RouteStorage.getRoutes();

        for (Route r : routeList)
            Log.d("RoutesFragment", String.format("Route in list: %s", r.toString()));

        ArrayAdapter myAdapter = new RouteAdapter(root.getContext(), routeList);
        listView.setAdapter(myAdapter);
    }

}
