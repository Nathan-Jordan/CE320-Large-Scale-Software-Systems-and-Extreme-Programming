package CSVReadWriting;

public class BookingDate {

    String date;
    HouseBooking houseOne;
    HouseBooking houseTwo;

    public BookingDate(String date, HouseBooking houseOne, HouseBooking houseTwo) {
        this.date = date;
        this.houseOne = houseOne;
        this.houseTwo = houseTwo;
    }

    public String getDate() {
        return date;
    }

    public HouseBooking getHouse(int houseNumber) {
        if (houseNumber == 1) {
            return houseOne;
        } else {
            return houseTwo;
        }
    }

    public void setHouse(int houseNumber, HouseBooking newBooking) {
        if (houseNumber == 1) {
            setHouseOne(newBooking);
        } else {
            setHouseTwo(newBooking);
        }
    }

    public HouseBooking getHouseOne() {
        return houseOne;
    }

    public HouseBooking getHouseTwo() {
        return houseTwo;
    }

    @Override
    public String toString() {
        return date + ":    House1: " + houseOne + "  -  House2: " + houseTwo;
    }

    public void setHouseOne(HouseBooking newHouseBooking) {
        this.houseOne = newHouseBooking;
    }

    public void setHouseTwo(HouseBooking newHouseBooking) {
        this.houseTwo = newHouseBooking;
    }
}

