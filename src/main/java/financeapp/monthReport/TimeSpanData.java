package financeapp.monthReport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSpanData {
    private String start;
    private String end;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
    ;

    public TimeSpanData(Integer month, Integer year) {
        var startDate = LocalDateTime.of(year, month, 1, 0, 0);

        var endDate = LocalDateTime.of(year, month, 1, 23, 59)
                .plusDays(startDate.toLocalDate().lengthOfMonth() - 1);

        this.start = startDate.format(formatter);
        this.end = endDate.format(formatter);
    }

    public LocalDate getFormatStart() {
        return getLocalDate(start);
    }

    public LocalDate getFormatEnd() {
        return getLocalDate(end);
    }

    public LocalDate getLocalDate(String str) {
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
