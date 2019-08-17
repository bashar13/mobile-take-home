package com.bashar.rickmortyepisodes;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterInfoActivityTest {

    @Rule
    public ActivityTestRule<CharacterInfoActivity> activityTestRule = new ActivityTestRule<CharacterInfoActivity>(CharacterInfoActivity.class);
    private CharacterInfoActivity activity = null;

    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();
    }

    @Test
    public void testLaunchActivity() {

        View view = activity.findViewById(R.id.charAvatar);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        activity = null;
    }
}