package exampleapp.app2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by str2n on 2017/3/31.
 */
public class ReIndex2Test {
    @Test
    public void getHour() throws Exception {
        System.out.println(ReIndex2.getHour("2008-08-05T03:11:55.570"));
    }

    @Test
    public void getDay() throws Exception {
        System.out.println(ReIndex2.getDay("2008-08-05T03:11:55.570"));
    }

}