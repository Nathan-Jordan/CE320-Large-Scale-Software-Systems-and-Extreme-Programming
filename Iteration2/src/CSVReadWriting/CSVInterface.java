package CSVReadWriting;

import Dates.CustomDate;

import java.io.File;
import java.io.PrintWriter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class CSVInterface {
    private final ArrayList<BookingDate> bookings = new ArrayList<>();

    private File bookingCSVFile;


    public void readDataFromFile(File bookingCSVFile) {
        this.bookingCSVFile = bookingCSVFile;

        try (Scanner fileReader = new Scanner(bookingCSVFile)) {

            //Ignore first line as it contains the headers
            fileReader.nextLine();

            while (fileReader.hasNextLine()) {
                String lineInFile = fileReader.nextLine();
                BookingDate bookingDate = getBookingDateFromLine(lineInFile);
                bookings.add(bookingDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please check Booking.csv");
        }
    }

    private BookingDate getBookingDateFromLine(String line) {
        String[] splitLineAtHouses = line.split("H1|H2");

        String date = splitLineAtHouses[0];
        date = date.replace(",", "");

        HouseBooking houseOneBooking = extractBookingInfo(splitLineAtHouses[1]);
        HouseBooking houseTwoBooking = extractBookingInfo(splitLineAtHouses[2]);

        return new BookingDate(date, houseOneBooking, houseTwoBooking);
    }

    private HouseBooking extractBookingInfo(String house) {
        String[] bookingDetails = house.split(",");

        return new HouseBooking(bookingDetails);
    }

    public void addNewDates() {
        LocalDate lastDateInFile = getLastDateInFile();

        long numberOfDatesToAdd = getNumberOfDatesToAdd(lastDateInFile);


        LocalDate nextDate = lastDateInFile;

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < numberOfDatesToAdd; i++) {
            nextDate = nextDate.plusDays(1);

            String newDate = nextDate.format(dateTimeFormatter);
            BookingDate newBookingDate = new BookingDate(newDate);
            bookings.add(newBookingDate);
        }
    }

    private LocalDate getLastDateInFile() {
        String strLastDate = bookings.get(bookings.size() - 1).getDate();

        return new CustomDate(strLastDate).getDate();
    }

    private long getNumberOfDatesToAdd(LocalDate lastDateInFile) {
        LocalDate today = LocalDate.now();
        LocalDate yearFromToday = today.plusYears(1);

        return DAYS.between(lastDateInFile, yearFromToday);
    }

    public ArrayList<BookingDate> getBookings() {
        return bookings;
    }


    public void writeToFile() {
        try {
            PrintWriter fileWriter = new PrintWriter(bookingCSVFile);

            fileWriter.println("Date,H1,firstName,surname,email,phoneNumber,status,H2,firstName,surname,email,phoneNumber,status");

            for (BookingDate bookingDate : bookings) {
                String line = getLineDetails(bookingDate);
                fileWriter.println(line);
            }

            fileWriter.close();
            System.out.println(bookingCSVFile + " updated");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Please check Booking.csv");
        }
    }

    private String getLineDetails(BookingDate bookingDate) {
        String line;

        line = "";
        line += bookingDate.getDate() + ",";
        line += "H1,";
        line += addHouseDetails(bookingDate.getHouseOne());
        line += "H2,";
        line += addHouseDetails(bookingDate.getHouseTwo());

        return line;
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
