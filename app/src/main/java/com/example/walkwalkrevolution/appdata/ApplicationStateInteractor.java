package com.example.walkwalkrevolution.appdata;

import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.Teammate;

import java.util.List;

public interface ApplicationStateInteractor {
    UserID getLocalUserID();
    void setLocalUserEmail(String email);

    boolean isUserEmailTaken(String email);
    void addUserToDatabase(UserID id, UserData userData);
    UserData getUserData(UserID userID);

    boolean getIsUserInAnyTeam(UserID userID);
    TeamID getUserTeamInviteStatus(UserID userID);
    void inviteUserToTeam(UserID userID, TeamID teamID);
    void resetUserTeamInvite(UserID userID);
    void addUserToTeam(UserID userID, TeamID teamID);

    List<UserID> getTeamMemberIDs(UserID userID);
  //  Teammate getTeammate(UserID userID);
  //  List<Teammate> getTeammates(UserID userID);
    TeamID getUsersTeamID(UserID userID);
    List<Route> getUserRoutes(UserID userID);
    void addUserRoute(UserID userID, Route route);
    List<Route> getExtraFavRoutes(UserID userID);
    void addExtraFavRoutes(UserID userID, Route route);

    void setRouteFavorite(UserID userID, Route route, Boolean favoriteStatus);
    Boolean getRouteFavorite(UserID userID, Route route);

    boolean getWalkPlanExists(TeamID teamID);
    void addWalkPlan(WalkPlan walkPlan);
    WalkPlan getWalkPlanData(TeamID teamID);
    void withdrawWalk(TeamID teamID);
    void scheduleWalk(TeamID teamID);
    void setWalkRSVP(UserID userID, WalkRSVPStatus status);


}
