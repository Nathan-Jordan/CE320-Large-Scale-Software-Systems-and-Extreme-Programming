package Calender;

import CSVReadWriting.HouseBooking;
import org.junit.jupiter.api.Test;

import javax.swing.border.LineBorder;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class DateButtonTest {

    @Test
    void testButtonText() {
        HouseBooking bookingConfirmed = getHouseBookingConfirmed();

        DateButton dateButton = new DateButton(bookingConfirmed, 1);

        assertEquals(dateButton.firstName, "John");
        assertEquals(dateButton.surname, "Doe");
        assertEquals(dateButton.email, "john.doe@gmail.com");
        assertEquals(dateButton.phoneNumber, "555-555-5555");
        assertEquals(dateButton.bookingStatus, "Confirmed");
    }

    @Test
    void testConfirmedColour() {
        HouseBooking bookingConfirmed = getHouseBookingConfirmed();

        DateButton dateButton = new DateButton(bookingConfirmed, 1);
        LineBorder border = ((LineBorder)dateButton.getBorder());

        assertEquals(border.getLineColor(), Color.RED);
    }

    @Test
    void testFormSubmittedColour() {
        HouseBooking bookingFormSubmitted = getHouseBookingFormSubmitted();

        DateButton dateButton = new DateButton(bookingFormSubmitted, 1);
        LineBorder border = ((LineBorder)dateButton.getBorder());

        assertEquals(border.getLineColor(), Color.BLACK);
    }

    @Test
    void testProvisionallyBookedColour() {
        HouseBooking bookingProvisionallyBooked = getHouseBookingProvisionallyBooked();

        DateButton dateButton = new DateButton(bookingProvisionallyBooked, 1);
        LineBorder border = ((LineBorder)dateButton.getBorder());

        assertEquals(border.getLineColor(), Color.BLUE);
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