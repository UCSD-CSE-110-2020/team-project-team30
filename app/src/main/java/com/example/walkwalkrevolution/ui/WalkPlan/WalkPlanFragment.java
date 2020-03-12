package com.example.walkwalkrevolution.ui.WalkPlan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.appdata.TeamID;
import com.example.walkwalkrevolution.appdata.UserID;
import com.example.walkwalkrevolution.appdata.WalkPlan;
import com.example.walkwalkrevolution.appdata.WalkRSVPStatus;
import com.example.walkwalkrevolution.ui.WalkPlan.WalkPlanViewModel;

public class WalkPlanFragment extends Fragment {

    public static final String TAG = "WalkPlanFragment";

    public static WalkPlanFragment newInstance() {
        Bundle args = new Bundle();
        WalkPlanFragment fragment = new WalkPlanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private WalkPlanViewModel walkPlanViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        walkPlanViewModel =
                ViewModelProviders.of(this).get(WalkPlanViewModel.class);
        root = inflater.inflate(R.layout.fragment_walkplan, container, false);

        TextView message = root.findViewById(R.id.tv_walkplan_message);
        TextView planName = root.findViewById(R.id.tv_plan_name);
        TextView planTime = root.findViewById(R.id.tv_plan_time);
        TextView planMember = root.findViewById(R.id.tv_plan_member);
        Button schedule = root.findViewById(R.id.btn_schedule);
        Button withdraw = root.findViewById(R.id.btn_withdraw);
        Button accept = root.findViewById(R.id.btn_in_acc);
        Button badTime = root.findViewById(R.id.btn_bad_time);
        Button badRoute = root.findViewById(R.id.btn_bad_route);

        //TODO: Delete after done the next TODO
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("propose", Context.MODE_PRIVATE);
        boolean createdWalk = sharedPreferences.getBoolean("proposewalk", false);
        //TODO: Change into checking if there's available walkplan
        if(createdWalk){
            message.setVisibility(View.GONE);
        }else{
            planName.setVisibility(View.GONE);
            planMember.setVisibility(View.GONE);
            planTime.setVisibility(View.GONE);
            schedule.setVisibility(View.GONE);
            withdraw.setVisibility(View.GONE);
            accept.setVisibility(View.GONE);
            badTime.setVisibility(View.GONE);
            badRoute.setVisibility(View.GONE);
        }

        //TODO: If this user is not proposer
        boolean groupMember = true;
        if(groupMember){
            schedule.setVisibility(View.GONE);
            withdraw.setVisibility(View.GONE);
        }else{
            accept.setVisibility(View.GONE);
            badTime.setVisibility(View.GONE);
            badRoute.setVisibility(View.GONE);
        }

        //TODO:Replace by the things from firebase
        //planName.setText(walkplan.getRouteData().getName().toString());
        //planTime.setText(walkplan.getDate() + walkplan.getTime());
        //planMember.setText(walkplan.memberRSVPStatus);
        planName.setText("Canyon Loop");
        planTime.setText("03/04/2020 4PM");
        planMember.setText("0/5");

        //Go to Google Maps
        planName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + planName.getText().toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        //TODO: If this user is the proposer
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send the notification to the member
                //Schedule the Walk TODO:replace by real TeamID here
                MainActivity.getAppDataInteractor().scheduleWalk(new TeamID("jiz546@ucsd.edu"));

            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send the notification to the member
                //TODO:replace by real TeamID here
                MainActivity.getAppDataInteractor().withdrawWalk(new TeamID("jiz546@ucsd.edu"));
                //TODO:delete this rest when walkplan getter is done
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("propose", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("proposewalk", false);
                editor.apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        //TODO:Set a indicator which option is selected
        /*if(MainActivity.getAppDataInteractor().getUserTeamInviteStatus(thisId) == WalkRSVPStatus.GOING){
            accept.setTextColor(Color.RED);
        }
        if(MainActivity.getAppDataInteractor().getUserTeamInviteStatus(thisId) == WalkRSVPStatus.BAD_TIME){
            badTime.setTextColor(Color.RED);
        }
        if(MainActivity.getAppDataInteractor().getUserTeamInviteStatus(thisId) == WalkRSVPStatus.BAD_ROUTE){
            badRoute.setTextColor(Color.RED);
        }*/

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send notification to the proposer
                //walkplan.setRSVPStatus(userID, WalkRSVPStatus.GOING);
                Toast.makeText(getContext(), "You accept the walk!", Toast.LENGTH_SHORT).show();
            }
        });

        badTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send notification to the proposer
                //walkplan.setRSVPStatus(userID, WalkRSVPStatus.BAD_TIME);
                Toast.makeText(getContext(), "You decline the walk!", Toast.LENGTH_SHORT).show();
            }
        });

        badRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send notification to the proposer
                //walkplan.setRSVPStatus(userID, WalkRSVPStatus.BAD_ROUTE);
                Toast.makeText(getContext(), "You decline the walk!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
