package com.example.walkwalkrevolution.ui.information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.ui.favorite.FavoriteFragment;
import com.example.walkwalkrevolution.ui.favorite.FavoriteViewModel;

import static android.content.ContentValues.TAG;

public class InformationFragment extends Fragment {
    private String[] myStringArray;
    private InformationViewModel informationViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        informationViewModel =
                ViewModelProviders.of(this).get(InformationViewModel.class);
        root = inflater.inflate(R.layout.fragment_information, container, false);
        Log.e(TAG, "InformationFragment created!!!!");

        Button doneButton = root.findViewById(R.id.button_done);
        EditText name = root.findViewById(R.id.editText_route_name);
        EditText start = root.findViewById(R.id.editText_starting_location);
        doneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // launchActivity();
                name.setVisibility(View.GONE);
                start.setVisibility(View.GONE);
                doneButton.setVisibility(View.GONE);
                Fragment favoriteFragment = new FavoriteFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.information_fragment_container, favoriteFragment);
                transaction.commit();
            }
        });

        return root;
    }

    /*private void launchActivity() {
        Intent intent = new Intent(getActivity(), FavoriteFragment.class);
        startActivity(intent);
    }*/

}
