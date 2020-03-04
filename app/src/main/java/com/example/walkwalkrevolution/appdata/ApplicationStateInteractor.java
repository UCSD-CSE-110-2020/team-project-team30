package com.example.walkwalkrevolution.appdata;

import com.example.walkwalkrevolution.Route;

import java.util.List;

public interface ApplicationStateInteractor {
    boolean isUserEmailTaken(String email);
    void addUserToDatabase(UserID id, UserData userData);

    boolean getIsUserInAnyTeam(UserID userID);
    TeamID getUserTeamInviteStatus(UserID userID);
    void inviteUserToTeam(UserID userID, TeamID teamID);
    void resetUserTeamInvite(UserID userID);
    void addUserToTeam(UserID userID, TeamID teamID);

    List<UserID> getTeamMemberIDs(UserID userID);
    List<Route> getUserRoutes(UserID userID);
    void addUserRoute(UserID userID, Route route);

    boolean getWalkPlanExists();
    void addWalkPlan(WalkPlan walkPlan);
    WalkPlan getWalkPlanData();
    void withdrawWalk();
    void scheduleWalk();
    void setWalkRSVP(UserID userID, String status);
}
