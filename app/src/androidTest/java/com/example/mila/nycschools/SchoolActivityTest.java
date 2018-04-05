package com.example.mila.nycschools;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mila.nycschools.view.SchoolActivity;


import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.assertEquals;;
/**
 * Created by mila on 4/5/18.
 */

@RunWith(AndroidJUnit4.class)

public class SchoolActivityTest {

    @Rule
    public ActivityTestRule<SchoolActivity> mActivityRule = new ActivityTestRule<>(
            SchoolActivity.class);
    @Test
    public void useAppContext() {
       Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.mila.nycschools", appContext.getPackageName());
    }

    @Test
    public void test_schoolsShown() {
        boolean isEmpty=true;
        if (getItems() > 0){
            isEmpty=false;
        }
        assertEquals(false,isEmpty);
    }

    @Test
    public void test_dialog_Shown() {

        onView(withId(R.id.school_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.dialog)).check(matches(isDisplayed()));
    }

    @Test
    public void test_dialog_closed() {
        onView(withId(R.id.school_list)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));

        onView(withId(R.id.dialog)).perform(clickXY(-150, -400));
        //onView(withId(R.id.dialog)).check(matches(not(isDisplayed())));

    }

    @Test
    public void test_clickURL() {
        Intents.init();
        onView(withId(R.id.school_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        String url = getText(withId(R.id.url));
        onView(withId(R.id.url)).perform(click());
        Matcher<Intent> expectedIntent = allOf(hasAction(Intent.ACTION_VIEW), hasData(url));
        intended(expectedIntent);
        Intents.release();
    }

    @Test
    public void test_clickNumber() {
        Intents.init();
        onView(withId(R.id.school_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.phone_value)).perform(click());
        Matcher<Intent> expectedIntent = hasAction(Intent.ACTION_VIEW);
        intended(expectedIntent);
        Intents.release();
    }

    private int getItems(){
        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.school_list);
        return recyclerView.getAdapter().getItemCount();
    }

    public static ViewAction clickXY(final int x, final int y){
        return new GeneralClickAction(
                Tap.SINGLE,
                (view) -> {
                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);

                        final float screenX = screenPos[0] +x;
                        final float screenY = screenPos[1] + y;
                        float[] coordinates = {screenX, screenY};

                        return coordinates;
                    }
                ,
                Press.PINPOINT);
    }

    private String getText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

}


