package h_mal.appttude.com.driver.user;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import h_mal.appttude.com.driver.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EspressoTestTwo {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void espressoTestTwo() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fields_holder),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("h.malik144.au@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fields_holder),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("crack167"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password), withText("crack167"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fields_holder),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(pressImeActionButton());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button = onView(
                allOf(withId(R.id.driver),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        0),
                                0),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.support.design.widget.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction cardView = onView(
                allOf(withId(R.id.car),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        1),
                                1),
                        isDisplayed()));
        cardView.perform(click());

        ViewInteraction cardView2 = onView(
                allOf(withId(R.id.vehicle_prof),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        1),
                                0),
                        isDisplayed()));
        cardView2.perform(click());

        pressBack();

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.driver), withText("Driver Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction cardView3 = onView(
                allOf(withId(R.id.driver_prof),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        1),
                                0),
                        isDisplayed()));
        cardView3.perform(click());

        pressBack();

        ViewInteraction cardView4 = onView(
                allOf(withId(R.id.private_hire),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.container),
                                        1),
                                1),
                        isDisplayed()));
        cardView4.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("View/Edit"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton2.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
