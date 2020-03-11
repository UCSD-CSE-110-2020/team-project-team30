package com.example.walkwalkrevolution;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.walkwalkrevolution.ui.team.AddTeammatePromptActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.truth.Truth.assertThat;

@RunWith(value = AndroidJUnit4.class)
public class InviteMemberBDDTest2 {

    private Intent intent;
    final String GIVEN = "Given that the user try to add teammate\n" +
                         "And that the user click the + button\n";
    final String WHEN = "When the user did not enter all the required information\n";
    final String THEN = "Then the user will not be allowed to send the information\n";

    @Before
    public void setUp() throws Exception {
        intent = new Intent(ApplicationProvider.getApplicationContext(), AddTeammatePromptActivity.class);
        System.out.println(GIVEN);
    }

    @Test
    public void fail(){
        ActivityScenario<AddTeammatePromptActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView fn = activity.findViewById(R.id.editText_first_name);
            TextView ln = activity.findViewById(R.id.editText_last_name);
            TextView gm = activity.findViewById(R.id.editText_gmail);
            ImageView b = activity.findViewById(R.id.imageView_send);
            b.performClick();
            System.out.println(WHEN);
            assertThat(activity.checkAllRequiredInfo()).isFalse();
            System.out.println(THEN);
        });
    }
}
