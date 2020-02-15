package com.example.walkwalkrevolution;

public class Route {
    private String name;
    private String date;
    private String start;
    private boolean favorite;
    private String Id;

    public Route(String name, String date, String start) {
        this.name = name;
        this.date = date;
        this.start = start;
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

    public boolean isFavorite(){
        return this.favorite;
    }

    public String toString(){
        return String.format("{name: %s, date: %s, start_loc: %s, favorite: %s}", name, date, start, (favorite ? "true" : "false"));
    }
}
