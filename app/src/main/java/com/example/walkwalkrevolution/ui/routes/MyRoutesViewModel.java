package com.example.walkwalkrevolution.ui.routes;

import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.walkwalkrevolution.R;

public class MyRoutesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyRoutesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}