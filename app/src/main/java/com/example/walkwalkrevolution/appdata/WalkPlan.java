package com.example.walkwalkrevolution.appdata;

import com.example.walkwalkrevolution.Route;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WalkPlan {

    public static final String KEY_ROUTE_DATA  = "route_data";
    public static final String KEY_TEAM_ID     = "team_id";
    public static final String KEY_DATE        = "date";
    public static final String KEY_TIME        = "time";
    public static final String KEY_ORGANIZER   = "organizer";
    public static final String KEY_RSVP_STATUS = "rsvp_status";
    public static final String KEY_IS_SCHEDULED= "is_scheduled";

    private Route routeData;
    private String date;
    private String time;
    private UserID organizer;
    private TeamID teamID;
    private boolean isScheduled;
    private Map<UserID, WalkRSVPStatus> memberRSVPStatus;

    public WalkPlan(Route routeData, String date, String time, UserID organizer, TeamID teamID, List<UserID> members) {
        this.routeData = routeData;
        this.date = date;
        this.time = time;
        this.organizer = organizer;
        this.teamID = teamID;
        this.isScheduled = false;

        this.memberRSVPStatus = new TreeMap<>();
        this.memberRSVPStatus.put(organizer, WalkRSVPStatus.GOING);

        for (UserID teammate : members) {
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

    public TeamID getTeamID() { return teamID; }

    public UserID getOrganizer() {
        return organizer;
    }

    public Map<UserID, WalkRSVPStatus> getAllMemberRSVPStatus() {
        return memberRSVPStatus;
    }

    public Map<String, Object> toFirebaseDoc() {
        Map<String, Object> documentData = new TreeMap<>();

        documentData.put(KEY_DATE, date);
        documentData.put(KEY_TIME, time);
        documentData.put(KEY_ORGANIZER, organizer.toString());
        documentData.put(KEY_TEAM_ID, teamID.toString());
        documentData.put(KEY_ROUTE_DATA, routeData);
        documentData.put(KEY_IS_SCHEDULED, isScheduled);

        Map<String, Object> rsvpStats = new TreeMap<>();
        for (UserID member : memberRSVPStatus.keySet()) {
            rsvpStats.put(member.toString(), memberRSVPStatus.get(member));
        }
        documentData.put(KEY_RSVP_STATUS, rsvpStats);

        return documentData;
    }
}
