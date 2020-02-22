package com.example.walkwalkrevolution.ui.routes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.walkwalkrevolution.DescriptionActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.WalkInProgress;

import java.util.ArrayList;
import java.util.List;

public class RouteAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Route> routeList = new ArrayList<Route>();
    private String fitnessServiceKey = "GOOGLE_FIT";

    public RouteAdapter(@NonNull Context context, List<Route> list) {
        super(context, 0 , list);
        mContext = context;
        routeList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.fragment_routes,parent,false);

        final Route currentRoute = routeList.get(position);

        final TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentRoute.getName());

        final TextView date = (TextView) listItem.findViewById(R.id.textView_date);
        date.setText(currentRoute.getDate());

        final TextView start = (TextView) listItem.findViewById(R.id.textView_start);
        start.setText(currentRoute.getStart());

        final ImageView location = (ImageView) listItem.findViewById(R.id.imageView_locationPNG);
        location.setVisibility(View.VISIBLE);

        final Button addNewRouteButton = (Button) listItem.findViewById(R.id.button_add_new_route);
        addNewRouteButton.setVisibility(View.GONE);



        listItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("route name...", currentRoute.getName());
                launchActivity(currentRoute.getName());
            }
        });


        return listItem;
    }

    private void launchActivity(String currentRouteName) {
        //Intent intent = new Intent(getContext(), WalkInProgress.class);
        //intent.putExtra(WalkInProgress.FITNESS_SERVICE_KEY, fitnessServiceKey);
        //intent.putExtra("route name", currentRouteName);
        //intent.putExtra("route exists", true);
        Intent intent = new Intent(getContext(), DescriptionActivity.class);
        intent.putExtra("route name", currentRouteName);
        intent.putExtra("route exists", true);
        mContext.startActivity(intent);
    }
}
