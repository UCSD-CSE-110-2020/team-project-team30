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
import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.TeamID;
import com.example.walkwalkrevolution.appdata.UserData;
import com.example.walkwalkrevolution.appdata.UserID;
import com.example.walkwalkrevolution.appdata.WalkPlan;
import com.example.walkwalkrevolution.appdata.WalkRSVPStatus;
import com.example.walkwalkrevolution.ui.WalkPlan.WalkPlanViewModel;

import java.util.Map;

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

        ApplicationStateInteractor appdata = MainActivity.getAppDataInteractor();

        UserID thisUser = new UserID(appdata.getLocalUserEmail());
        TeamID teamID = new TeamID(appdata.getUsersTeamID(thisUser).toString());
        WalkPlan walkPlan = appdata.getWalkPlanData(teamID);
        boolean createdWalk = appdata.getWalkPlanExists(teamID);
        if(createdWalk){
            message.setVisibility(View.GONE);
            if(walkPlan.getScheduled()){
                planName.setTextColor(Color.RED);
            }
            //Indicate if the user is proposer of the walkplan
            boolean isProposer = appdata.getLocalUserEmail().equals(walkPlan.getOrganizer());
            if(!isProposer){
                schedule.setVisibility(View.GONE);
                withdraw.setVisibility(View.GONE);
            }else{
                accept.setVisibility(View.GONE);
                badTime.setVisibility(View.GONE);
                badRoute.setVisibility(View.GONE);
            }

            planName.setText(walkPlan.getRouteData().getName().toString());
            planTime.setText(walkPlan.getDate() + " " + walkPlan.getTime());
            int num = getNum(appdata, teamID);
            planMember.setText(num + "/5");

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

            schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Send the notification to the member
                    MainActivity.getAppDataInteractor().scheduleWalk(teamID);
                }
            });

            withdraw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Send the notification to the member
                    MainActivity.getAppDataInteractor().withdrawWalk(teamID);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });

            if(walkPlan.getAllMemberRSVPStatus().get(thisUser) == WalkRSVPStatus.GOING){
                accept.setTextColor(Color.RED);
            }
            if(walkPlan.getAllMemberRSVPStatus().get(thisUser) == WalkRSVPStatus.BAD_TIME){
                badTime.setTextColor(Color.RED);
            }
            if(walkPlan.getAllMemberRSVPStatus().get(thisUser) == WalkRSVPStatus.BAD_ROUTE){
                badRoute.setTextColor(Color.RED);
            }

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Send notification to the proposer
                    appdata.setWalkRSVP(thisUser, WalkRSVPStatus.GOING);
                    Toast.makeText(getContext(), "You accept the walk!", Toast.LENGTH_SHORT).show();
                }
            });

            badTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Send notification to the proposer
                    appdata.setWalkRSVP(thisUser, WalkRSVPStatus.BAD_TIME);
                    Toast.makeText(getContext(), "You decline the walk!", Toast.LENGTH_SHORT).show();
                }
            });

            badRoute.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Send notification to the proposer
                    appdata.setWalkRSVP(thisUser, WalkRSVPStatus.BAD_ROUTE);
                    Toast.makeText(getContext(), "You decline the walk!", Toast.LENGTH_SHORT).show();
                }
            });

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

        return root;
    }

    private int getNum(ApplicationStateInteractor appdata, TeamID teamID) {
        int num = 0;
        for(Map.Entry<UserID, WalkRSVPStatus> entry: appdata.getWalkPlanData(teamID).getAllMemberRSVPStatus().entrySet()){
            if(entry.getValue() == WalkRSVPStatus.GOING){
                num++;
            }
        }
        return num;
    }
}
