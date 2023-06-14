package ChoosyApplication;

import BookingSystem.BookingSystem;
import CSVReadWriting.BookingDate;
import CSVReadWriting.CSVInterface;
import CSVReadWriting.HouseBooking;
import Dates.CustomDate;
import MailingSystem.MailHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class ChoosyApplication extends JFrame {

    private final CSVInterface csvInterface;
    ArrayList<BookingDate> bookings;
    private final BookingSystem bookingSystem;
    private final MailHandler mailhandler;

    DefaultComboBoxModel<String> startDates = new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> endDates = new DefaultComboBoxModel<>();

    int cost;
    boolean bookingAvailable;
    boolean inputProblems;

    JComboBox<String> startDate;
    JComboBox<String> endDate;
    JButton checkAvailability;
    JTextField firstname;
    JTextField surname;
    JTextField email;
    JTextField phoneNumber;
    JLabel notificationLbl;
    JButton sendDetails;
    JButton formSubmitted;
    JButton confirmBooking;


    public ChoosyApplication(ArrayList<BookingDate> bookings, CSVInterface csvInterface){
        //Init
        this.csvInterface = csvInterface;
        this.bookings = bookings;
        this.bookingSystem = new BookingSystem(bookings);
        this.mailhandler = new MailHandler();

        setTitle("CHOOSY");
        setLayout(new GridLayout(7,3, 10, 10));
        setSize(670,750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createHousePanel();
        createDatePanel();
        createInputPanel();
        createButtonPanel();

        clearInputs();
    }


    private void createHousePanel(){
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


    private void createDatePanel(){
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
        }

        startDate.setModel(startDates);
        endDate.setModel(endDates);
    }

    void addDateActionListeners() {
        checkAvailability.addActionListener(e -> checkForExistingBooking());

        startDate.addActionListener(e -> {
            clearInputs();
            checkDates(startDate);
            bookingSystem.setStartDateAndSetIndex((String) startDate.getSelectedItem());
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


    private void createInputPanel(){
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 4, -180 ,0));
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

    private void createButtonPanel(){
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
            }
        });
    }

    private void sendDetails() {
        notificationLbl.setText("  Booking has been provisionally booked and an email has been sent requesting the customer to confirm booking.");
        cost = bookingSystem.getCost();

        HouseBooking newBooking = getNewProvisionalBooking();

        bookingSystem.setProvisionalBooking(newBooking);
        csvInterface.writeToFile();
        mailhandler.sendEmail(newBooking, cost, startDate.getSelectedItem(), endDate.getSelectedItem(), "available"); //Sends the available + google forms format email

        sendDetails.setEnabled(false);
        formSubmitted.setEnabled(true);
        confirmBooking.setEnabled(false);
    }

    private void bookingNotAvailable() {
        HouseBooking temp = getNewProvisionalBooking();
        mailhandler.sendEmail(temp, cost, startDate.getSelectedItem(), endDate.getSelectedItem(), "unavailable"); //Sends an unavailable format email
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
            }
        });
    }

    private void addConfirmedActionListener() {
        confirmBooking.addActionListener(e -> {
            checkInputs();

            if (!inputProblems) {
                notificationLbl.setText("  Booking is confirmed and a confirmation email has been sent.");

                HouseBooking temp = getNewProvisionalBooking();
                mailhandler.sendEmail(temp, cost, startDate.getSelectedItem(), endDate.getSelectedItem(), "confirmation_email"); //Sends the confirmation email

                bookingSystem.setConfirmedBooking();
                confirmBooking.setEnabled(false);
            }
        });
    }

    private void checkInputs() {
        notificationLbl.setForeground(Color.BLACK);
        inputProblems = false;

        if (firstname.getText().isBlank()) {
            inputProblems = true;
        }
        if (surname.getText().isBlank()) {
            inputProblems = true;
        }
        if (email.getText().isBlank()) {
            inputProblems = true;
        }
        if (phoneNumber.getText().isBlank()) {
            inputProblems = true;
        }

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

        if (existingBooking.isConfirmed()) {
            notificationLbl.setText("  Booking is confirmed and a confirmation email has been sent.");

        } else if (existingBooking.isFormSubmitted()) {
            confirmBooking.setEnabled(true);
            notificationLbl.setText("  Booking is provisionally booked and the customer has confirmed the booking.");

        } else if (existingBooking.isProvisionallyBooked()){
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
}