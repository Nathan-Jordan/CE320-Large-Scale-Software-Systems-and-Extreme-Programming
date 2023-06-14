package Calender;

import CSVReadWriting.BookingDate;
import CSVReadWriting.HouseBooking;
import ChoosyApplication.ChoosyApplication;
import Dates.CustomDate;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;

public class ChoosyCalender extends JPanel {

    private final ArrayList<BookingDate> bookings;
    private final Map<CustomDate, HouseBooking> houseOneBookings = new HashMap<>();
    private final Map<CustomDate, HouseBooking> houseTwoBookings = new HashMap<>();

    private int selectedHouse = 1;
    int month = LocalDate.now().getMonthValue();
    private int year = LocalDate.now().getYear();

    private JFrame frame;
    private final JLabel yearLabel = new JLabel();
    private JPanel calenderPanel = new JPanel();

    private final JButton left = new JButton("<-");
    private final JButton right = new JButton("->");
    private final JButton toggleHouse = new JButton("House 1");
    private JButton backToBookingSheet;


    public ChoosyCalender(JFrame frame, ArrayList<BookingDate> bookings) {
        this.frame = frame;
        this.bookings = bookings;

        initFrame();
        addNavigationPanel();
        initMaps();
        addButtonListeners();
        reloadCalender();
    }

    void initFrame() {
        setSize(1920, 1080);
        setLayout(new BorderLayout());
    }

    private void initMaps() {
        for (BookingDate booking : bookings) {
            CustomDate date = booking.getCustomDate();
            houseOneBookings.put(date, booking.getHouseOne());
            houseTwoBookings.put(date, booking.getHouseTwo());
        }
    }

    private void reloadCalender() {
        remove(calenderPanel);

        yearLabel.setText(getPeriod());

        calenderPanel = new JPanel();
        GridLayout grid = new GridLayout(5, 7, 10, 10);
        calenderPanel.setLayout(grid);


        Map<CustomDate, HouseBooking> selectedBookings = getMonthBookings(month, year);

        for (int i = 0; i < getMonthLength(month); i++) {
            String date = (i + 1) + "/" + month + "/" + year;
            CustomDate customDate = new CustomDate(date);

            selectedBookings.putIfAbsent(customDate, new HouseBooking());
        }

        for (Map.Entry<CustomDate, HouseBooking> booking : selectedBookings.entrySet()) {
            int date = booking.getKey().day;

            HouseBooking b = booking.getValue();
            DateButton button = new DateButton(b, date);

            calenderPanel.add(button);
        }

        add(calenderPanel, BorderLayout.CENTER);
    }

    Map<CustomDate, HouseBooking> getMonthBookings(int month, int year) {
        Map<CustomDate, HouseBooking> dateByHouseBookings = selectedHouse == 1 ? houseOneBookings : houseTwoBookings;
        Map<CustomDate, HouseBooking> selectedBookings = new TreeMap<>();

        for (Map.Entry<CustomDate, HouseBooking> booking : dateByHouseBookings.entrySet()) {
            if (booking.getKey().isInPeriod(month, year))
                selectedBookings.put(booking.getKey(), booking.getValue());
        }
        return selectedBookings;
    }

    private void addNavigationPanel() {
        JPanel inputPanel = new JPanel();

        backToBookingSheet = new JButton("Booking Sheet");

        inputPanel.add(left);
        inputPanel.add(backToBookingSheet);
        inputPanel.add(yearLabel);
        inputPanel.add(toggleHouse);
        inputPanel.add(right);

        yearLabel.setName(getPeriod());

        add(inputPanel, BorderLayout.NORTH);
    }

    private void addButtonListeners() {
        left.addActionListener(e -> goToPreviousMonth());

        right.addActionListener(e -> goToNextMonth());

        toggleHouse.addActionListener(e -> {
            if (selectedHouse == 1) {
                toggleHouse.setText("House 2");
                selectedHouse = 2;
            } else {
                toggleHouse.setText("House 1");
                selectedHouse = 1;
            }
            reloadCalender();
        });

        backToBookingSheet.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            frame = new JFrame();
            frame.setSize(670, 750);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.add(new ChoosyApplication(frame, bookings));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    void goToPreviousMonth() {
        if (month > 1)
            month--;
        else if (month == 1) {
            year--;
            month = 12;
        }

        reloadCalender();
    }

    void goToNextMonth() {
        if (month < 12)
            month++;
        else if (month == 12) {
            year++;
            month = 1;
        }

        reloadCalender();
    }

    String getMonth(int month) {
        return switch (month) {
            case 1 -> "JANUARY";
            case 2 -> "FEBRUARY";
            case 3 -> "MARCH";
            case 4 -> "APRIL";
            case 5 -> "MAY";
            case 6 -> "JUNE";
            case 7 -> "JULY";
            case 8 -> "AUGUST";
            case 9 -> "SEPTEMBER";
            case 10 -> "OCTOBER";
            case 11 -> "NOVEMBER";
            default -> "DECEMBER";
        };
    }

    int getMonthLength(int month) {
        return switch (month) {
            case 2 -> 28;
            case 4, 11, 9, 6 -> 30;
            default -> 31;
        };
    }

    private String getPeriod() {
        return getMonth(month) + " " + year;
    }
}