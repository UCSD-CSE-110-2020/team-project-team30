package com.example.walkwalkrevolution.ui.team;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.walkwalkrevolution.Login;
import com.example.walkwalkrevolution.MainActivity;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.Route;
import com.example.walkwalkrevolution.Teammate;
import com.example.walkwalkrevolution.appdata.ApplicationStateInteractor;
import com.example.walkwalkrevolution.appdata.FirebaseInteractor;
import com.example.walkwalkrevolution.appdata.MockInteractor;
import com.example.walkwalkrevolution.appdata.TeamID;
import com.example.walkwalkrevolution.appdata.UserData;
import com.example.walkwalkrevolution.appdata.UserID;
import com.example.walkwalkrevolution.appdata.WalkPlan;
import com.example.walkwalkrevolution.appdata.WalkRSVPStatus;

import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {
    private static final String TAG = "TeamFragment";
    private String[] myStringArray;
    private TeamViewModel teamViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        teamViewModel =
                ViewModelProviders.of(this).get(TeamViewModel.class);
        root = inflater.inflate(R.layout.fragment_team, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("TeamFragment", "onActivityCreated called");

        ListView listView = (ListView) root.findViewById(R.id.teamListView);

        ApplicationStateInteractor firebase = MainActivity.getAppDataInteractor();
        List<UserID> teammateIDList = firebase.getTeamMemberIDs(firebase.getLocalUserID());
        List<Teammate> teammatesList = new ArrayList<Teammate>();
        for(UserID userID : teammateIDList ) {
            UserData userData = firebase.getUserData(userID);
            Teammate teammate = new Teammate(userData.getFirstName(), userData.getLastName(), userData.getColor());
            teammatesList.add(teammate);
        }

        if (!teammatesList.isEmpty()) {
            root.findViewById(R.id.textView_no_teammates).setVisibility(View.INVISIBLE);
        }

        ArrayAdapter myAdapter = new TeammateAdapter(root.getContext(), teammatesList);
        listView.setAdapter(myAdapter);

        Button addNewTeammateButton = (Button) root.findViewById(R.id.button_add_new_teammate);
        addNewTeammateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addNewTeammateButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.nav_default_enter_anim));
                launchPromptActivity();
            }
        });

        //If user get invited and it's not already in a team, prompt
        ApplicationStateInteractor appdata = MainActivity.getAppDataInteractor();
        UserID thisID = appdata.getLocalUserID();
        if(appdata.getUserTeamInviteStatus(thisID) != null && !appdata.getIsUserInAnyTeam(thisID)){
            launchJoinTeamActivity();
        }

        root.findViewById(R.id.btn_initFirebase).setOnClickListener(v -> initFirebaseData(v));
        root.findViewById(R.id.btn_runTests).setOnClickListener(v -> performFirebaseTests(v));
        root.findViewById(R.id.btn_login).setOnClickListener(v -> startActivity(new Intent(getActivity(), Login.class)));
    }

    public void launchPromptActivity(){
        Intent intent = new Intent(getActivity(), AddTeammatePromptActivity.class);
        startActivity(intent);
    }

    private void launchJoinTeamActivity(){
        Intent intent = new Intent(getActivity(), JoinTeamPromptActivity.class);
        startActivity(intent);
    }

    public static void initFirebaseData(View v) {
        // TODO George's Testing Code, DONT PUSH or call in production
        ApplicationStateInteractor appdata = MainActivity.getAppDataInteractor();

        UserData ariannaUser = new UserData("amartinez@gmail.com", "notAdmin", "Arianna", "Martinez");
        UserData userEllen   = new UserData("eanderson@gmail.com", "notAdmin", "Ellen", "Anderson");
        UserData userDeondre = new UserData("dwilliams@gmail.com", "notAdmin", "Deondre", "Williams");
        UserData userSara = new UserData("ssmith@gmail.com", "notAdmin", "Sara", "Smith");
        UserData userJiayi = new UserData("jiz546@ucsd.edu", "sandiego", "Jiayi", "Zhang");

        UserID ariannaID = ariannaUser.getUserID();
        UserID ellenID  = userEllen.getUserID();
        UserID deonID   =  userDeondre.getUserID();
        UserID saraID   =  userSara.getUserID();
        UserID jiayiID = userJiayi.getUserID();

        TeamID teamID = new TeamID(ariannaID.toString());
        appdata.addUserToDatabase(ariannaID, ariannaUser);
        appdata.addUserToDatabase(ellenID, userEllen);
        appdata.addUserToDatabase(deonID, userDeondre);
        appdata.addUserToDatabase(saraID, userSara);
        appdata.addUserToDatabase(jiayiID, userJiayi);

        appdata.addUserToTeam(ariannaID, teamID);
        appdata.addUserToTeam(ellenID, teamID);
        appdata.addUserToTeam(deonID, teamID);
        appdata.addUserToTeam(saraID, teamID);
        appdata.addUserToTeam(jiayiID, teamID);

        Route r1 = new Route("Pepper Canyon", "01/22/2020", "PCYNH");
        Route r2 = new Route("Gilman Drive", "01/25/2020", "Starbucks PC");
        Route r3 = new Route("Warren Dorms", "01/22/2020", "PCYNH");
        Route r4 = new Route("Tecolote Canyon", "04/22/2020", "Appfolio");

        r3.setFeatureStreetTrail(2);
        r3.setFeatureFlatHilly(1);
        r3.setFeatureEven(2);
        r3.setFeatureDifficulty(3);
        r3.setFeatureLoop(1);
        r3.setNotes("Very scenic, great canyon view, dorms look nice");
        r3.setFavorite(true);

        appdata.addUserRoute(ariannaID, r1);
        appdata.addUserRoute(deonID, r2);
        appdata.addUserRoute(ellenID, r3);
        appdata.addUserRoute(saraID, r4);

        appdata.addExtraFavRoutes(saraID, r1);

        List<UserID> teamMembers = new ArrayList<>();
        teamMembers.add(deonID);
        teamMembers.add(ellenID);
        teamMembers.add(saraID);
        teamMembers.add(jiayiID);

        WalkPlan walkPlan = new WalkPlan(r3, "04/01/2020", "16:20", ariannaID, teamID, teamMembers);
        appdata.addWalkPlan(walkPlan);
    }

    private void performFirebaseTests(View v) {

        // TODO George's Testing Code, DONT PUSH or call in production
        ApplicationStateInteractor appdata = MainActivity.getAppDataInteractor();

        UserID ariannaID = new UserID("amartinez@gmail.com");
        UserID ellenID   = new UserID("eanderson@gmail.com");
        UserID deonID    =  new UserID("dwilliams@gmail.com");
        UserID saraID    =  new UserID("ssmith@gmail.com");

        UserID georgeID = new UserID("gtroulis@ucsd.edu");

        TeamID teamID = new TeamID(ariannaID.toString());

        //appdata.withdrawWalk(teamID);

        if (appdata.isUserEmailTaken(ariannaID.toString())) {
            Log.d(TAG, String.format("User %s exists!", ariannaID));

            Log.d(TAG, "Routes of Arianna:");
            for (Route r : appdata.getUserRoutes(ariannaID)) {
                Log.d(TAG, r.toString());
            }

            appdata.setWalkRSVP(ellenID, WalkRSVPStatus.BAD_ROUTE);
            appdata.setWalkRSVP(deonID, WalkRSVPStatus.BAD_TIME);
        }
    }
}
