package com.example.walkwalkrevolution.appdata;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.walkwalkrevolution.Route;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseInteractor implements ApplicationStateInteractor {

    private String TAG = "FirebaseInteractor";

    private CollectionReference no_teams;
    private DocumentReference documentReference;

    public FirebaseInteractor(Context context) {
        FirebaseApp.initializeApp(context);
    }

    @Override
    public boolean isUserEmailTaken(String email) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection("teams")
                .document("no_team")
                .collection("members")
                .whereArrayContains("email", email);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, String.format("Query successful, email %s exists!", email));
                    Log.d(TAG, document.getId() + " => " + document.getData());
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });



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
