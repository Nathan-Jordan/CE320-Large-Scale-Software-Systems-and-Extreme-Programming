package BookingSystem;

import CSVReadWriting.BookingDate;
import CSVReadWriting.CSVInterface;
import CSVReadWriting.HouseBooking;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BookingSystemTest {

    ArrayList<BookingDate> testBookings;
    BookingSystem testBookingSystem;

    @BeforeEach
    public void init() {
        CSVInterface csvInterface = new CSVInterface();
        csvInterface.readDataFromFile(new File("DataForUnitTests.csv"));

        testBookings = csvInterface.getBookings();
        testBookingSystem = new BookingSystem(testBookings);
    }

    @AfterEach
    public void teardown() {
        testBookings = null;
        testBookingSystem = null;
    }

    @Test
    public void bookingAvailableTrue() {
        setNewBookingThatIsNotAlreadyBooked();
        assertTrue(testBookingSystem.isBookingAvailable());
    }

    @Test
    public void bookingAvailableFalse() {
        setNewBookingThatIsAlreadyBooked();
        assertFalse(testBookingSystem.isBookingAvailable());
    }

    @Test
    public void daysStayingTest() {
        setNewBookingThatIsNotAlreadyBooked();
        assertEquals(testBookingSystem.getDaysStaying(), 4);
    }

    @Test
    public void getCostTest() {
        setNewBookingThatIsNotAlreadyBooked();
        assertEquals(testBookingSystem.getCost(), 4 * 35);
    }

    @Test
    public void setProvisionalBookingTest() {
        setNewBookingThatIsNotAlreadyBooked();

        HouseBooking newTestBooking = getTestProvisionalBooking();

        testBookingSystem.setProvisionalBooking(newTestBooking);

        checkProvisionalBookingHasBeenAdded();
    }

    @Test
    public void setConfirmedBookingTest() {
        testBookingSystem.setStartDateAndSetIndex("01/01/2022");
        testBookingSystem.setEndDateAndSetIndex("02/01/2022");
        testBookingSystem.setHouseNumber(2);
        testBookingSystem.setConfirmedBooking();

        checkConfirmedBookingHasBeenAdded();
    }

    private void checkConfirmedBookingHasBeenAdded() {
        assertHouseBookingIsConfirmed(testBookings.get(0).getHouseTwo());
        assertHouseBookingIsConfirmed(testBookings.get(1).getHouseTwo());
    }
    private void assertHouseBookingIsConfirmed(HouseBooking houseTwo) {
        assertTrue(houseTwo.isConfirmed());
    }

    private void checkProvisionalBookingHasBeenAdded() {
        assertHouseBookingIsProvisionallyBooked(testBookings.get(2).getHouseTwo());
        assertHouseBookingIsProvisionallyBooked(testBookings.get(3).getHouseTwo());
        assertHouseBookingIsProvisionallyBooked(testBookings.get(4).getHouseTwo());
        assertHouseBookingIsProvisionallyBooked(testBookings.get(5).getHouseTwo());
    }
    private void assertHouseBookingIsProvisionallyBooked(HouseBooking houseTwo) {
        assertTrue(houseTwo.isProvisionallyBooked());
    }



    private HouseBooking getTestProvisionalBooking() {
        String[] bookingDetails = getNewBooking();
        bookingDetails[5] = "Provisionally Booked";
        return new HouseBooking(bookingDetails);
    }

    private String[] getNewBooking() {
        String[] bookingDetails = new String[6];

        bookingDetails[1] = "Joe";
        bookingDetails[2] = "Blogs";
        bookingDetails[3] = "joeblogs@gmail.com";
        bookingDetails[4] = "012304 12312";

        return bookingDetails;
    }


    //Methods to create test bookings
    private void setNewBookingThatIsNotAlreadyBooked() {
        testBookingSystem.setStartDateAndSetIndex("03/01/2022");
        testBookingSystem.setEndDateAndSetIndex("06/01/2022");
        testBookingSystem.setHouseNumber(2);
        testBookingSystem.isBookingAvailable();
    }

    private void setNewBookingThatIsAlreadyBooked() {
        testBookingSystem.setStartDateAndSetIndex("01/01/2022");
        testBookingSystem.setEndDateAndSetIndex("03/01/2022");
        testBookingSystem.setHouseNumber(1);
    }

    @Test
    public void getBookingFromDateTest() {
        BookingDate bookingDate = testBookingSystem.getBookingFromDate("05/01/2022");
        assertEquals(bookingDate.getHouseOne().getStatus(), "Form Submitted");
    }

    @Test
    public void cancelBookingTest() {
        testBookingSystem.setStartDateAndSetIndex("01/01/2022");
        testBookingSystem.setEndDateAndSetIndex("02/01/2022");
        testBookingSystem.setHouseNumber(2);
        testBookingSystem.cancelBooking();

        assertTrue(testBookingSystem.isBookingAvailable(), "Check if booking is cancelled");
    }
}
