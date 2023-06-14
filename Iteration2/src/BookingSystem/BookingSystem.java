package BookingSystem;

import CSVReadWriting.BookingDate;
import CSVReadWriting.HouseBooking;

import java.util.ArrayList;
import java.util.Objects;

public class BookingSystem {

    private ArrayList<BookingDate> bookings;
    private HouseBooking newBooking;

    private int houseNumber = 1;

    private int startDateIndex = 0;
    private int endDateIndex = 0;
    private int daysStaying;
    private boolean isAvailable;

    private final int costPerDay = 35;


    public BookingSystem(ArrayList<BookingDate> bookings) {
        this.bookings = bookings;
    }

    public boolean isBookingAvailable() {
        daysStaying = 1 + endDateIndex - startDateIndex;

        isAvailable = true;

        for (int i = startDateIndex; i <= endDateIndex; i++) {
            HouseBooking house = bookings.get(i).getHouse(houseNumber);
            if (house.isBooked()) {
                isAvailable = false;
                break;
            }
        }

        return isAvailable;
    }

    public void setProvisionalBooking(HouseBooking newBooking) {
        this.newBooking = newBooking;

        setBookings("setProvisionalBooking");
    }

    public void setFormSubmitted() {
        setBookings("setFormSubmitted");
    }

    public void setConfirmedBooking() {
        setBookings("setConfirmedBooking");
    }

    private void setBookings(String action) {
        for (int i = startDateIndex; i <= endDateIndex ; i++) {
            BookingDate bookingDate = bookings.get(i);
            HouseBooking booking =  bookingDate.getHouse(houseNumber);

            if (Objects.equals(action, "setProvisionalBooking")) {
                bookingDate.setHouse(houseNumber, newBooking);

            } else if (Objects.equals(action, "setFormSubmitted")) {
                booking.setFormSubmitted(true);

            } else if (Objects.equals(action, "setConfirmedBooking")) {
                booking.setConfirmed(true);
            }
        }
    }

    public void cancelBooking() {
        for (int i = startDateIndex; i <= endDateIndex ; i++) {
            bookings.get(i).setHouse(houseNumber, new HouseBooking());
        }
    }

    public HouseBooking getExistingBooking() {
        for (int i = startDateIndex; i <= endDateIndex ; i++) {
            BookingDate bookingDate = bookings.get(i);

            if (bookingDate.getHouse(houseNumber).isBooked()) {
                return bookingDate.getHouse(houseNumber);
            }

        }

        return null;
    }

    public String getEarliestStartDateOfBooking(HouseBooking existingBooking) {
        for (int i = 0; i < bookings.size(); i++) {
            BookingDate bookingDate = bookings.get(i);
            HouseBooking houseBooking = bookingDate.getHouse(houseNumber);
            if (houseBooking.equals(existingBooking)) {
                return bookingDate.getDate();
            }
        }
        return "";
    }

    public String getEndDateOfBooking(HouseBooking existingBooking) {
        for (int i = startDateIndex; i < bookings.size(); i++) {
            BookingDate bookingDate = bookings.get(i);
            HouseBooking houseBooking = bookingDate.getHouse(houseNumber);

            if (!houseBooking.equals(existingBooking)) {
                return bookings.get(i - 1).getDate();
            }
        }
        return "";
    }

    public void setStartDateAndSetIndex(String startDate) {
        for (int i = 0; i < bookings.size(); i++) {

            if (Objects.equals(bookings.get(i).getDate(), startDate)) {
                startDateIndex = i;
                break;
            }
        }
    }

    public void setEndDateAndSetIndex(String endDate) {
        for (int i = 0; i < bookings.size(); i++) {
            if (Objects.equals(bookings.get(i).getDate(), endDate)) {
                endDateIndex = i;
                break;
            }
        }
    }


    public BookingDate getBookingFromDate(String date) {
        for (int i = 0; i <= bookings.size() ; i++) {
            BookingDate bookingDate = bookings.get(i);

            if (Objects.equals(bookingDate.getDate(), date)) {
                return bookingDate;
            }

        }

        return null;
    }


    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getCost() {
        return daysStaying * costPerDay;
    }

    public int getDaysStaying() {
        return daysStaying;
    }

    public String getStartDate() {
        return bookings.get(startDateIndex).getDate();
    }

    public String getEndDate() {
        return bookings.get(endDateIndex).getDate();
    }

    public int getHouseNumber() {
        return houseNumber;
    }
}
