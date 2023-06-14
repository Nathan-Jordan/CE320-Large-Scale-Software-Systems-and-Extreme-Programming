package MailingSystem;

import BookingSystem.BookingSystem;
import CSVReadWriting.HouseBooking;

public class MailHandler {

    private final EmailObject emailObject = new EmailObject();

    public void sendEmail(BookingSystem bookingSystem, HouseBooking booking, String emailType) {
        String messageSubject = "";
        String messageBody = "";

        switch (emailType) {
            case "available" -> {
                messageSubject = "Colchester Garrison Booking Available - " + bookingSystem.getStartDate() + " to " + bookingSystem.getEndDate();
                messageBody = "<p>Dear Mr/Mrs " + booking.getSurname() + ",</p><br>" +
                        "<p>Please see your booking details below:</p><br>" +
                        "<p>Name: " + booking.getFirstName() + " " + booking.getSurname() + "</p>" +
                        "<p>House: " + bookingSystem.getHouseNumber() + ", Wivenhoe Park, Colchester, Essex, CO4 3SQ</p>" +
                        "<p>Check in:  " + bookingSystem.getStartDate() + "</p>" +
                        "<p>Check out: " + bookingSystem.getEndDate() + "</p><br>" +
                        "<p>Cost: £" + bookingSystem.getCost() + "</p><br>" +
                        "<p>To Confirm the booking please complete the Google Form linked below:</p>" +
                        "<p>https://forms.gle/5oKrUAXCTiBKpjN17</p><br>";
            }
            case "confirmation" -> {
                messageSubject = "Colchester Garrison Booking Confirmed - " + bookingSystem.getStartDate() + " to " + bookingSystem.getEndDate();
                messageBody = "<p>Dear Mr/Mrs " + booking.getSurname() + ",</p><br>" +
                        "<p>Your booking has been confirmed, we look forward to seeing you soon.</p><br>" +
                        "<p>To confirm:</p>" +
                        "<p>House: " + bookingSystem.getHouseNumber() + "</p>" +
                        "<p>Check in:  " + bookingSystem.getStartDate() + "</p>" +
                        "<p>Check out: " + bookingSystem.getEndDate() + "</p><br>";
            }
            case "cancellation" -> {
                messageSubject = "Colchester Garrison Booking Cancelled - " + bookingSystem.getStartDate() + " to " + bookingSystem.getEndDate();
                messageBody = "<p>Dear Mr/Mrs " + booking.getSurname() + ",</p><br>" +
                        "<p>Your booking has been cancelled.</p><br>" +
                        "<p>The below booking has been cancelled:</p>" +
                        "<p>House: " + bookingSystem.getHouseNumber() + "</p>" +
                        "<p>Check in:  " + bookingSystem.getStartDate() + "</p>" +
                        "<p>Check out: " + bookingSystem.getEndDate() + "</p><br>";
            }
        }

        messageBody += "<p>Kind Regards,</p>" +
                "<p>Colchester Garrison Officer</p>";

        emailObject.sendEmail(messageSubject, booking.getEmail(), messageBody);
    }
}
