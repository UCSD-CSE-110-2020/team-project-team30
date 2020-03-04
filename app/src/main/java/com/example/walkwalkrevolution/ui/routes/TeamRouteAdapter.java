package com.example.walkwalkrevolution.ui.routes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;

import com.example.walkwalkrevolution.DescriptionActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.Teammate;

import java.util.ArrayList;
import java.util.List;

public class TeamRouteAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Pair<Route, Teammate>> routeList = new ArrayList<Pair<Route, Teammate>>();
    private String fitnessServiceKey = "GOOGLE_FIT";

    public TeamRouteAdapter(@NonNull Context context, List<Pair<Route, Teammate>> list) {
        super(context, 0 , list);
        mContext = context;
        routeList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.fragment_team_routes,parent,false);

        final Pair<Route, Teammate> current = routeList.get(position);
        Route currentRoute = current.first;
        Teammate currentTeammate = current.second;

        final TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentRoute.getName());

        final TextView date = (TextView) listItem.findViewById(R.id.textView_date);
        date.setText(currentRoute.getDate());

        final TextView start = (TextView) listItem.findViewById(R.id.textView_start);
        start.setText(currentRoute.getStart());

        final ImageView location = (ImageView) listItem.findViewById(R.id.imageView_locationPNG);
        location.setVisibility(View.VISIBLE);

        final TextView initialsTextView = (TextView) listItem.findViewById(R.id.color_coded_icon);
        initialsTextView.setVisibility(View.VISIBLE);

        Log.d("Checking if color coded icon is null", String.valueOf(initialsTextView == null));
        Log.d("Checking if current teammate is null", String.valueOf(currentTeammate == null));
        initialsTextView.setText(currentTeammate.getIconInitials());
        initialsTextView.setVisibility(View.VISIBLE);
        GradientDrawable tvBackground = (GradientDrawable) initialsTextView.getBackground();
        tvBackground.setColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));

        listItem.setPadding(10, 0, 0, 0);
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
        Intent intent = new Intent(getContext(), DescriptionActivity.class);
        intent.putExtra("route name", currentRouteName);
        intent.putExtra("route exists", true);
        mContext.startActivity(intent);
    }
}
