package com.example.walkwalkrevolution.ui.information;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
        RadioGroup firstGroup = (RadioGroup) root.findViewById(R.id.rg_loop);
        RadioGroup secondGroup = (RadioGroup) root.findViewById(R.id.rg_flatHilly);
        RadioGroup thirdGroup = (RadioGroup) root.findViewById(R.id.rg_streetTrail);
        RadioGroup fourthGroup = (RadioGroup) root.findViewById(R.id.rg_even);
        RadioGroup fifthGroup = (RadioGroup) root.findViewById(R.id.rg_difficulty);

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
                RadioGroup rg_loop = (RadioGroup) root.findViewById(R.id.rg_loop);
                RadioGroup rg_flatHilly = (RadioGroup) root.findViewById(R.id.rg_flatHilly);
                RadioGroup rg_streetTrail = (RadioGroup) root.findViewById(R.id.rg_streetTrail);
                RadioGroup rg_even = (RadioGroup) root.findViewById(R.id.rg_even);
                RadioGroup rg_difficulty = (RadioGroup) root.findViewById(R.id.rg_difficulty);

                int featureLoop = rg_loop.getCheckedRadioButtonId();
                int featureFlatHilly = rg_flatHilly.getCheckedRadioButtonId();
                int featureStreetTrail = rg_streetTrail.getCheckedRadioButtonId();
                int featureEven = rg_even.getCheckedRadioButtonId();
                int featureDifficulty = rg_difficulty.getCheckedRadioButtonId();

                //Log.d("FeaturesFragment", String.format(""));

                boolean allowed = checkRadioButtons();
                if(allowed){
                    nextButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.nav_default_enter_anim));
                    FavoriteFragment favoriteFragment = new FavoriteFragment();

                    // Pass information about route to next fragment, appending the route features
                    Bundle routeOptions = getArguments();
                    routeOptions.putInt("featureLoop", featureLoop);
                    routeOptions.putInt("featureFlatHilly", featureFlatHilly);
                    routeOptions.putInt("featureStreetTrail", featureStreetTrail);
                    routeOptions.putInt("featureEven", featureEven);
                    routeOptions.putInt("featureDifficulty", featureDifficulty);
                    favoriteFragment.setArguments(routeOptions);

                    getFragmentManager().beginTransaction().replace(R.id.walk_screen_container, favoriteFragment).commit();
                }
                else{
                    Toast.makeText(getContext(), "Please choose an option from each group", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
