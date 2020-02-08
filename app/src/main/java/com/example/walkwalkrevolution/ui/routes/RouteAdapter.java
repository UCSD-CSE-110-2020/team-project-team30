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

    public RouteAdapter(@NonNull Context context, @LayoutRes ArrayList<Route> list) {
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
        //ImageView image = (ImageView)listItem.findViewById(R.id.imageView_poster);
        //image.setImageResource();

        TextView name = (TextView) listItem.findViewById(R.id.textView_name);
        name.setText(currentRoute.getName());

        TextView date = (TextView) listItem.findViewById(R.id.textView_date);
        date.setText(currentRoute.getDate());

        listItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(mContext, currentRoute.getName(), Toast.LENGTH_SHORT).show();
            }
        });


        return listItem;
    }
}
