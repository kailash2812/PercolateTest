package com.percolate.codetest;


import android.test.ActivityInstrumentationTestCase2;

import com.percolate.codetest.activity.MainActivity;


/**
 * Test Activity to do functional testing for MainActivity
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }

}