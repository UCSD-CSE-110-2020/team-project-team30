package com.example.walkwalkrevolution.ui.routes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TeamRoutesViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TeamRoutesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is team routes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }


}
