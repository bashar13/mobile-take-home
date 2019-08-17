package com.bashar.rickmortyepisodes;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterListActivityTest {

    @Rule
    public ActivityTestRule<CharacterListActivity> activityTestRule = new ActivityTestRule<CharacterListActivity>(CharacterListActivity.class);
    private CharacterListActivity activity = null;

    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();
    }

    @After
    public void tearDown() throws Exception {
        activity = null;
    }

    @Test
    public void onCreate() {
        View view = activity.findViewById(R.id.tabs);
        assertNotNull(view);
    }
}