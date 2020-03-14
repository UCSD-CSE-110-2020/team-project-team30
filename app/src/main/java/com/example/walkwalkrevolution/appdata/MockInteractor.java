package com.example.walkwalkrevolution.appdata;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.Teammate;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class MockInteractor implements  ApplicationStateInteractor{

    private String localEmail;

    private static final String TAG = "MockInteractor";

    private Context context;

    // Local copies of data on Firebase to be returned by getters
    // Updated asynchronously via listeners to Firestore data
    private Map<UserID, UserData> localExistingUserMap;
    private Map<TeamID, WalkPlan> localWalkPlanMap;


    public MockInteractor() {
        initLocalAppDataStructures();
    }

    private void initLocalAppDataStructures() {
        localExistingUserMap = Collections.synchronizedMap(new TreeMap<>());
        localWalkPlanMap = Collections.synchronizedMap(new TreeMap<TeamID, WalkPlan>());
    }

    @Override
    public UserID getLocalUserID() {
        return new UserID(this.localEmail);
    }

    @Override
    public void setLocalUserEmail(String email) {
        this.localEmail = email;
    }

    @Override
    public boolean isUserEmailTaken(String email) {
        UserID userID = new UserID(email);
        return localExistingUserMap.containsKey(userID);
    }

    @Override
    public void addUserToDatabase(UserID userID, UserData userData) {
        Log.d(TAG, String.format("About to add user %s to firestore", userID.toString()));

        localExistingUserMap.put(userID, userData);
    }

    @Override
    public UserData getUserData(UserID userID) {
        return localExistingUserMap.get(userID);
    }

    @Override
    public boolean getIsUserInAnyTeam(UserID userID) {
        return localExistingUserMap.get(userID).getTeamID() != null;
    }

    @Override
    public TeamID getUserTeamInviteStatus(UserID userID) {
        return localExistingUserMap.get(userID).getTeamInvite();
    }

    @Override
    public void inviteUserToTeam(UserID userID, TeamID teamID) {
        UserData userData = localExistingUserMap.get(userID);
        userData.setTeamInvite(teamID);
    }

    @Override
    public void resetUserTeamInvite(UserID userID) {
        UserData userData = localExistingUserMap.get(userID);
        userData.setTeamInvite(null);
    }

    @Override
    public void addUserToTeam(UserID userID, TeamID teamID) {
        UserData userData = localExistingUserMap.get(userID);
        userData.setTeamID(teamID);
    }

    @Override
    public List<UserID> getTeamMemberIDs(UserID userID) {
        List<UserID> teammates = new ArrayList<>();

        TeamID teamID = localExistingUserMap.get(userID).getTeamID();

        // No teammates if user does not belong in a team
        if (teamID == null) {
            return teammates;
        }

        for (UserID teammateUserID : localExistingUserMap.keySet()) {
            if (teammateUserID.equals(userID))
                continue;

            UserData userData = localExistingUserMap.get(teammateUserID);
            if (teamID.equals(userData.getTeamID()))
                teammates.add(teammateUserID);
        }

        return teammates;
    }


    public void setRouteFavorite(UserID userID, Route route, Boolean favoriteStatus) {

        List<Route> routes = getUserRoutes(userID);
        if(routes.size() == 0) {
            Log.v(TAG, String.format("Route %s is not found for this user %s ", route, userID));
            return;
        }

        for (Route r : routes) {
            if (r.equals(route)) {
                r.setFavorite(true);
                return;
            }
        }
    }

    public Boolean getRouteFavorite(UserID userID, Route route) {
        List<Route> routes = getUserRoutes(userID);
        for(Route __route : routes) {
            if(__route.getName().equals(route.getName())) {
                return __route.getIsFavorite();
            }
        }
        Log.v(TAG, String.format("Route %s is not found for this user %s ", route, userID));
        return false;
    }



    @Override
    public TeamID getUsersTeamID(UserID userID) {
        return localExistingUserMap.get(userID).getTeamID();
    }

    @Override
    public List<Route> getUserRoutes(UserID userID) {
        return (localExistingUserMap.get(userID)).getRoutes();
    }

    @Override
    public void addUserRoute(UserID userID, Route route) {
        UserData userData = localExistingUserMap.get(userID);
        userData.addRoute(route);
    }


    public List<Route> getExtraFavRoutes(UserID userID) {
        return (localExistingUserMap.get(userID)).getExtraFavoriteRoutes();
    }
    public void addExtraFavRoutes(UserID userID, Route fav_route) {
        UserData userData = localExistingUserMap.get(userID);
        userData.addExtraFavoriteRoute(fav_route);
    }


    @Override
    public boolean getWalkPlanExists(TeamID teamID) {
        return localWalkPlanMap.containsKey(teamID);
    }

    @Override
    public void addWalkPlan(WalkPlan walkPlan) {
        TeamID teamID = walkPlan.getTeamID();
        localWalkPlanMap.put(teamID, walkPlan);
    }

    @Override
    public WalkPlan getWalkPlanData(TeamID teamID) {
        return localWalkPlanMap.get(teamID);
    }

    @Override
    public void withdrawWalk(TeamID teamID) {
        localWalkPlanMap.remove(teamID);
    }

    @Override
    public void scheduleWalk(TeamID teamID) {
        WalkPlan plan = localWalkPlanMap.get(teamID);
        plan.setScheduled();
    }

    @Override
    public void setWalkRSVP(UserID userID, WalkRSVPStatus status) {

        TeamID teamID = this.getUsersTeamID(userID);

        WalkPlan plan = localWalkPlanMap.get(teamID);
        plan.setRSVPStatus(userID, status);
    }

    public static String encodeKey(String key) {
        return key.replace(".", "_");
    }

    public static String decodeKey(String key) {
        return key.replace("_", ".");
    }

}
