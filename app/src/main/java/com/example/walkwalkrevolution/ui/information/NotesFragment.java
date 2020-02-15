package com.example.walkwalkrevolution.ui.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.ui.routes.RoutesFragment;

public class NotesFragment extends Fragment {
    private NotesViewModel notesViewModel;
    private View root;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notesViewModel =
                ViewModelProviders.of(this).get(NotesViewModel.class);
        root = inflater.inflate(R.layout.fragment_notes, container, false);
        setDoneWithAllInformation();

        return root;
    }

    public void setDoneWithAllInformation(){
        Button doneButton = root.findViewById(R.id.button_done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CODE HERE TO SAVE THE NOTES
                getActivity().finish(); //destroy walk screen activity to go back to main activity
                launchActivity();
            }
        });
    }

    private void launchActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("cameFromDone", true);
        startActivity(intent);
    }
}
