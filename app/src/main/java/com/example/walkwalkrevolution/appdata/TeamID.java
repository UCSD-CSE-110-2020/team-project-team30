package com.example.walkwalkrevolution.appdata;

public class TeamID implements Comparable {
    private final String email;

    public TeamID(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TeamID))
            return false;

        TeamID otherID = (TeamID) o;

        return this.email.equals(otherID.email);
    }

    @Override
    public String toString() {
        return this.email;
    }

    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(((TeamID) o).toString());
    }
}
