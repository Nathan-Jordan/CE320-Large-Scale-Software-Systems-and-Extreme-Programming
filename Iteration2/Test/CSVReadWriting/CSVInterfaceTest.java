package CSVReadWriting;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CSVInterfaceTest {

    ArrayList<BookingDate> bookings;
    CSVInterface csvInterface = new CSVInterface();

    @Test
    public void readDataFromFile() {
        readFromTestFile();

        checkDaveDavids();
        checkNathanJordan();
        checkJoeBlogs();
        checkSarahPeters();
    }

    private void readFromTestFile() {
        File bookingFile = new File("DataForUnitTests.csv");
        csvInterface.readDataFromFile(bookingFile);

        bookings = csvInterface.getBookings();
    }

    private void checkDaveDavids() {
        assertBookingIsProvisionallyBooked(bookings.get(0).getHouseOne());
        assertBookingIsProvisionallyBooked(bookings.get(1).getHouseOne());
        assertBookingIsProvisionallyBooked(bookings.get(2).getHouseOne());

        assertEquals(bookings.get(0).getHouseOne().getFirstName(), "Dave");
    }
    private void checkNathanJordan() {
        assertBookingIsFormSubmitted(bookings.get(4).getHouseOne());

        assertEquals(bookings.get(4).getHouseOne().getFirstName(), "Nathan");
    }
    private void checkJoeBlogs() {
        assertBookingIsConfirmed(bookings.get(6).getHouseOne());

        assertEquals(bookings.get(6).getHouseOne().getFirstName(), "Joe");
    }
    private void checkSarahPeters() {
        assertBookingIsConfirmed(bookings.get(0).getHouseTwo());
        assertBookingIsConfirmed(bookings.get(1).getHouseTwo());

        assertEquals(bookings.get(0).getHouseTwo().getFirstName(), "Sarah");
    }

    private void assertBookingIsProvisionallyBooked(HouseBooking booking) {
        assertTrue(booking.isProvisionallyBooked());
    }
    private void assertBookingIsConfirmed(HouseBooking booking) {
        assertTrue(booking.isConfirmed());
    }
    private void assertBookingIsFormSubmitted(HouseBooking booking) {
        assertTrue(booking.isFormSubmitted());
    }


    @Test
    public void testAddingExtraDates() {
        readFromTestFile();

        int previousNumberOfDates = bookings.size();

        csvInterface.addNewDates();

        assertNotEquals(previousNumberOfDates, bookings.size(), "Expect amount of dates to be different");
    }
}