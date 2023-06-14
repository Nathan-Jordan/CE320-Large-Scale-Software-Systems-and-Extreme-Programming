package MailingSystem;

import java.util.Objects;

public class EmailObject {

    String subject; //Holds the subject of the email
    String text_format; // Holds the details for the main body.


    EmailObject(String email_type, String first_name, String last_name, int cost, Object start_date, Object end_date){

        if (Objects.equals(email_type, "unavailable")) {
            subject = "Subject: Booking Unavailable";
            text_format = "Dear Mr/Ms\n" +
                               "\n" +
                               "Unfortunately we do not have any availability for these current dates.\n" +
                               "\n" +
                               "Kind Regards,\n" +
                               "Colchester Garrison Officer";
        }

        if (Objects.equals(email_type, "available")) {
            subject = "Subject: Booking Available";
            text_format = "Dear Mr/Mrs " + last_name + "\n" +
                               "\n" +
                               "Please see your booking details below:\n" +
                               "\n" +
                               "Name: " + first_name + " " + last_name + "\n"+
                               "House: Wivenhoe Park, Colchester, Essex, CO4 3SQ\n" +
                               "\n"+
                               "Check in: " + start_date.toString() + "\n" +
                               "Check Out: " + end_date.toString() + "\n" +
                               "\n" + "Cost: £" + cost + ".00\n" +
                               "\n" +
                               "To Confirm the booking please complete the Google Form linked below:" + "\n" +
                               "https://forms.gle/5oKrUAXCTiBKpjN17\n" +
                               "\n" +
                               "Kind Regards,\n" +
                               "Colchester Garrison Officer";
        }

        if (Objects.equals(email_type, "confirmation_email")) {
            subject = "Subject: Booking Confirmed";
            text_format = "Dear Mr/Mrs " + last_name + "\n" +
                               "\n" +
                               "Your booking has been confirmed, we look forward to seeing you soon." + "\n" +
                               "\n" +
                               "To Confirm:\n" +
                               "Check in:  " + start_date.toString() + "\n" +
                               "Check Out: " + end_date.toString() + "\n" +
                               "\n" +
                               "Kind Regards,\n" +
                               "Colchester Garrison Officer";
        }
    }

    public void printEmail(){
        System.out.println(text_format);
    }
}
