package com.example.walkwalkrevolution.appdata;

import com.example.walkwalkrevolution.Route;

import java.util.ArrayList;
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

        for (UserID teammate : members) {
            this.memberRSVPStatus.put(teammate, WalkRSVPStatus.PENDING);
        }

        this.memberRSVPStatus.put(organizer, WalkRSVPStatus.GOING);
    }

    public static WalkPlan deserializeFromFirestore(Map<String, Object> data) {

        Route routeData = Route.deserializeFromFirestore((Map<String, Object>) data.get(KEY_ROUTE_DATA));
        String date = (String) data.get(KEY_DATE);
        String time = (String) data.get(KEY_TIME);

        String organizerStr = (String) data.get(KEY_ORGANIZER);
        UserID organizer = organizerStr == null ? null : new UserID(organizerStr);

        String teamStr = (String) data.get(KEY_TEAM_ID);
        TeamID teamID = teamStr == null ? null : new TeamID(teamStr);

        List<UserID> membersList = new ArrayList<>();
        Map<UserID, WalkRSVPStatus> allMemberRsvpStatus = new TreeMap<>();

        Map<String, Object> rawRsvpStatuses = (Map<String, Object>) data.get(KEY_RSVP_STATUS);

        for (String teammateEncoded : rawRsvpStatuses.keySet()) {
            String teammate = FirebaseInteractor.decodeKey(teammateEncoded);
            UserID teammateID = new UserID(teammate);

            membersList.add(teammateID);
            allMemberRsvpStatus.put(teammateID, WalkRSVPStatus.toStatus((String) rawRsvpStatuses.get(teammateEncoded)));
        }

        WalkPlan plan = new WalkPlan(routeData, date, time, organizer, teamID, membersList);
        plan.setAllMemberRSVPStatus(allMemberRsvpStatus);

        return plan;
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

    public void setAllMemberRSVPStatus(Map<UserID, WalkRSVPStatus> allMemberRSVPStatus) {
        this.memberRSVPStatus = allMemberRSVPStatus;
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
            String memberKey = FirebaseInteractor.encodeKey(member.toString());
            rsvpStats.put(memberKey, memberRSVPStatus.get(member));
        }
        documentData.put(KEY_RSVP_STATUS, rsvpStats);

        return documentData;
    }
}
