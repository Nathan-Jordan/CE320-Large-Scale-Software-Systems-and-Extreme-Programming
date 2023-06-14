package ChoosyApplication;

import CSVReadWriting.BookingDate;
import CSVReadWriting.CSVInterface;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class ChoosyApplicationTest {

    @Test
    public void clearInputsTest() {
        CSVInterface csvInterface = new CSVInterface();
        csvInterface.readDataFromFile(new File("Bookings.csv"));

        ArrayList<BookingDate> bookings = csvInterface.getBookings();


        JFrame frame = new JFrame();
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ChoosyApplication app = new ChoosyApplication(frame, bookings);

        frame.add(app);
        frame.setVisible(true);

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