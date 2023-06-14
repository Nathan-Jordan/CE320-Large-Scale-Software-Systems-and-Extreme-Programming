package CSVReadWriting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HouseBookingTest {

    @Test
    public void testEmptyConstructor() {
        HouseBooking booking = new HouseBooking();

        assertEquals("", booking.getFirstName());
        assertEquals("", booking.getSurname());
        assertEquals("", booking.getEmail());
        assertEquals("", booking.getPhoneNumber());
        assertFalse(booking.isBooked());
    }

    @Test
    public void testFullConstructor() {
        HouseBooking booking = getHouseBookingConfirmed();

        assertEquals("John", booking.getFirstName());
        assertEquals("Doe", booking.getSurname());
        assertEquals("john.doe@gmail.com", booking.getEmail());
        assertEquals("555-555-5555", booking.getPhoneNumber());
        assertTrue(booking.isBooked());
    }

    @Test
    public void testEachStatusIsBooked() {
        HouseBooking bookingConfirmed = getHouseBookingConfirmed();
        HouseBooking bookingProvisionallyBooked = getHouseBookingProvisionallyBooked();
        HouseBooking bookingFormSubmitted = getHouseBookingFormSubmitted();

        assertTrue(bookingConfirmed.isBooked());
        assertTrue(bookingProvisionallyBooked.isBooked());
        assertTrue(bookingFormSubmitted.isBooked());
    }

    @Test
    void testEqualsTrue() {
        HouseBooking houseBooking1 = getHouseBookingConfirmed();
        HouseBooking houseBooking2 = getHouseBookingConfirmed();

        assertEquals(houseBooking1, houseBooking2);
    }

    @Test
    void testEqualsFalse() {
        HouseBooking houseBooking1 = getHouseBookingConfirmed();
        HouseBooking houseBooking2 = getHouseBookingProvisionallyBooked();

        assertNotEquals(houseBooking1, houseBooking2);
    }

    @Test
    void testToString() {
        HouseBooking bookingConfirmed = getHouseBookingConfirmed();
        HouseBooking bookingProvisionallyBooked = getHouseBookingProvisionallyBooked();
        HouseBooking bookingBlank = new HouseBooking();

        assertEquals(bookingConfirmed.toString(), "Confirmed");
        assertEquals(bookingProvisionallyBooked.toString(), "Provisionally Booked");
        assertEquals(bookingBlank.toString(), "Not Booked");
    }

    @Test
    void testGetStatus() {
        HouseBooking bookingConfirmed = getHouseBookingConfirmed();
        HouseBooking bookingProvisionallyBooked = getHouseBookingProvisionallyBooked();
        HouseBooking bookingFormSubmitted = getHouseBookingFormSubmitted();
        HouseBooking bookingBlank = new HouseBooking();

        assertEquals(bookingConfirmed.getStatus(), "Confirmed");
        assertEquals(bookingProvisionallyBooked.getStatus(), "Provisionally Booked");
        assertEquals(bookingFormSubmitted.getStatus(), "Form Submitted");
        assertNull(bookingBlank.getStatus());
    }

    private HouseBooking getHouseBookingConfirmed() {
        String[] bookingDetails = {"", "John", "Doe", "john.doe@gmail.com", "555-555-5555", "Confirmed"};
        return new HouseBooking(bookingDetails);
    }
    private HouseBooking getHouseBookingProvisionallyBooked() {
        String[] bookingDetails = {"", "John", "Doe", "john.doe@gmail.com", "555-555-5555", "Provisionally Booked"};
        return new HouseBooking(bookingDetails);
    }
    private HouseBooking getHouseBookingFormSubmitted() {
        String[] bookingDetails = {"", "John", "Doe", "john.doe@gmail.com", "555-555-5555", "Form Submitted"};
        return new HouseBooking(bookingDetails);
    }
}