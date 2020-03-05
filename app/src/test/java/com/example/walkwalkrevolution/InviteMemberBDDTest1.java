package com.example.walkwalkrevolution;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;


import org.junit.Before;
import org.junit.Test;


import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.walkwalkrevolution.Fitness.FitnessService;
import com.example.walkwalkrevolution.Fitness.FitnessServiceFactory;
import com.example.walkwalkrevolution.R;
import com.example.walkwalkrevolution.ui.home.HomeFragment;
import com.example.walkwalkrevolution.ui.team.AddTeammatePromptActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.Context.MODE_PRIVATE;
import static org.junit.Assert.*;

import static org.junit.Assert.*;
import static com.google.common.truth.Truth.assertThat;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.*;

import static org.junit.Assert.*;

@RunWith(value = AndroidJUnit4.class)
public class InviteMemberBDDTest1{

    private Intent intent;
    final String GIVEN = "Given that the user try to add teammate\n" +
                         "And that the user click the + button\n";
    final String WHEN = "When the user enter all the required information\n";
    final String THEN = "Then the user will be allowed to send the information\n";

    @Before
    public void setUp() throws Exception {
        intent = new Intent(ApplicationProvider.getApplicationContext(), AddTeammatePromptActivity.class);
    }

    @Test
    public void success(){
        ActivityScenario<AddTeammatePromptActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            TextView fn = activity.findViewById(R.id.editText_first_name);
            TextView ln = activity.findViewById(R.id.editText_last_name);
            TextView gm = activity.findViewById(R.id.editText_gmail);
            fn.setText("First Name");
            ln.setText("Last Name");
            gm.setText("G mail");
            ImageView b = activity.findViewById(R.id.imageView_send);
            b.performClick();
            assertThat(activity.checkAllRequiredInfo()).isTrue();
        });
    }

}