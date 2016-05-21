package com.example.ian.mobileoa;

import android.test.ActivityInstrumentationTestCase2;

import presentation.index.IndexActivity;

/**
 * Created by Ian on 2016/5/17.
 */
public class ListItemUITest extends ActivityInstrumentationTestCase2<IndexActivity> {
    public ListItemUITest() {
//        super(ListItemTestActivity.class);
        super(IndexActivity.class);
    }

    protected void setUp() throws Exception{
        super.setUp();
    }
}
