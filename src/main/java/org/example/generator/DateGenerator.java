package org.example.generator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateGenerator {

    public List<String> generate(String startDate, String airingDays){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        LocalDate date = LocalDate.parse(startDate, dateTimeFormatter);

        List<String> dates = new ArrayList<>();

        for(int i = 0; i < Integer.parseInt(airingDays); i++){
            date = date.plusDays(1);
            dates.add(date.format(dateTimeFormatter));
        }

        return dates;
    }
}
