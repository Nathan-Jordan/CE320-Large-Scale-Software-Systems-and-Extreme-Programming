package CSVReadWriting;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class CSVInterface {

    private final ArrayList<BookingDate> bookings = new ArrayList<>();
    File bookingFile;


    public void readDataFromFile(File bookingFile) {
        this.bookingFile = bookingFile;

        try (Scanner fileReader = new Scanner(bookingFile)) {

            //Ignore first line as it contains the headers
            fileReader.nextLine();

            while (fileReader.hasNext()) {
                String line = fileReader.nextLine();
                BookingDate bookingDate = getBookingDateFromLine(line);
                bookings.add(bookingDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BookingDate getBookingDateFromLine(String line) {
        String[] splitLine = line.split("H1|H2");

        String date = splitLine[0];
        date = date.replace(",", "");

        HouseBooking houseOneBooking = extractBookingInfo(splitLine[1]);
        HouseBooking houseTwoBooking = extractBookingInfo(splitLine[2]);

        return new BookingDate(date, houseOneBooking, houseTwoBooking);
    }

    private HouseBooking extractBookingInfo(String house) {
        String[] bookingDetails = house.split(",");
        return new HouseBooking(bookingDetails);
    }


    public ArrayList<BookingDate> getBookings() {
        return bookings;
    }


    public void writeToFile() {
        try {
            PrintWriter fileWriter = new PrintWriter(bookingFile);


            fileWriter.println("Date,H1,firstName,surname,email,phoneNumber,status,H2,firstName,surname,email,phoneNumber,status");
            String line;

            for (BookingDate bookingDate : bookings) {
                line = "";
                line += bookingDate.date + ",";
                line += "H1,";
                line += addHouseDetails(bookingDate.houseOne);
                line += "H2,";
                line += addHouseDetails(bookingDate.houseTwo);

                fileWriter.println(line);
            }

            fileWriter.close();
            System.out.println(bookingFile + " updated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String addHouseDetails(HouseBooking house) {
        String result = "";

        result = addFieldDetails(result, house.getFirstName());
        result = addFieldDetails(result, house.getSurname());
        result = addFieldDetails(result, house.getEmail());
        result = addFieldDetails(result, house.getPhoneNumber());
        result = addFieldDetails(result, house.getStatus());

        return result;
    }

    private String addFieldDetails(String result, String value) {
        if (value != null) {
            result += value;
        }
        result += ",";
        return result;
    }
}
