package ChoosyApplication;

import BookingSystem.BookingSystem;
import CSVReadWriting.BookingDate;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ComboBoxRenderer extends JPanel implements ListCellRenderer {

    JPanel textPanel;
    JLabel text;
    BookingSystem bookingSystem;

    public ComboBoxRenderer(BookingSystem bookingSystem) {
        this.bookingSystem = bookingSystem;

        textPanel = new JPanel();
        textPanel.add(this);
        text = new JLabel();
        text.setOpaque(true);
        textPanel.add(text);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        if (isSelected) {
            setBackground(list.getSelectionBackground());
        } else {
            setBackground(Color.WHITE);
        }

        text.setBackground(getBackground());
        text.setText(value.toString());


        BookingDate dateCell = bookingSystem.getBookingFromDate(text.getText());
        dateCell.getHouse(bookingSystem.getHouseNumber()).getStatus();


        String status = dateCell.getHouse(bookingSystem.getHouseNumber()).getStatus();

        if (Objects.equals(status, null)) {
            text.setForeground(Color.GREEN);    //Booking Available

        } else if (Objects.equals(status, "Confirmed")) {
            text.setForeground(Color.RED);

        } else if (Objects.equals(status, "Provisionally Booked")) {
            text.setForeground(Color.BLUE);

        } else {
            text.setForeground(Color.BLACK);    //Form Submitted
        }

        return text;
    }

}
