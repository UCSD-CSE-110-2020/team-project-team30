package com.example.walkwalkrevolution.appdata;

/**
 * This class acts as a "key" for locating UserData objects within the application state database.
 *
 * Currently it is a wrapper for user emails because all user emails must be unique.
 */
public class UserID {
    private final String email;

    public UserID(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserID))
            return false;

        UserID otherID = (UserID) o;

        return this.email.equals(otherID.email);
    }

    @Override
    public String toString() {
        return this.email;
    }
}
