package com.example.walkwalkrevolution.appdata;

import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.Teammate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MockInteractor implements  ApplicationStateInteractor{

    private int scenarioVersion;
    private static List<Teammate> teammates = new ArrayList<Teammate>();
    private static String email;


    public MockInteractor() {

    }


    public static void dummyAddEmail(String __email) {email = __email;}
    @Override
    public String getMyEmail() {
        return email;
    }

    @Override
    public boolean isUserEmailTaken(String email) {
        return false;
    }

    @Override
    public void addUserToDatabase(UserID id, UserData userData) {

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


    public static void dummyAddTeammates(Teammate teammate) {
        teammates.add(teammate);
    }
    public List<Teammate> getTeammates(UserID userID) {
        return teammates;
    }


    @Override
    public TeamID getUsersTeamID(UserID userID) {
        return null;
    }

    @Override
    public List<Route> getUserRoutes(UserID userID) {
        return null;
    }

    @Override
    public void addUserRoute(UserID userID, Route route) {

    }

    @Override
    public boolean getWalkPlanExists() {
        return false;
    }

    @Override
    public void addWalkPlan(WalkPlan walkPlan) {

    }

    @Override
    public WalkPlan getWalkPlanData() {
        return null;
    }

    @Override
    public void withdrawWalk() {

    }

    @Override
    public void scheduleWalk() {

    }

    @Override
    public void setWalkRSVP(UserID userID, String status) {

    }
}
