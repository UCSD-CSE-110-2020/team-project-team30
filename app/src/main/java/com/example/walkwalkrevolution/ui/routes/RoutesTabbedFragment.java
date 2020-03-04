package com.example.walkwalkrevolution.ui.routes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.walkwalkrevolution.R;
import com.google.android.material.tabs.TabLayout;

public class RoutesTabbedFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private ViewModel routesTabbedViewModel;
    private View root;

    //This is our tablayout
    private TabLayout tabLayout;
    //This is our viewPager
    private ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        routesTabbedViewModel =
                ViewModelProviders.of(this).get(RoutesTabbedViewModel.class);
        root = inflater.inflate(R.layout.fragment_routes_pager, container, false);

        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setActionBar(toolbar);

        //Initializing the tablayout
        tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("My Routes"));
        tabLayout.addTab(tabLayout.newTab().setText("Team Routes"));
        tabLayout.addTab(tabLayout.newTab().setText("Walk Plans"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) root.findViewById(R.id.pager);

        //Creating our pager adapter
        RoutesPagerAdapter adapter =
                new RoutesPagerAdapter(getFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        return root;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.d("OnTabSelected(tab)", "selected tab: " + String.valueOf(tab.getPosition()));
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
