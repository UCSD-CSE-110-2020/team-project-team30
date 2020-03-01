package com.example.walkwalkrevolution.appdata;

import com.example.walkwalkrevolution.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores all information about a specific user, including personal routes and team ID.
 */
public class UserData {
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

    public TeamID getTeamID() {
        return teamID;
    }

    @Override
    public String toString() {
        return String.format("{%s %s}", this.firstName, this.lastName);
    }
}
