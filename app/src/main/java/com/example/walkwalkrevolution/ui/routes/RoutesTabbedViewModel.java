package com.example.walkwalkrevolution.ui.routes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoutesTabbedViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public RoutesTabbedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is routes tabbed fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
