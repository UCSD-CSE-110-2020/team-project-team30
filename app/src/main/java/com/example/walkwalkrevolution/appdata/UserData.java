package com.example.walkwalkrevolution.appdata;

import com.example.walkwalkrevolution.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores all information about a specific user, including personal routes and team ID.
 */
public class UserData {

    // Keys for firestore data storage
    public static String KEY_FIRST_NAME = "first_name";
    public static String KEY_LAST_NAME  = "last_name";
    public static String KEY_EMAIL      = "email";
    public static String KEY_USERID     = "user_id";
    public static String KEY_TEAMID     = "team_id";
    public static String KEY_TEAM_INV   = "team_invite";
    public static String KEY_ROUTES     = "routes";

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private UserID userID;

    // teamID starts as null (not in a team yet). When user is added to a team, this gets set to
    // whoever sent the team invite.
    private TeamID teamID;

    private TeamID pendingTeamInvite;

    private List<Route> routes;

    public UserData(String email, String password, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

        this.userID = new UserID(email);
        this.routes = new ArrayList<Route>();

        this.teamID = null;
        this.pendingTeamInvite = null;
    }

    public UserData() {
        this(null, null, null, null);
    }

    public static UserData deserializeFromFirestore(Map<String, Object> data) {

        String email = (String) data.get(KEY_EMAIL);
        String password = "null";
        String firstName = (String) data.get(KEY_FIRST_NAME);
        String lastName = (String) data.get(KEY_LAST_NAME);

        UserData userData = new UserData(email, password, firstName, lastName);
        userData.setRoutes(Route.deserializeListFromFirestore((List<Map<String, Object>>) data.get(KEY_ROUTES)));

        String teamID = (String) data.get(KEY_TEAMID);
        userData.setTeamID(teamID == null ? null : new TeamID(teamID));

        String pendInv = (String) data.get(KEY_TEAM_INV);
        userData.setPendingTeamInvite(pendInv == null ? null : new TeamID(pendInv));

        return userData;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public UserID getUserID() {
        return userID;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public TeamID getTeamID() {
        return teamID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    public void setTeamID(TeamID teamID) {
        this.teamID = teamID;
    }

    public void setPendingTeamInvite(TeamID pendingTeamInvite) {
        this.pendingTeamInvite = pendingTeamInvite;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return String.format("{%s %s}", this.firstName, this.lastName);
    }

    /*
     * Return key-value format for putting data to firebase
     */
    public Map<String, Object> toFirebaseDoc() {
        Map<String, Object> firebaseDocData = new HashMap<>();

        firebaseDocData.put(KEY_FIRST_NAME, this.firstName);
        firebaseDocData.put(KEY_LAST_NAME, this.lastName);
        firebaseDocData.put(KEY_EMAIL, this.email);
        firebaseDocData.put(KEY_USERID, this.userID == null ? null : this.userID.toString());
        firebaseDocData.put(KEY_TEAMID, this.teamID == null ? null : this.teamID.toString());
        firebaseDocData.put(KEY_TEAM_INV, this.pendingTeamInvite);
        firebaseDocData.put(KEY_ROUTES, this.routes);

        return firebaseDocData;
    }
}
