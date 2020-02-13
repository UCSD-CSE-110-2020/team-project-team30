package com.example.walkwalkrevolution.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.ui.information.InformationFragment;

import static android.content.ContentValues.TAG;

public class FavoriteFragment extends Fragment {
    private String[] myStringArray;
    private FavoriteViewModel favoriteViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteViewModel =
                ViewModelProviders.of(this).get(FavoriteViewModel.class);
        root = inflater.inflate(R.layout.fragment_favorite, container, false);
        Log.e(TAG, "FavoriteFragment created!!!!");

        CheckBox checkBox = root.findViewById(R.id.checkFavorite);
        checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(checkBox.isChecked()){
                    Toast.makeText(root.getContext(), "Checked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button nextButton = root.findViewById(R.id.nextInFavorite);
        nextButton.setOnClickListener(new View.OnClickListener(){
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
