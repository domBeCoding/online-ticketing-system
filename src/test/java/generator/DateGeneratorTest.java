package generator;

import org.example.generator.DateGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateGeneratorTest {

    @Test
    public void whenValuesInputted_thenCorrectDateIsGenerated(){
        DateGenerator dateGenerator = new DateGenerator();
        List<String> generatedDates = dateGenerator.generate("05-01-25", "10");

        List<String> expectDates = List.of("06-01-25", "07-01-25", "08-01-25", "09-01-25", "10-01-25", "11-01-25", "12-01-25", "13-01-25", "14-01-25", "15-01-25");
        assertEquals(expectDates, generatedDates);
    }

    @Test
    public void whenDaysGoesIntoNewMonth_thenCorrectDateIsGenerated(){
        DateGenerator dateGenerator = new DateGenerator();
        List<String> generatedDates = dateGenerator.generate("25-01-25", "10");

        List<String> expectDates = List.of("26-01-25", "27-01-25", "28-01-25", "29-01-25", "30-01-25", "31-01-25", "01-02-25", "02-02-25", "03-02-25", "04-02-25");
        assertEquals(expectDates, generatedDates);
    }
}
