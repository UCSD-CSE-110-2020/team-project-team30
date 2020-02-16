package com.example.walkwalkrevolution;

public class Route {
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

    public String toString(){
        return String.format("{name: %s, date: %s, start_loc: %s, favorite: %s, ",
                               name, date, start, (favorite ? "true" : "false")) +
               String.format("loop: %d, flatHilly: %d, streetTrail: %d, even: %d, difficulty: %d}",
                       featureLoop, featureFlatHilly, featureStreetTrail, featureEven, featureDifficulty);
    }
}
