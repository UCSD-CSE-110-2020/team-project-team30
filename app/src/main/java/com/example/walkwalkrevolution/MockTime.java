package com.example.walkwalkrevolution;

import java.util.Calendar;

public class MockTime extends Calendar {
    private static long millis;

    public MockTime(long millis){
        this.millis = millis;
    }

    public static MockTime getInstance(){
        return new MockTime(millis);
    }

    @Override
    public long getTimeInMillis() {
        return millis;
    }

    @Override
    public void setTimeInMillis(long ms){
        millis = ms;
    }

    @Override
    public void computeTime(){

    }

    @Override
    public void computeFields(){

    }

    @Override
    public void add(int int1, int int2){

    }

    @Override
    public void roll(int int1, boolean bool){

    }

    @Override
    public int getMinimum(int int1){
        return int1;
    }

    @Override
    public int getMaximum(int int1){
        return int1;
    }

    @Override
    public int getGreatestMinimum(int int1){
        return int1;
    }

    @Override
    public int getLeastMaximum(int int1){
        return int1;
    }
}
