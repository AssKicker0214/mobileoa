package com.example.ian.other;

import junit.framework.TestCase;

import bl.Rounder;

/**
 * Created by Ian on 2016/6/27.
 */
public class RounderTest extends TestCase {

    public void testZero(){
        double input = 1.0;
        double output = Rounder.round(input, 2);
        assertEquals(1.0, output);
    }

    public void test1(){
        assertEquals(1.33, Rounder.round(1.3345, 2));
    }
}
