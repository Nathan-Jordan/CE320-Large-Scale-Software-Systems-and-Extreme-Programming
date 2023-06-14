package Calender;

import CSVReadWriting.CSVInterface;
import CSVReadWriting.HouseBooking;
import Dates.CustomDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ChoosyCalenderTest {


    ChoosyCalender calender;

    @BeforeEach
    public void init() {
        CSVInterface csvInterface = new CSVInterface();
        csvInterface.readDataFromFile(new File("DataForUnitTests.csv"));

        calender = new ChoosyCalender(new JFrame(), csvInterface.getBookings());
    }

    @Test
    public void testInitFrame() {
        calender.initFrame();

        assertEquals(1920, calender.getWidth());
        assertEquals(1080, calender.getHeight());
        assertTrue(calender.getLayout() instanceof BorderLayout);
    }

    @Test
    void testGoToNextMonthLastMonth() {
        calender.month = 12;
        calender.goToNextMonth();
        assertEquals(calender.month, 1);
    }

    @Test
    void testGoToPreviousMonthFirstMonth() {
        calender.month = 1;
        calender.goToPreviousMonth();
        assertEquals(calender.month, 12);
    }

    @Test
    void testGoToNextMonth() {
        calender.month = 6;
        calender.goToNextMonth();
        assertEquals(calender.month, 7);
    }

    @Test
    void testGoToPreviousMonth() {
        calender.month = 6;
        calender.goToPreviousMonth();
        assertEquals(calender.month, 5);
    }

    @Test
    void testGetMonth() {
        assertEquals(calender.getMonth(5), "MAY");
        assertEquals(calender.getMonth(1), "JANUARY");
        assertEquals(calender.getMonth(12), "DECEMBER");
    }

    @Test
    void testGetMonthLength() {
        assertEquals(calender.getMonthLength(1), 31);
        assertEquals(calender.getMonthLength(2), 28);
        assertEquals(calender.getMonthLength(4), 30);
    }

    @Test
    void testGetMonthBookings() {
        Map<CustomDate, HouseBooking> bookingsMap = calender.getMonthBookings(1, 2022);

        assertEquals(bookingsMap.size(), 9);
        assertEquals("Provisionally Booked", bookingsMap.get(new CustomDate("01/01/2022")).getStatus());
        assertEquals("Provisionally Booked", bookingsMap.get(new CustomDate("02/01/2022")).getStatus());
        assertEquals("Provisionally Booked", bookingsMap.get(new CustomDate("03/01/2022")).getStatus());
        assertNull(bookingsMap.get(new CustomDate("04/01/2022")).getStatus());
        assertEquals("Form Submitted", bookingsMap.get(new CustomDate("05/01/2022")).getStatus());
        assertNull(bookingsMap.get(new CustomDate("06/01/2022")).getStatus());
        assertEquals("Confirmed", bookingsMap.get(new CustomDate("07/01/2022")).getStatus());
        assertNull(bookingsMap.get(new CustomDate("08/01/2022")).getStatus());
        assertNull(bookingsMap.get(new CustomDate("09/01/2022")).getStatus());
    }
}