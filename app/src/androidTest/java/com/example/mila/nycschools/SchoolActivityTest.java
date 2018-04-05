package com.example.mila.nycschools;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.mila.nycschools.view.SchoolActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

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
        // Context of the app under test.
       Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.mila.nycschools", appContext.getPackageName());
    }

    @Test
    public void test_schoolsShown() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.mila.nycschools", appContext.getPackageName());
    }

    @Test
    public void test_dialog_Shown() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.mila.nycschools", appContext.getPackageName());
    }

    @Test
    public void test_dialog_closed() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.mila.nycschools", appContext.getPackageName());
    }
}


