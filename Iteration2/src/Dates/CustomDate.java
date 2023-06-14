package Dates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


public class CustomDate implements Comparable<CustomDate>{

    public LocalDate date;
    public int day;
    public int month;
    public int year;

    public CustomDate(String dateIn) {
        date = parseDate(dateIn);
    }

    public LocalDate parseDate(String stringDate) {
        String[] dates = stringDate.split("/");
        int[] date = new int[dates.length];

        for(int i = 0; i < dates.length; i++)
            date[i] = Integer.parseInt(dates[i]);
        day = date[0];
        month = date[1];
        year = date[2];
        return LocalDate.of(date[2], date[1], date[0]);
    }

    public String getVerbalName() {
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }

    public static boolean isDate1AfterDate2(String date1, String date2) {
        CustomDate start = new CustomDate(date1);
        CustomDate end = new CustomDate(date2);

        return start.date.isAfter(end.date);
    }

    public boolean isInPeriod(int month, int year) {
        return this.month == month && this.year == year;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        String[] dates = date.toString().split("-");
        return dates[2] + "/" + dates[1] + "/" + dates[0];
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;

        if(!(obj instanceof CustomDate))
            return false;

        CustomDate other = (CustomDate) obj;
        return day == other.day && month == other.month && year == other.year;
    }

    @Override
    public int compareTo(CustomDate o) {
        return date.compareTo(o.date);
    }
}
