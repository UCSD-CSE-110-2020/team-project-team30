package com.example.walkwalkrevolution.appdata;

import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.Teammate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MockInteractor implements  ApplicationStateInteractor{

    private int scenarioVersion;

    private static String email;

    public MockInteractor() {

    }


    public static void dummySetEmail(String __email) {email = __email;}
    @Override
    public UserID getLocalUserID() {
        return new UserID(email);
    }

    @Override
    public void setLocalUserEmail(String email) {

    }

    @Override
    public boolean isUserEmailTaken(String email) {
        return false;
    }

    @Override
    public void addUserToDatabase(UserID id, UserData userData) {

    }

    @Override
    public UserData getUserData(UserID userID) {
        return null;
    }

    @Override
    public boolean getIsUserInAnyTeam(UserID userID) {
        return false;
    }

    @Override
    public TeamID getUserTeamInviteStatus(UserID userID) {
        return null;
    }

    @Override
    public void inviteUserToTeam(UserID userID, TeamID teamID) {

    }

    @Override
    public void resetUserTeamInvite(UserID userID) {

    }

    @Override
    public void addUserToTeam(UserID userID, TeamID teamID) {

    }

    @Override
    public List<UserID> getTeamMemberIDs(UserID userID) {
        return null;
    }

    @Override
    public Teammate getTeammate(UserID userID) {
        return null;
    }

    private static List<Teammate> teammates = new ArrayList<Teammate>();

    public static void dummyAddTeammates(Teammate teammate, String t_email) {
        teammate.setEmail(t_email);
        teammates.add(teammate);
    }

    public List<Teammate> getTeammates(UserID userID) {
        return teammates;
    }

    @Override
    public TeamID getUsersTeamID(UserID userID) {
        return null;
    }

    private static Map<String, List<Route>> userRoutes = new HashMap<String, List<Route>>();
    public static void dummyAddUserRoute(UserID usr, Route route) {
        if(!userRoutes.containsKey(usr.toString())) userRoutes.put(usr.toString(), new ArrayList<Route>() );
        (userRoutes.get(usr.toString())).add(route);
    }
    @Override
    public List<Route> getUserRoutes(UserID userID) {
        return userRoutes.get( userID.toString() );
    }

    @Override
    public void addUserRoute(UserID userID, Route route) {

    }

    @Override
    public boolean getWalkPlanExists(TeamID teamID) {
        return false;
    }

    @Override
    public void addWalkPlan(WalkPlan walkPlan) {

    }

    @Override
    public WalkPlan getWalkPlanData(TeamID teamID) {
        return null;
    }

    @Override
    public void withdrawWalk(TeamID teamID) {

    }

    @Override
    public void scheduleWalk(TeamID teamID) {

    }

    @Override
    public void setWalkRSVP(UserID userID, WalkRSVPStatus status) {

    }


    public void setRouteFavorite(UserID userID, Route route, Boolean favoriteStatus) {

    }
    public Boolean getRouteFavorite(UserID userID, Route route) {
        return false;
    }


    public static String dummyGetTeammateEmail(String email){
        for(Teammate teammate: teammates){
            return teammate.getEmail();
        }
        return "";
    }
}
