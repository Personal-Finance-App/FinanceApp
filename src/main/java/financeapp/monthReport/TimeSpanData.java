package financeapp.monthReport;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class TimeSpanData {
    private String start;
    private String end;

    public LocalDate getFormatStart() {
        return getLocalDate(start);
    }

    public LocalDate getFormatEnd() {
        return getLocalDate(end);
    }

    public LocalDate getLocalDate(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        return LocalDate.parse(str, formatter);
    }

    public LocalDateTime getTimeStart() {
        return getFormatStart().atStartOfDay();
    }

    ;

    public LocalDateTime getTimeEnd() {
        return getFormatEnd().atTime(23, 59);
    }
}
