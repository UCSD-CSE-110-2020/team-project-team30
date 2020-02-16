package com.example.walkwalkrevolution.ui.information;

import android.os.Bundle;
import android.util.Log;
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

        ImageView filledStar = root.findViewById(R.id.imageView_star_filled);
        ImageView unfilledStar = root.findViewById(R.id.imageView_star_unfilled);
        setStarsOnClick(filledStar, unfilledStar);

        return root;
    }

    public void setStarsOnClick(ImageView filledStar, ImageView unfilledStar){
        filledStar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                filledStar.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.nav_default_enter_anim));
                proceedToNotesFragment(true);
            }
        });

        unfilledStar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                unfilledStar.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.nav_default_enter_anim));
                proceedToNotesFragment(false);
            }
        });
    }

    private void proceedToNotesFragment(boolean isFavoriteRoute) {
        NotesFragment notesFragment = new NotesFragment();

        // Pass previous route options to next fragment, appending the "favorite route" option
        Bundle routeOptions = getArguments();
        routeOptions.putBoolean("favorite", isFavoriteRoute);
        notesFragment.setArguments(routeOptions);

        getFragmentManager().beginTransaction().replace(R.id.walk_screen_container, notesFragment).commit();
    }
}
