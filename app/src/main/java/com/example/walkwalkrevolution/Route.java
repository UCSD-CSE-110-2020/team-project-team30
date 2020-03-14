package com.example.walkwalkrevolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Route {

    public static final String KEY_NAME = "name";
    public static final String KEY_DATE = "date";
    public static final String KEY_START = "start";
    public static final String KEY_FAV = "isFavorite";

    public static final String KEY_FEAT_LOOP = "featureLoop";
    public static final String KEY_FEAT_FLAT = "featureFlatHilly";
    public static final String KEY_FEAT_STREET = "featureStreetTrail";
    public static final String KEY_FEAT_EVEN = "featureEven";
    public static final String KEY_FEAT_DIFF = "featureDifficulty";

    public static final String KEY_NOTES = "notes";

    private String name;
    private String date;
    private String start;
    private boolean favorite;

    private int featureLoop;
    private int featureFlatHilly;
    private int featureStreetTrail;
    private int featureEven;
    private int featureDifficulty;
    private String notes;

    private String Id;

    public Route(String name, String date, String start) {
        this.name = name;
        this.date = date;
        this.start = start;

        // Extra options, not necessary to initialize at first
        this.favorite = false;
        this.featureLoop        = -1;
        this.featureFlatHilly   = -1;
        this.featureStreetTrail = -1;
        this.featureEven        = -1;
        this.featureDifficulty  = -1;
        this.notes = "";
    }

    public Route() {
        this(null, null, null);
    }

    public static List<Route> deserializeListFromFirestore(List<Map<String, Object>> rawList) {
        List<Route> routes = new ArrayList<>();

        for (Map<String, Object> rawRouteData : rawList) {
            Route r = deserializeFromFirestore(rawRouteData);

            routes.add(r);
        }

        return routes;
    }

    public static Route deserializeFromFirestore(Map<String, Object> rawRouteData) {

        String name  = (String) rawRouteData.get(KEY_NAME);
        String date  = (String) rawRouteData.get(KEY_DATE);
        String start = (String) rawRouteData.get(KEY_START);

        String notes = (String) rawRouteData.get(KEY_NOTES);
        boolean fav = (boolean) rawRouteData.get(KEY_FAV);

        Route route = new Route(name, date, start);
        route.setNotes(notes);
        route.setFavorite(fav);

        route.setFeatureDifficulty(((Long) rawRouteData.get(KEY_FEAT_DIFF)).intValue());
        route.setFeatureEven(((Long) rawRouteData.get(KEY_FEAT_EVEN)).intValue());
        route.setFeatureLoop(((Long) rawRouteData.get(KEY_FEAT_LOOP)).intValue());
        route.setFeatureFlatHilly(((Long) rawRouteData.get(KEY_FEAT_FLAT)).intValue());
        route.setFeatureStreetTrail(((Long) rawRouteData.get(KEY_FEAT_STREET)).intValue());

        return route;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getStart() {return start;}

    public void setFavorite(boolean favorite){
        this.favorite = favorite;
    }

    public void setFeatureLoop(int val) {
        this.featureLoop = val;
    }

    public void setFeatureFlatHilly(int val) {
        this.featureFlatHilly = val;
    }

    public void setFeatureStreetTrail(int val) {
        this.featureStreetTrail = val;
    }

    public void setFeatureEven(int val) {
        this.featureEven = val;
    }

    public void setFeatureDifficulty(int val) {
        this.featureDifficulty = val;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean getIsFavorite(){
        return this.favorite;
    }

    public int getFeatureLoop() {
        return this.featureLoop;
    }

    public int getFeatureFlatHilly() {
        return this.featureFlatHilly;
    }

    public int getFeatureStreetTrail() {
        return this.featureStreetTrail;
    }

    public int getFeatureEven() {
        return this.featureEven;
    }

    public int getFeatureDifficulty() {
        return this.featureDifficulty;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String toString(){
        return String.format("{name: %s, date: %s, start_loc: %s, favorite: %s, ",
                               name, date, start, (favorite ? "true" : "false")) +
               String.format("loop: %d, flatHilly: %d, streetTrail: %d, even: %d, difficulty: %d}",
                       featureLoop, featureFlatHilly, featureStreetTrail, featureEven, featureDifficulty);
    }

    @Override
    public boolean equals(Object o) {
        return (o != null) && this.toString().equals(((Route) o).toString());
    }
}
