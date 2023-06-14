package ChoosyApplication;

import CSVReadWriting.BookingDate;
import CSVReadWriting.CSVInterface;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChoosyApplicationTest {

    @Test
    public void clearInputsTest() {
        CSVInterface csvInterface = new CSVInterface();
        csvInterface.readDataFromFile(new File("Bookings.csv"));

        ArrayList<BookingDate> bookings = csvInterface.getBookings();

        ChoosyApplication app = new ChoosyApplication(bookings, csvInterface);


        app.firstname.setText("John");
        app.surname.setText("Smith");
        app.email.setText("johnsmith@gmail.com");
        app.phoneNumber.setText("0123456789");
        app.clearInputs();

        assertEquals("", app.firstname.getText());
        assertEquals("", app.surname.getText());
        assertEquals("", app.email.getText());
        assertEquals("", app.phoneNumber.getText());
    }
}