package com.example.walkwalkrevolution.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;

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
        doneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                launchActivity();
            }
        });

        return root;
    }

    private void launchActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

}
