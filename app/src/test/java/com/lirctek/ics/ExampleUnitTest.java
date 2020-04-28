package com.lirctek.ics;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        double firstAngle=10;
        double secondAngle=350;
        double difference = secondAngle - firstAngle;
        while (difference < -180) difference += 360;
        while (difference > 180) difference -= 360;
        assertEquals(20, difference);
    }

     @Test
    public void calculateDifferenceBetweenAngles()
    {    double firstAngle=90;
         double secondAngle=350;
        double difference = secondAngle - firstAngle;
        while (difference < -180) difference += 360;
        while (difference > 180) difference -= 360;
        System.out.println(difference);

    }
}