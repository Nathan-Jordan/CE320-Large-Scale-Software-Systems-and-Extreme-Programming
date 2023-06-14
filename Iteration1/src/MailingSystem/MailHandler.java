package MailingSystem;

import CSVReadWriting.HouseBooking;

import java.util.Objects;

public class MailHandler {

    public void sendEmail(HouseBooking booking, int cost, Object Start_date, Object End_date, String email_type){

        //This section will print out the email to console currently, once SMTP server is working then the email handler can pull from these objects also

        EmailObject email;

        if (Objects.equals(email_type, "available")) {
             email = new EmailObject("available",booking.getFirstName(),booking.getSurname(), cost, Start_date, End_date);
             email.printEmail();
        }
        if (Objects.equals(email_type, "unavailable")) {
             email = new EmailObject("unavailable",booking.getFirstName(),booking.getSurname(), cost, Start_date, End_date);
            email.printEmail();
        }
        if (Objects.equals(email_type, "confirmation_email")) {
             email = new EmailObject("confirmation_email",booking.getFirstName(),booking.getSurname(), cost, Start_date, End_date);
            email.printEmail();
        }
    }
}
