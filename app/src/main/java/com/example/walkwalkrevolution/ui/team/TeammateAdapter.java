package com.example.walkwalkrevolution.ui.team;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Teammate;

import java.util.ArrayList;
import java.util.List;

public class TeammateAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Teammate> teammateList;

    public TeammateAdapter(@NonNull Context context, List<Teammate> list) {
        super(context, 0 , list);
        mContext = context;
        teammateList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.fragment_team,parent,false);

        if(listItem.findViewById(R.id.textView_no_teammates).getVisibility() == View.VISIBLE){
            listItem.findViewById(R.id.textView_no_teammates).setVisibility(View.GONE);
        }
        final Teammate currentTeammate = teammateList.get(position);

        final TextView nameTextView = (TextView) listItem.findViewById(R.id.textView_teammate_name);
        nameTextView.setText(currentTeammate.getFullName());
        nameTextView.setPadding(20, 15, 20, 15);

        final TextView initialsTextView = (TextView) listItem.findViewById(R.id.color_coded_icon);
        initialsTextView.setText(currentTeammate.getIconInitials());
        initialsTextView.setVisibility(View.VISIBLE);
        GradientDrawable tvBackground = (GradientDrawable) initialsTextView.getBackground();
        tvBackground.setColor(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));

        final Button addNewTeammateButton = (Button) listItem.findViewById(R.id.button_add_new_teammate);
        addNewTeammateButton.setVisibility(View.GONE);

        listItem.findViewById(R.id.btn_login).setVisibility(View.GONE);
        listItem.findViewById(R.id.btn_initFirebase).setVisibility(View.GONE);
        listItem.findViewById(R.id.btn_runTests).setVisibility(View.GONE);

        listItem.setPadding(20, 20, 20, 20);
        listItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d("teammate name...", currentTeammate.getFullName());
                //launchActivity(currentRoute.getName());
            }
        });
        return listItem;
    }
}
