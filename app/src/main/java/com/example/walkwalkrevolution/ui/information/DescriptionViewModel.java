package com.example.walkwalkrevolution.ui.information;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DescriptionViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DescriptionViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
