package com.example.ian.business;

import android.util.Log;

import junit.framework.TestCase;
import junit.framework.TestResult;

import bl.BusinessBL;
import entity.OppoState;

/**
 * Created by Ian on 2016/6/26.
 */
public class BusinessBLTest extends TestCase {
    private BusinessBL bbl;

    public void setUp(){
        bbl = new BusinessBL();
    }

    public void testOppoRate(){
        double rate = bbl.getRateOfOppo(OppoState.NEGOTIATION);
        Log.e("business", rate+"%");
    }
}
