package com.example.dailymood2020;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.service.chooser.ChooserTarget;
import android.view.View;
import android.widget.ListView;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.dailymood2020.activities.ActivityMoodChoice;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ActivityMoodChoiceTests {

    private void clearSharedPrefs(Context context) {
        SharedPreferences prefs =
                context.getSharedPreferences(Config.prefsFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    @Rule
    public ActivityTestRule<ActivityMoodChoice> activityRule =
            new ActivityTestRule<ActivityMoodChoice>(ActivityMoodChoice.class) {
                @Override
                protected void beforeActivityLaunched() {
                    clearSharedPrefs(InstrumentationRegistry.getInstrumentation().getTargetContext());
                    super.beforeActivityLaunched();
                }
            };

    @Test
    public void swipeDownProducesSadderView() {
        onView(withId(R.id.super_happy_view)).perform(swipeDown());
        onView(withId(R.id.super_happy_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.happy_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void swipeUpTest() {
        swipeDownProducesSadderView();

        onView(withId(R.id.happy_view)).perform(swipeUp());
        onView(withId(R.id.super_happy_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.happy_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

    @Test
    public void swipeToBottom() {
        onView(withId(R.id.super_happy_view)).perform(swipeDown());
        onView(withId(R.id.super_happy_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.happy_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withId(R.id.happy_view)).perform(swipeDown());
        onView(withId(R.id.happy_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.normal_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withId(R.id.normal_view)).perform(swipeDown());
        onView(withId(R.id.normal_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.disappointed_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withId(R.id.disappointed_view)).perform(swipeDown());
        onView(withId(R.id.disappointed_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.sad_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void swipeOffOfBottomEndOfMoodArray() {
        swipeToBottom();

        onView(withId(R.id.sad_view)).perform(swipeDown());
        onView(withId(R.id.sad_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    }


    @Test
    public void swipeToTop() {
        swipeToBottom();

        onView(withId(R.id.sad_view)).perform(swipeUp());
        onView(withId(R.id.disappointed_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.sad_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.disappointed_view)).perform(swipeUp());
        onView(withId(R.id.normal_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.disappointed_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.normal_view)).perform(swipeUp());
        onView(withId(R.id.happy_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.normal_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.happy_view)).perform(swipeUp());
        onView(withId(R.id.super_happy_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.happy_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }


    @Test
    public void swipeOffOfTopEndOfArray() {
        onView(withId(R.id.super_happy_view)).perform(swipeUp());
        onView(withId(R.id.super_happy_view)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void navigateToHistory() {
        onView(withId(R.id.button_history)).perform(click());
        onView(withId(R.id.root_container)).check(matches(isDisplayed()));
    }

    @Test
    public void testNoteDialog() {
        onView(withId(R.id.button_note)).perform(click());
        onView(withText(R.string.enter_a_note)).check(matches(isDisplayed()));
    }

}
