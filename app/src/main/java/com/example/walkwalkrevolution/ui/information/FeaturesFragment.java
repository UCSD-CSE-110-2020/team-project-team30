package com.example.walkwalkrevolution.ui.information;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;

import java.util.ArrayList;

public class FeaturesFragment extends Fragment {
    private FeaturesViewModel featuresViewModel;
    private View root;

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            featuresViewModel =
                    ViewModelProviders.of(this).get(FeaturesViewModel.class);
            root = inflater.inflate(R.layout.fragment_features, container, false);

            allowToContinue();

            return root;
        }

        public boolean checkRadioButtons(){
            boolean checkEveryGroup = true;
            RadioGroup firstGroup = (RadioGroup) root.findViewById(R.id.first_radio_group);
            RadioGroup secondGroup = (RadioGroup) root.findViewById(R.id.second_radio_group);
            RadioGroup thirdGroup = (RadioGroup) root.findViewById(R.id.third_radio_group);
            RadioGroup fourthGroup = (RadioGroup) root.findViewById(R.id.fourth_radio_group);
            RadioGroup fifthGroup = (RadioGroup) root.findViewById(R.id.fifth_radio_group);

            ArrayList<RadioGroup> groups = new ArrayList<RadioGroup>();
            groups.add(firstGroup);
            groups.add(secondGroup);
            groups.add(thirdGroup);
            groups.add(fourthGroup);
            groups.add(fifthGroup);

            for(int index = 0; index < groups.size(); index++){
                RadioGroup currentGroup = groups.get(index);

                if(currentGroup.getCheckedRadioButtonId() == -1){
                    checkEveryGroup = false;
                }
            }
            return checkEveryGroup;
        }

    public void allowToContinue(){
        ImageView nextButton = root.findViewById(R.id.imageView_next);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean allowed = checkRadioButtons();
                if(allowed){
                    nextButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.nav_default_enter_anim));
                    FavoriteFragment favoriteFragment = new FavoriteFragment();
                    getFragmentManager().beginTransaction().replace(R.id.walk_screen_container, favoriteFragment).commit();
                }
                else{
                    Toast.makeText(getContext(), "Please choose an option from each group", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
