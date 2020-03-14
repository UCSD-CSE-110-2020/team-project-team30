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

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.WalkInProgress;
import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.UserID;

import java.util.List;

public class MyRoutesFragment extends Fragment {
    private String[] myStringArray;
    private MyRoutesViewModel myRoutesViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myRoutesViewModel =
                ViewModelProviders.of(this).get(MyRoutesViewModel.class);
        root = inflater.inflate(R.layout.fragment_my_routes, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("MyRoutesFragment", "onActivityCreated called");

        ListView listView = (ListView) root.findViewById(R.id.routesListView);
        ApplicationStateInteractor appdata = MainActivity.getAppDataInteractor();
        UserID localUserID = appdata.getLocalUserID();
        List<Route> routeList = appdata.getUserRoutes(localUserID);

        for (Route r : routeList)
            Log.d("MyRoutesFragment", String.format("Route in list: %s", r.toString()));

        ArrayAdapter myAdapter = new MyRouteAdapter(root.getContext(), routeList);
        listView.setAdapter(myAdapter);

        Button addNewRouteButton = (Button) root.findViewById(R.id.button_add_new_route);
        addNewRouteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addNewRouteButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.nav_default_enter_anim));
                launchActivity();
            }
        });
    }

    private void launchActivity() {
        Intent intent = new Intent(getActivity(), WalkInProgress.class);
        intent.putExtra("skip route", true);
        startActivity(intent);
    }

}
