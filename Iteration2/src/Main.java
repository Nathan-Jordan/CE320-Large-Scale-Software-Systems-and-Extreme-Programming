import CSVReadWriting.BookingDate;
import CSVReadWriting.CSVInterface;
import ChoosyApplication.*;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(700, 750);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CSVInterface csvInterface = new CSVInterface();

        ArrayList<BookingDate> bookings = getBookings(csvInterface);

        ChoosyApplication app = new ChoosyApplication(frame, bookings);
        ChoosyApplication.csvInterface = csvInterface;

        frame.add(app);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static ArrayList<BookingDate> getBookings(CSVInterface csvInterface) {
        File bookingFile = new File("Bookings.csv");

        csvInterface.readDataFromFile(bookingFile);
        csvInterface.addNewDates();
        csvInterface.writeToFile();

        return csvInterface.getBookings();
    }
}
