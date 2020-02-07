package com.example.walkwalkrevolution.Fitness;

import android.util.Log;

import com.example.walkwalkrevolution.ui.WalkInProgress;

import java.util.HashMap;
import java.util.Map;

public class FitnessServiceFactory {
    private static final String TAG = "[FitnessServiceFactory]";

    private static Map<String, BluePrint> blueprints = new HashMap<>();

    public static void put(String key, BluePrint bluePrint) {
        blueprints.put(key, bluePrint);
    }

    public static FitnessService create(String key, WalkInProgress walkInProgress) {
        Log.i(TAG, String.format("creating FitnessService with key %s", key));
        return blueprints.get(key).create(walkInProgress);
    }

    public interface BluePrint {
        FitnessService create(WalkInProgress walkInProgress);
    }
}
