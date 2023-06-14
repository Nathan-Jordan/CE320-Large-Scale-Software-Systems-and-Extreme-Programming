package Dates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


// TODO: WARNING - can only compare dates not months!!!!!

public class CustomDate {
    public LocalDate date;

    public CustomDate(String dateIn) {
        date = getDate(dateIn);
    }

    public LocalDate getDate(String stringDate) {
        String[] dates = stringDate.split("/");
        int[] date = new int[dates.length];

        for(int i = 0; i < dates.length; i++)
            date[i] = Integer.parseInt(dates[i]);

        return LocalDate.of(date[2], date[1], date[0]);
    }


    public String getVerbalName() {
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }

    public static boolean isDate1AfterDate2(String startDate, String endDate) {
        CustomDate start = new CustomDate(startDate);
        CustomDate end = new CustomDate(endDate);

        return start.date.isAfter(end.date);
    }

    @Override
    public String toString() {
        String[] dates = date.toString().split("-");
        return dates[2] + "/" + dates[1] + "/" + dates[0];
    }

}
