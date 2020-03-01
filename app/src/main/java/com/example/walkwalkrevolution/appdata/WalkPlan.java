package com.example.walkwalkrevolution.appdata;

import com.example.walkwalkrevolution.Route;

import java.util.Map;
import java.util.TreeMap;

class WalkPlan {
    private Route routeData;
    private String date;
    private String time;
    private UserID organizer;
    private Map<UserID, WalkRSVPStatus> memberRSVPStatus;

    public WalkPlan(Route routeData, String date, String time, UserID organizer) {
        this.routeData = routeData;
        this.date = date;
        this.time = time;
        this.organizer = organizer;

        this.memberRSVPStatus = new TreeMap<>();
        this.memberRSVPStatus.put(organizer, WalkRSVPStatus.GOING);

        // TODO Grab reference to appdata from wherever it's instantiated
        ApplicationStateInteractor appdata = null;
        for (UserID teammate : appdata.getTeamMemberIDs(organizer)) {
            this.memberRSVPStatus.put(teammate, WalkRSVPStatus.PENDING);
        }
    }

    public void setRSVPStatus(UserID userID, WalkRSVPStatus status) {
        // TODO Grab reference to appdata from wherever it's instantiated
        ApplicationStateInteractor appdata = null;
        this.memberRSVPStatus.put(userID, status);
    }

    public Route getRouteData() {
        return routeData;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public UserID getOrganizer() {
        return organizer;
    }

    public Map<UserID, WalkRSVPStatus> getAllMemberRSVPStatus() {
        return memberRSVPStatus;
    }
}
