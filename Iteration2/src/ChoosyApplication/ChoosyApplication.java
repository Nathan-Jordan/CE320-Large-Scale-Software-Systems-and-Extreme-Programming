package ChoosyApplication;

import BookingSystem.BookingSystem;
import CSVReadWriting.BookingDate;
import CSVReadWriting.CSVInterface;
import CSVReadWriting.HouseBooking;
import Calender.ChoosyCalender;
import Dates.CustomDate;
import MailingSystem.MailHandler;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChoosyApplication extends JPanel {

    public static CSVInterface csvInterface;
    ArrayList<BookingDate> bookings;

    private final BookingSystem bookingSystem;
    private final MailHandler mailhandler;

    DefaultComboBoxModel<String> startDates = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> endDates = new DefaultComboBoxModel<>();

    boolean bookingAvailable;
    boolean inputProblems;

    JComboBox<String> startDate, endDate;
    JTextField firstname, surname, email, phoneNumber;

    JLabel notificationLbl;

    JButton checkAvailability, sendDetails, formSubmitted, confirmBooking, cancelBooking, calenderButton;
    JFrame frame;

    public ChoosyApplication(JFrame frame, ArrayList<BookingDate> bookings) {
        this.bookings = bookings;
        this.frame = frame;

        this.bookingSystem = new BookingSystem(this.bookings);
        this.mailhandler = new MailHandler();

        setLayout(new GridLayout(7, 3, 10, 10));
        setSize(670, 750);

        createHousePanel();
        createDatePanel();
        createInputPanel();
        createButtonPanel();
        createCancelButton();
        createKeyPanel();
        createSwitchButton();

        clearInputs();
    }

    private void createHousePanel() {
        JPanel housePanel = new JPanel();
        housePanel.setLayout(new GridLayout(1, 3, 10, 10));

        JButton house1 = new JButton("House 1");
        JButton house2 = new JButton("House 2");

        house1.setBackground(Color.DARK_GRAY);
        house1.setForeground(Color.WHITE);
        house2.setBackground(Color.GRAY);
        house2.setForeground(Color.WHITE);

        housePanel.add(house1);
        housePanel.add(house2);

        addHouseActionListeners(house1, house2);

        add(housePanel);
    }

    private void addHouseActionListeners(JButton house1, JButton house2) {
        house1.addActionListener(e -> {
            house1.setBackground(Color.darkGray);
            house2.setBackground(Color.GRAY);
            bookingSystem.setHouseNumber(1);
            clearInputs();
        });
        house2.addActionListener(e -> {
            house1.setBackground(Color.GRAY);
            house2.setBackground(Color.darkGray);
            bookingSystem.setHouseNumber(2);
            clearInputs();
        });
    }

    private void createDatePanel() {
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new GridLayout(2, 3));

        JLabel startDateLbl = new JLabel("Start date");
        startDateLbl.setHorizontalAlignment(SwingConstants.CENTER);
        startDate = new JComboBox<>();

        JLabel endDateLbl = new JLabel("End date");
        endDateLbl.setHorizontalAlignment(SwingConstants.CENTER);
        endDate = new JComboBox<>();

        checkAvailability = new JButton("Check Availability");

        datePanel.add(startDateLbl);
        datePanel.add(endDateLbl);
        datePanel.add(Box.createGlue());
        datePanel.add(startDate);
        datePanel.add(endDate);
        datePanel.add(checkAvailability);

        populateDateComboBoxes();
        addDateActionListeners();

        add(datePanel);
    }

    private void populateDateComboBoxes() {
        for (BookingDate booking : bookings) {
            startDates.addElement(booking.getDate());
            endDates.addElement(booking.getDate());

            booking.getHouseOne().getStatus();
            booking.getHouseTwo().getStatus();
        }

        ComboBoxRenderer renderer = new ComboBoxRenderer(bookingSystem);

        startDate.setRenderer(renderer);
        endDate.setRenderer(renderer);
        startDate.setModel(startDates);
        endDate.setModel(endDates);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        startDate.setSelectedItem(dtf.format(now));
        endDate.setSelectedItem(dtf.format(now));

        bookingSystem.setStartDateAndSetIndex((String)startDate.getSelectedItem());
        bookingSystem.setEndDateAndSetIndex((String) endDate.getSelectedItem());
    }

    void addDateActionListeners() {
        checkAvailability.addActionListener(e -> checkForExistingBooking());

        startDate.addActionListener(e -> {
            clearInputs();
            checkDates(startDate);
            bookingSystem.setStartDateAndSetIndex((String)startDate.getSelectedItem());
        });

        endDate.addActionListener(e -> {
            clearInputs();
            checkDates(endDate);
            bookingSystem.setEndDateAndSetIndex((String) endDate.getSelectedItem());
        });
    }

    private void checkDates(JComboBox<String> caller) {
        String startDateStr = (String) startDate.getSelectedItem();

        String endDateStr = (String) endDate.getSelectedItem();

        boolean date1AfterDate2 = CustomDate.isDate1AfterDate2(startDateStr, endDateStr);

        if (date1AfterDate2) {
            if (caller == endDate) {
                startDate.setSelectedItem(endDateStr);
            } else {
                endDate.setSelectedItem(startDateStr);
            }
        }
    }

    private void createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 4, -180, 0));
        firstname = new JTextField();
        surname = new JTextField();
        email = new JTextField();
        phoneNumber = new JTextField();

        JLabel firstnameLbl = new JLabel("  First Name:");
        JLabel surnameLbl = new JLabel("  Surname:");
        JLabel emailLbl = new JLabel("  Email:");
        JLabel phoneNumberLbl = new JLabel("  Phone Number:");

        inputPanel.add(firstnameLbl);
        inputPanel.add(firstname);
        inputPanel.add(surnameLbl);
        inputPanel.add(surname);
        inputPanel.add(emailLbl);
        inputPanel.add(email);
        inputPanel.add(phoneNumberLbl);
        inputPanel.add(phoneNumber);

        add(inputPanel);
    }

    private void createButtonPanel() {
        sendDetails = new JButton("Send Details");
        formSubmitted = new JButton("Form Submitted");
        confirmBooking = new JButton("Confirm Booking");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));
        buttonPanel.add(sendDetails);
        buttonPanel.add(formSubmitted);
        buttonPanel.add(confirmBooking);

        notificationLbl = new JLabel("");

        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new GridLayout(2, 3));

        bookingPanel.add(buttonPanel);
        bookingPanel.add(notificationLbl);

        addSendDetailsActionListener();
        addFormSubmittedActionListener();
        addConfirmedActionListener();

        add(bookingPanel);
    }

    private void addSendDetailsActionListener() {
        sendDetails.addActionListener(e -> {
            checkInputs();

            if (!inputProblems) {
                sendDetails();
                setInputsEditable(false);
                cancelBooking.setEnabled(true);
            }
        });
    }

    private void sendDetails() {
        notificationLbl.setText("  Booking has been provisionally booked and an email has been sent requesting the customer to confirm booking.");

        HouseBooking booking = getNewProvisionalBooking();

        bookingSystem.setProvisionalBooking(booking);
        csvInterface.writeToFile();
        mailhandler.sendEmail(bookingSystem, booking, "available");

        sendDetails.setEnabled(false);
        formSubmitted.setEnabled(true);
        confirmBooking.setEnabled(false);
    }

    private void addFormSubmittedActionListener() {
        formSubmitted.addActionListener(e -> {
            checkInputs();

            if (!inputProblems) {
                notificationLbl.setText("  Booking is provisionally booked and the customer has confirmed the booking.");

                bookingSystem.setFormSubmitted();
                csvInterface.writeToFile();

                confirmBooking.setEnabled(true);
                formSubmitted.setEnabled(false);
                cancelBooking.setEnabled(true);
            }
        });
    }

    private void addConfirmedActionListener() {
        confirmBooking.addActionListener(e -> {
            checkInputs();

            if (!inputProblems) {
                notificationLbl.setText("  Booking is confirmed and a confirmation email has been sent.");

                HouseBooking booking = getNewProvisionalBooking();
                mailhandler.sendEmail(bookingSystem, booking, "confirmation");

                bookingSystem.setConfirmedBooking();
                confirmBooking.setEnabled(false);
                cancelBooking.setEnabled(true);
                csvInterface.writeToFile();
            }
        });
    }

    private void createCancelButton() {
        JPanel cancelPanel = new JPanel();
        cancelPanel.setLayout(new GridLayout(2, 3));
        cancelBooking = new JButton("Cancel Booking");

        JLabel notificationLbl = new JLabel("");
        cancelPanel.add(cancelBooking);
        cancelPanel.add(notificationLbl);

        addCancelActionListener();

        add(cancelPanel);
    }

    private void addCancelActionListener() {
        cancelBooking.addActionListener(e -> {
            HouseBooking booking = getNewProvisionalBooking();
            mailhandler.sendEmail(bookingSystem, booking, "cancellation");

            bookingSystem.cancelBooking();
            csvInterface.writeToFile();
            clearInputs();
            notificationLbl.setText("  Booking has been cancelled and an email has been sent.");
        });
    }

    private void createKeyPanel() {
        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new GridLayout(5, 1));
        keyPanel.setBackground(Color.LIGHT_GRAY);

        JLabel keyLbl = new JLabel("Booking Date Key:");
        keyLbl.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel availableLbl = new JLabel("Available");
        availableLbl.setForeground(Color.GREEN);
        availableLbl.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel formSubmittedLbl = new JLabel("Form Submitted");
        formSubmittedLbl.setForeground(Color.BLACK);
        formSubmittedLbl.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel provisionallyBookedLbl = new JLabel("Provisionally Booked");
        provisionallyBookedLbl.setForeground(Color.BLUE);
        provisionallyBookedLbl.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel confirmedLbl = new JLabel("Confirmed");
        confirmedLbl.setForeground(Color.RED);
        confirmedLbl.setHorizontalAlignment(SwingConstants.CENTER);

        keyPanel.add(keyLbl);
        keyPanel.add(availableLbl);
        keyPanel.add(formSubmittedLbl);
        keyPanel.add(provisionallyBookedLbl);
        keyPanel.add(confirmedLbl);

        add(keyPanel);
    }

    private void checkInputs() {
        notificationLbl.setForeground(Color.BLACK);
        inputProblems = false;

        inputProblems = firstname.getText().isBlank() ||
                        surname.getText().isBlank() ||
                        email.getText().isBlank() ||
                        phoneNumber.getText().isBlank();

        if (inputProblems) {
            notificationLbl.setForeground(Color.RED);
            notificationLbl.setText("  Please ensure all inputs are filled out.");
        }
    }

    private void checkForExistingBooking() {
        bookingAvailable = bookingSystem.isBookingAvailable();

        //If booking is not available find the full booking and populate the Ui
        if (!bookingAvailable) {
            HouseBooking existingBooking = bookingSystem.getExistingBooking();

            setDatesToExistingBooking(existingBooking);
            setInputsToExistingBooking(existingBooking);
            enableRelevantButtons(existingBooking);

        } else {
            setInputsEditable(true);

            sendDetails.setEnabled(true);
            notificationLbl.setText("  Booking is available.");
        }
    }

    private void setDatesToExistingBooking(HouseBooking existingBooking) {
        String startDateItem = bookingSystem.getEarliestStartDateOfBooking(existingBooking);
        startDate.setSelectedItem(startDateItem);

        String endDateItem = bookingSystem.getEndDateOfBooking(existingBooking);
        endDate.setSelectedItem(endDateItem);
    }

    private void setInputsToExistingBooking(HouseBooking existingBooking) {
        firstname.setText(existingBooking.getFirstName());
        surname.setText(existingBooking.getSurname());
        email.setText(existingBooking.getEmail());
        phoneNumber.setText(existingBooking.getPhoneNumber());
    }

    private void enableRelevantButtons(HouseBooking existingBooking) {
        sendDetails.setEnabled(false);
        cancelBooking.setEnabled(true);

        if (existingBooking.isConfirmed()) {
            notificationLbl.setText("  Booking is confirmed and a confirmation email has been sent.");

        } else if (existingBooking.isFormSubmitted()) {
            confirmBooking.setEnabled(true);
            notificationLbl.setText("  Booking is provisionally booked and the customer has confirmed the booking.");

        } else if (existingBooking.isProvisionallyBooked()) {
            formSubmitted.setEnabled(true);
            confirmBooking.setEnabled(true);
            notificationLbl.setText("  Booking has been provisionally booked and an email has been sent requesting the customer to confirm booking.");
        }
    }

    void clearInputs() {
        notificationLbl.setForeground(Color.BLACK);
        firstname.setText("");
        surname.setText("");
        email.setText("");
        phoneNumber.setText("");
        notificationLbl.setText("");

        sendDetails.setEnabled(false);
        confirmBooking.setEnabled(false);
        formSubmitted.setEnabled(false);
        cancelBooking.setEnabled(false);

        setInputsEditable(false);
    }

    private void setInputsEditable(boolean editable) {
        firstname.setEditable(editable);
        surname.setEditable(editable);
        email.setEditable(editable);
        phoneNumber.setEditable(editable);
    }

    private HouseBooking getNewProvisionalBooking() {
        String[] bookingDetails = new String[6];

        bookingDetails[1] = firstname.getText();
        bookingDetails[2] = surname.getText();
        bookingDetails[3] = email.getText();
        bookingDetails[4] = phoneNumber.getText();
        bookingDetails[5] = "Provisionally Booked";

        return new HouseBooking(bookingDetails);
    }

    private void createSwitchButton() {
        calenderButton = new JButton("View Calendar");

        calenderButton.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            frame = new JFrame();
            frame.setSize(1920, 1080);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.add(new ChoosyCalender(frame, bookings));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        add(calenderButton);
    }
}