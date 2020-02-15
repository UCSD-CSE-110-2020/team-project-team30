package com.example.walkwalkrevolution.ui.information;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.R;

public class FavoriteFragment extends Fragment {
    private FavoriteViewModel favoriteViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        favoriteViewModel =
                ViewModelProviders.of(this).get(FavoriteViewModel.class);
        root = inflater.inflate(R.layout.fragment_favorite, container, false);

        //by default, make the user
        allowToContinue(false);

        ImageView filledStar = root.findViewById(R.id.imageView_star_filled);
        ImageView unfilledStar = root.findViewById(R.id.imageView_star_unfilled);
        setStarsOnClick(filledStar, unfilledStar);

        return root;
    }

    //allow the user to continue only if they selected if it is a favorite or not
    public void allowToContinue(boolean allowed){
        ImageView nextButton = root.findViewById(R.id.imageView_next);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(allowed){
                    nextButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.nav_default_enter_anim));
                    NotesFragment notesFragment = new NotesFragment();
                    getFragmentManager().beginTransaction().replace(R.id.walk_screen_container, notesFragment).commit();
                }
                else{
                    Toast.makeText(getContext(), "Please choose an option", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setStarsOnClick(ImageView filledStar, ImageView unfilledStar){
        filledStar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                filledStar.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.nav_default_enter_anim));
                Toast.makeText(getContext(), "Route Favorited", Toast.LENGTH_SHORT).show();
                allowToContinue(true);
            }
        });

        unfilledStar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                unfilledStar.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.nav_default_enter_anim));
                Toast.makeText(getContext(), "Route Unfavorited", Toast.LENGTH_SHORT).show();
                allowToContinue(true);
            }
        });
    }
}
