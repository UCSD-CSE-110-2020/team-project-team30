package com.example.walkwalkrevolution.appdata;

public enum WalkRSVPStatus {
    GOING,
    BAD_TIME,
    BAD_ROUTE,
    PENDING;

    public static WalkRSVPStatus toStatus(String status) {
        switch(status) {
            case "GOING":
                return GOING;
            case "BAD_TIME":
                return BAD_TIME;
            case "BAD_ROUTE":
                return BAD_ROUTE;
            case "PENDING":
                return PENDING;
            default:
                return PENDING;
        }
    }
}
