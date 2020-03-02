package com.example.walkwalkrevolution.ui.WalkPlan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.ui.WalkPlan.WalkPlanViewModel;

public class WalkPlanFragment extends Fragment {

    private WalkPlanViewModel walkPlanViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        walkPlanViewModel =
                ViewModelProviders.of(this).get(WalkPlanViewModel.class);
        root = inflater.inflate(R.layout.fragment_walkplan, container, false);

        return root;
    }
}
