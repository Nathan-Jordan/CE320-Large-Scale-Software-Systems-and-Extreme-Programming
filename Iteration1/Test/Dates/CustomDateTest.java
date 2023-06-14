package Dates;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomDateTest {

    @Test
    public void testDate() {
        CustomDate date = new CustomDate("05/04/2002");
        assertEquals(date.toString(), "05/04/2002");
    }

    @Test
    public void testVerbalName() {
        CustomDate date = new CustomDate("05/04/2002");
        assertEquals(date.getVerbalName(), "5 April 2002");
    }

    @Test
    public void testDateCompare() {
        String a = "05/04/2002";
        String b = "18/04/2004";
        assertTrue(CustomDate.isDate1AfterDate2(b,a));
    }

    public static void main(String[] args) {
        CustomDateTest test = new CustomDateTest();
        test.testDate();
        test.testVerbalName();
        test.testDateCompare();
        System.out.println("Passed custom date tests successfully");
    }

}
