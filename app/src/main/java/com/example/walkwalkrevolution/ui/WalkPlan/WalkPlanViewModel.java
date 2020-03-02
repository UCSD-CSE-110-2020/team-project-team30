package com.example.walkwalkrevolution.ui.WalkPlan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WalkPlanViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public WalkPlanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is WalkPlan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
