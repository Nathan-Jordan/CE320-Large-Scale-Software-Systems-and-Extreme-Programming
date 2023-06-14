package Calender;

import CSVReadWriting.HouseBooking;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DateButton extends JButton {

    private final HouseBooking booking;

    private final int date;
    String firstName;
    String surname;
    String email;
    String phoneNumber;
    String bookingStatus;

    public DateButton(HouseBooking booking, int date) {
        this.booking = booking;
        this.date = date;

        setupButton();
    }

    private void setupButton() {
        updateButtonText();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new ButtonLabel(String.valueOf(date), true));

        if (bookingStatus != null) {
            add(new ButtonLabel(" " + firstName, false));
            add(new ButtonLabel(" " + surname, false));
            add(new ButtonLabel(" " + email, false));
            add(new ButtonLabel(" " + phoneNumber, false));
            add(new ButtonLabel(" " + bookingStatus, false));

        }

        setColour();
    }

    private void updateButtonText() {
        firstName = booking.getFirstName();
        surname = booking.getSurname();
        email = booking.getEmail();
        phoneNumber = booking.getPhoneNumber();
        bookingStatus = booking.getStatus();
    }

    private void setColour() {
        Color colour = Color.GREEN;

        if (bookingStatus == null) {
            setBorder(new EmptyBorder(4,4,4,4));

        } else {
            switch (bookingStatus) {
                case "Confirmed" -> colour = Color.RED;
                case "Form Submitted" -> colour = Color.BLACK;
                case "Provisionally Booked" -> colour = Color.BLUE;
            }

            setBorder(new LineBorder(colour, 4));
        }
    }


    static class ButtonLabel extends JLabel {
        public ButtonLabel(String text, boolean isDate) {
            setText(text);

            if (isDate) {
                setFont(new Font("Arial", Font.PLAIN, 30));
            } else {
                setFont(new Font("Arial", Font.PLAIN, 16));
            }
        }
    }
}
