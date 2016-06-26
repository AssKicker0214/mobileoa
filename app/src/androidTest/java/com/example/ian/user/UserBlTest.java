package com.example.ian.user;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;
import junit.framework.TestResult;

import bl.UserBL;

/**
 * Created by Ian on 2016/6/25.
 */
public class UserBlTest extends TestCase {

    private UserBL ubl;

    public void setUp(){
        ubl = new UserBL();
    }

    @SmallTest
    public void testRegister(){
        int before = ubl.getRecordCount();
        String[] att = {"",""};
        int after = ubl.getRecordCount();
        assertEquals(before+1, after);
    }
}
