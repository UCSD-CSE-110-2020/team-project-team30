package com.example.walkwalkrevolution.ui.WalkPlan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.appdata.WalkPlan;
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


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("propose", Context.MODE_PRIVATE);
        boolean createdWalk = sharedPreferences.getBoolean("proposewalk", false);
        //Created walk as proposer, or get an invitation of walk as member.
        if(createdWalk){
            message.setVisibility(View.GONE);
        }else{
            planName.setVisibility(View.GONE);
            planMember.setVisibility(View.GONE);
            planTime.setVisibility(View.GONE);
            schedule.setVisibility(View.GONE);
            withdraw.setVisibility(View.GONE);
        }

        //MOCKING, Eventually Replace by firebase interaction
        planName.setText("Canyon Loop");
        planTime.setText("03/04/2020 4PM");
        planMember.setText("0/5");

        //If the route is proposed by this UserID
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send the notification to the member
                //Make this route appear in other member's page
                //Disabled the button after clicking
            }
        });


        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send the notification to the member
                //Delete this object/walk from all the members
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("propose", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("proposewalk", false);
                editor.apply();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        //If the route is proposed by a teammember

        return root;
    }
}
