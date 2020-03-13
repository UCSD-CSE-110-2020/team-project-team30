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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FirebaseInteractor implements ApplicationStateInteractor {

    private static final String TAG = "FirebaseInteractor";

    private Context context;

    private FirebaseFirestore firestore;
    private CollectionReference collection_users;
    private CollectionReference collection_walkPlans;

    // Local copies of data on Firebase to be returned by getters
    // Updated asynchronously via listeners to Firestore data
    private Map<UserID, UserData> localExistingUserMap;

    private Map<TeamID, WalkPlan> localWalkPlanMap;

    public FirebaseInteractor(Context context) {
        firestore = FirebaseFirestore.getInstance();
        collection_users = firestore.collection("users");
        collection_walkPlans = firestore.collection("walk_plans");

        initLocalAppDataStructures();

        initFirestoreListeners();

        this.context = context;
    }

    private void initFirestoreListeners() {
        collection_users.addSnapshotListener((queryDocumentSnapshots, e) -> {
            localExistingUserMap.clear();
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                UserData data = UserData.deserializeFromFirestore(doc.getData());
                localExistingUserMap.put(data.getUserID(), data);
                Log.d(TAG+"_UserUpdateListenerCallback", "Updating user " + doc.getId() + " in local cache");
            }
        });

        collection_walkPlans.addSnapshotListener((queryDocumentSnapshots, e) -> {
            localWalkPlanMap.clear();
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                WalkPlan data = WalkPlan.deserializeFromFirestore(doc.getData());
                localWalkPlanMap.put(data.getTeamID(), data);
                Log.d(TAG+"_WalkPlanUpdateListenerCallback", "Updating WalkPlan " + doc.getId() + " in local cache");
            }
        });
    }

    private void initLocalAppDataStructures() {
        localExistingUserMap = Collections.synchronizedMap(new TreeMap<>());
        localWalkPlanMap = Collections.synchronizedMap(new TreeMap<TeamID, WalkPlan>());
    }

    @Override
    public String getLocalUserEmail() {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);

        return prefs.getString("current_user_id", null);
    }

    @Override
    public void setLocalUserEmail(String email) {
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("current_user_id", email);
        editor.commit();
    }

    @Override
    public boolean isUserEmailTaken(String email) {
        UserID userID = new UserID(email);
        return localExistingUserMap.containsKey(userID);
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
        return localExistingUserMap.get(userID).getTeamID() != null;
    }

    @Override
    public TeamID getUserTeamInviteStatus(UserID userID) {
        return localExistingUserMap.get(userID).getTeamInvite();
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
        List<UserID> teammates = new ArrayList<>();

        TeamID teamID = localExistingUserMap.get(userID).getTeamID();

        for (UserID teammateUserID : localExistingUserMap.keySet()) {
            if (teammateUserID.equals(userID))
                continue;

            UserData userData = localExistingUserMap.get(teammateUserID);
            if (userData.getTeamID().equals(teamID))
                teammates.add(teammateUserID);
        }

        return teammates;
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
        return localExistingUserMap.get(userID).getTeamID();
    }

    @Override
    public List<Route> getUserRoutes(UserID userID) {
        return (localExistingUserMap.get(userID)).getRoutes();
    }

    @Override
    public void addUserRoute(UserID userID, Route route) {
        DocumentReference userDocument = collection_users.document(userID.toString());
        userDocument.update(UserData.KEY_ROUTES, FieldValue.arrayUnion(route))
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Added route " + route.getName()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding route", e));
    }

    @Override
    public boolean getWalkPlanExists(TeamID teamID) {
        return localWalkPlanMap.containsKey(teamID);
    }

    @Override
    public void addWalkPlan(WalkPlan walkPlan) {
        TeamID teamID = walkPlan.getTeamID();
        collection_walkPlans.document(teamID.toString()).set(walkPlan.toFirebaseDoc());
    }

    @Override
    public WalkPlan getWalkPlanData(TeamID teamID) {
        return localWalkPlanMap.get(teamID);
    }

    @Override
    public void withdrawWalk(TeamID teamID) {
        Task<QuerySnapshot> walkPlanExistsQuery =
                collection_walkPlans.whereEqualTo(WalkPlan.KEY_TEAM_ID, teamID.toString())
                        .get();

        walkPlanExistsQuery.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.v(TAG, "WalkPlan " + document.getId() + " => " + document.getData());

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
                    Log.v(TAG, "WalkPlan " + document.getId() + " => " + document.getData());

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
    public void setWalkRSVP(UserID userID, WalkRSVPStatus status) {

        TeamID teamID = this.getUsersTeamID(userID);

        Task<QuerySnapshot> walkPlanExistsQuery =
                collection_walkPlans.whereEqualTo(WalkPlan.KEY_TEAM_ID, teamID.toString())
                        .get();

        walkPlanExistsQuery.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    DocumentReference walkPlanDoc = collection_walkPlans.document(document.getId());

                    String userStatusKey = String.format("%s.%s", WalkPlan.KEY_RSVP_STATUS, encodeKey(userID.toString()));
                    walkPlanDoc.update(userStatusKey, status)
                            .addOnSuccessListener(documentReference -> Log.d(TAG, String.format("Changed RSVP status of %s to %s", userID, status)))
                            .addOnFailureListener(e -> Log.w(TAG, String.format("Failed to change RSVP status of %s to %s", userID, status), e));
                }
            } else {
                Log.w(TAG, String.format("WalkPlan for team %s not found, can't schedule. Ignoring", teamID));
            }
        });
    }

    public static String encodeKey(String key) {
        return key.replace(".", "_");
    }

    public static String decodeKey(String key) {
        return key.replace("_", ".");
    }
}
