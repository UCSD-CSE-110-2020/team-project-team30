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

import java.util.List;
import java.util.Map;

public class FirebaseInteractor implements ApplicationStateInteractor {

    private static final String TAG = "FirebaseInteractor";

    private Context context;

    private FirebaseFirestore firestore;
    private CollectionReference collection_users;
    private CollectionReference collection_walkPlans;

    public FirebaseInteractor(Context context) {
        firestore = FirebaseFirestore.getInstance();
        collection_users = firestore.collection("users");
        collection_walkPlans = firestore.collection("walk_plans");

        this.context = context;
    }

    @Override
    public String getLocalUserEmail() {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);

        return prefs.getString("current_user_id", null);
    }

    @Override
    public boolean isUserEmailTaken(String email) {
//
//        AtomicBoolean emailTaken = new AtomicBoolean(false);
//        AtomicBoolean queryDone = new AtomicBoolean(false);
//
//        Log.d(TAG, "Query about to execute");
//
//        Task<QuerySnapshot> emailTakenQuery =
//        collection_users.whereEqualTo(UserData.KEY_USERID, email)
//        .get();
//
//        emailTakenQuery.addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    Log.d(TAG, "User found!");
//                    emailTaken.set(true);
//                    queryDone.set(true);
//                }
//            } else {
//                Log.d(TAG, "User not found");
//                emailTaken.set(false);
//                queryDone.set(true);
//            }
//        });
//
//        try {
//            Tasks.await(emailTakenQuery, 5000, TimeUnit.MILLISECONDS);
//        }
//        catch (TimeoutException t) {
//            Log.e(TAG, t.getMessage());
//        }
//        catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//        }
//
//        Log.d(TAG, "Exit while loop");
//
//        return emailTaken.get();

        return false;
    }

    @Override
    public void addUserToDatabase(UserID userID, UserData userData) {
        Log.d(TAG, String.format("About to add user %s to firestore", userID.toString()));

        Map<String, Object> newUserData = userData.toFirebaseDoc();

        if (newUserData == null) {
            Log.e(TAG, "newUserData is null, not putting data to firestore");
            return;
        }

        DocumentReference userDocument = collection_users.document(userID.toString());
        userDocument.set(newUserData)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + userID.toString()))
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
        DocumentReference userDocument = collection_users.document(userID.toString());

        userDocument.update(UserData.KEY_TEAM_INV, teamID.toString())
                .addOnSuccessListener(documentReference -> Log.d(TAG, String.format("Invited user %s to team %s ", userID, teamID)))
                .addOnFailureListener(e -> Log.w(TAG, "Error inviting user to team, ", e));
    }

    @Override
    public void resetUserTeamInvite(UserID userID) {
        DocumentReference userDocument = collection_users.document(userID.toString());

        userDocument.update(UserData.KEY_TEAM_INV, null)
                .addOnSuccessListener(documentReference -> Log.d(TAG, String.format("Successfully reset invite for user %s", userID)))
                .addOnFailureListener(e -> Log.w(TAG, "Error in clearing invite for user", e));
    }

    @Override
    public void addUserToTeam(UserID userID, TeamID teamID) {
        DocumentReference userDocument = collection_users.document(userID.toString());

        userDocument.update(UserData.KEY_TEAMID, teamID.toString())
                .addOnSuccessListener(documentReference -> Log.d(TAG, String.format("Added user %s to team %s ", userID, teamID)))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding user to team, ", e));
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
        DocumentReference userDocument = collection_users.document(userID.toString());
        userDocument.update(UserData.KEY_ROUTES, FieldValue.arrayUnion(route))
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Added route " + route.getName()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding route", e));
    }

    @Override
    public boolean getWalkPlanExists() {
        return false;
    }

    @Override
    public void addWalkPlan(WalkPlan walkPlan) {
        collection_walkPlans.add(walkPlan.toFirebaseDoc());
    }

    @Override
    public WalkPlan getWalkPlanData(TeamID teamID) {
        return null;
    }

    @Override
    public void withdrawWalk(TeamID teamID) {
        Task<QuerySnapshot> walkPlanExistsQuery =
                collection_walkPlans.whereEqualTo(WalkPlan.KEY_TEAM_ID, teamID.toString())
                        .get();

        walkPlanExistsQuery.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, String.format("WalkPlan for team %s found:", teamID));
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, "WalkPlan " + document.getId() + " => " + document.getData());

                    DocumentReference walkPlanDoc = collection_walkPlans.document(document.getId());
                    walkPlanDoc.delete()
                            .addOnSuccessListener(documentReference -> Log.d(TAG, String.format("Successfully withdrew WalkPlan")))
                            .addOnFailureListener(e -> Log.w(TAG, "Error in withdrawing WalkPlan", e));
                }
            } else {
                Log.w(TAG, String.format("WalkPlan for team %s not found, can't schedule. Ignoring", teamID));
            }
        });
    }

    @Override
    public void scheduleWalk(TeamID teamID) {
        Task<QuerySnapshot> walkPlanExistsQuery =
        collection_walkPlans.whereEqualTo(WalkPlan.KEY_TEAM_ID, teamID.toString())
        .get();

        walkPlanExistsQuery.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, String.format("WalkPlan for team %s found:", teamID));
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(TAG, "WalkPlan " + document.getId() + " => " + document.getData());

                    DocumentReference walkPlanDoc = collection_walkPlans.document(document.getId());
                    walkPlanDoc.update(WalkPlan.KEY_IS_SCHEDULED, true)
                            .addOnSuccessListener(documentReference -> Log.d(TAG, String.format("Successfully scheduled WalkPlan")))
                            .addOnFailureListener(e -> Log.w(TAG, "Error in scheduling WalkPlan", e));
                }
            } else {
                Log.w(TAG, String.format("WalkPlan for team %s not found, can't schedule. Ignoring", teamID));
            }
        });
    }

    @Override
    public void setWalkRSVP(UserID userID, String status) {

    }
}
