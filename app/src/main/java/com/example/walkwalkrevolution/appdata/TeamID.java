package com.example.walkwalkrevolution.appdata;

public class TeamID {
    private String email;

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
}
