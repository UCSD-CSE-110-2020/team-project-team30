package com.example.walkwalkrevolution;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.walkwalkrevolution.ui.team.JoinTeamPromptActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(value = AndroidJUnit4.class)
public class AcceptInvitationBDDTest2 {

    private Intent intent;
    private Button accept;
    private Button decline;
    private TextView message;
    final String GIVEN = "Given that the user receive an invitation to join a team\n" +
                         "And that the user can see the option to accept or decline\n";
    final String WHEN = "When the user click decline\n";
    final String THEN = "Then the user will prompt with a message\n" +
                        "And that the user is taken to the home screen";

    @Before
    public void setUp() {
        intent = new Intent(ApplicationProvider.getApplicationContext(), JoinTeamPromptActivity.class);
        System.out.println(GIVEN);
    }

    @Test
    public void seeVisibleButton(){
        ActivityScenario<JoinTeamPromptActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            accept = activity.findViewById(R.id.btn_acc_invite);
            decline = activity.findViewById(R.id.btn_dec_invite);
            message = activity.findViewById(R.id.tv_invite_message);
        });
        assertEquals(View.VISIBLE, accept.getVisibility());
        assertEquals(View.VISIBLE, decline.getVisibility());
        assertEquals("Join the Team", message.getText().toString());
    }

    @Test
    public void clickDecline(){
        ActivityScenario<JoinTeamPromptActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            Button decInvite = activity.findViewById(R.id.btn_dec_invite);
            decInvite.performClick();
            System.out.println(WHEN);
            assertThat(activity.result).isEqualTo("Invitation Decline");
            System.out.println(THEN);
        });
    }
}
