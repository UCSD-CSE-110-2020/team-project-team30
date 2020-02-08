package com.example.walkwalkrevolution;

public class Route {
    private String name;
    private String date;

    public Route(String n, String d) {
        name = n;
        date = "\n" + d;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String toString(){
        return name + date;
    }
}
