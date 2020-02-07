package com.example.walkwalkrevolution.ui.routes;

import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.walkwalkrevolution.R;

public class RoutesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RoutesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}