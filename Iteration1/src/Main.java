import CSVReadWriting.BookingDate;
import CSVReadWriting.HouseBooking;
import CSVReadWriting.CSVInterface;
import ChoosyApplication.*;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        CSVInterface csvInterface = new CSVInterface();

        File bookingFile = new File("Bookings.csv");
        csvInterface.readDataFromFile(bookingFile);

        ArrayList<BookingDate> bookings = csvInterface.getBookings();


        ChoosyApplication app = new ChoosyApplication(bookings, csvInterface);
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }
}
