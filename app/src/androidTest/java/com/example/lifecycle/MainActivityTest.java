package com.example.lifecycle;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testBackgroundColorChange_Red() {
        onView(withId(R.id.editText1)).perform(clearText(), typeText("hoang"));
        onView(withId(R.id.myScreen1)).check(matches(withBackgroundColor(0xffff0000)));
    }

    @Test
    public void testBackgroundColorChange_Green() {
        onView(withId(R.id.editText1)).perform(clearText(), typeText("huy"));
        onView(withId(R.id.myScreen1)).check(matches(withBackgroundColor(0xff00ff00)));
    }

    @Test
    public void testBackgroundColorChange_Blue() {
        onView(withId(R.id.editText1)).perform(clearText(), typeText("phuong"));
        onView(withId(R.id.myScreen1)).check(matches(withBackgroundColor(0xff0000ff)));
    }

    @Test
    public void testBackgroundColorChange_White() {
        onView(withId(R.id.editText1)).perform(clearText(), typeText("thuan"));
        onView(withId(R.id.myScreen1)).check(matches(withBackgroundColor(0xffffffff)));
    }

    @Test
    public void testBackgroundColorChange_Purple() {
        onView(withId(R.id.editText1)).perform(clearText(), typeText("quan"));
        onView(withId(R.id.myScreen1)).check(matches(withBackgroundColor(0xFF6200EE)));
    }

    /**
     * Custom Matcher to check the background color of a View.
     */
    public static Matcher<View> withBackgroundColor(final int expectedColor) {
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with background color: " + String.format("#%08X", expectedColor));
            }

            @Override
            protected boolean matchesSafely(View view) {
                Drawable background = view.getBackground();
                if (background instanceof ColorDrawable) {
                    return ((ColorDrawable) background).getColor() == expectedColor;
                }
                return false;
            }
        };
    }
}
