package com.example.walkwalkrevolution.appdata;

import android.app.DownloadManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.Teammate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FirebaseInteractor implements ApplicationStateInteractor {

    private static final String TAG = "FirebaseInteractor";
    private FirebaseFirestore firestore;

    public FirebaseInteractor() {
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public String getMyEmail() {
        return null;
    }

    @Override
    public boolean isUserEmailTaken(String email) {
        return false;
    }

    @Override
    public void addUserToDatabase(UserID id, UserData userData) {
        // TODO TESTING STUFF
        firestore = FirebaseFirestore.getInstance();

        CollectionReference c_users = firestore.collection("users");

        Log.d(TAG, "About to add data to firebase");

        Map<String, Object> newUserData = userData.toFirebaseDoc();

        if (newUserData == null) {
            Log.e(TAG, "Oops, newUserData is null");
            return;
        }

        c_users.document(id.toString())
                .set(newUserData)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + id.toString()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));;
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

    @Override
    public List<Teammate> getTeammates(UserID userID) {
        return null;
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
