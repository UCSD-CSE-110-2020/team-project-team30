package com.example.walkwalkrevolution.ui.routes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.ui.WalkInProgress;

import java.util.ArrayList;
import java.util.List;

public class RouteAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Route> routeList = new ArrayList<Route>();
    private String fitnessServiceKey = "GOOGLE_FIT";

    public RouteAdapter(@NonNull Context context, ArrayList<Route> list) {
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



        listItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(mContext, "Starting: " + currentRoute.getName(), Toast.LENGTH_SHORT).show();
                launchActivity();
                //name.setBackground("#00FF00");
            }
        });


        return listItem;
    }

    private void launchActivity() {
        Intent intent = new Intent(getContext(), WalkInProgress.class);
        intent.putExtra(WalkInProgress.FITNESS_SERVICE_KEY, fitnessServiceKey);
        mContext.startActivity(intent);
    }
}
