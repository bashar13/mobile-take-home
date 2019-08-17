package com.bashar.rickmortyepisodes;

import android.support.test.rule.ActivityTestRule;
import android.view.View;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity activity = null;
    private static final String LAST_ITEM_NAME = "The Rickchurian Mortydate";
    private static final String FIRST_ITEM_NAME = "Pilot";


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
        View view = activity.findViewById(R.id.episodeListView);
        assertNotNull(view);
    }

    @Test
    public void clickOnListItemOpenCharacters() throws Exception {
        onView(withId(R.id.episodeListView)).perform(click());
        onView(withId(R.id.tabs)).check(matches(isDisplayed()));
    }

    @Test
    public void lastItemNotDisplayed() {
        onView(withText(LAST_ITEM_NAME)).check(doesNotExist());
    }
}