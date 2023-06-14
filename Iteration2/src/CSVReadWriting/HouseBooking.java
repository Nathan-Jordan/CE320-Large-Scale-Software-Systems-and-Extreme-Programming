package CSVReadWriting;

import java.util.Objects;

public class HouseBooking {

    private String firstName;
    private String surname;
    private String email;
    private String phoneNumber;

    private boolean provisionallyBooked;
    private boolean confirmed;
    private boolean formSubmitted;
    public boolean isEmpty;


    public HouseBooking(String[] bookingDetails) {
        if (bookingDetails.length == 6) {
            this.firstName = bookingDetails[1];
            this.surname = bookingDetails[2];
            this.email = bookingDetails[3];
            this.phoneNumber = bookingDetails[4];
            this.isEmpty = false;

            if (Objects.equals(bookingDetails[5], "Confirmed")) {
                confirmed = true;
            } else if (Objects.equals(bookingDetails[5], "Form Submitted")) {
                formSubmitted = true;
            } else if (Objects.equals(bookingDetails[5], "Provisionally Booked")) {
                provisionallyBooked = true;
            }
        }
    }

    public HouseBooking() {
        this.firstName = "";
        this.surname = "";
        this.email = "";
        this.phoneNumber = "";
        this.isEmpty = true;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isProvisionallyBooked() {
        return provisionallyBooked;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isFormSubmitted() {
        return formSubmitted;
    }

    public void setFormSubmitted(boolean formSubmitted) {
        this.formSubmitted = formSubmitted;
    }

    public String getStatus() {
        if (confirmed) {
            return "Confirmed";
        } else if (formSubmitted) {
            return "Form Submitted";
        } else if (provisionallyBooked) {
            return "Provisionally Booked";
        } else {
            return null;
        }
    }

    public boolean isBooked() {
        return confirmed || provisionallyBooked || formSubmitted;
    }


    @Override
    public String toString() {
        if (confirmed) {
            return "Confirmed";
        } else if (provisionallyBooked) {
            return "Provisionally Booked";
        } else {
            return "Not Booked";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof HouseBooking) {
            HouseBooking otherBooking = (HouseBooking) obj;

            return Objects.equals(this.firstName, otherBooking.firstName)
                    &&
                    Objects.equals(this.surname, otherBooking.surname)
                    &&
                    Objects.equals(this.email, otherBooking.email)
                    &&
                    Objects.equals(this.phoneNumber, otherBooking.phoneNumber)
                    &&
                    Objects.equals(this.getStatus(), otherBooking.getStatus());
        }

        return false;
    }
}
