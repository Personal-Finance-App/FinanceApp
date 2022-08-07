package financeapp.monthReport;

import com.google.gson.Gson;
import financeapp.monthReport.services.ReportService;
import financeapp.users.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController()
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

    private final UserRepo userRepo;
    private final ReportService reportService;

    @PostMapping("/createReport")
    public ResponseEntity<?> createReport(Authentication authentication, @RequestBody TimeSpanData timeSpanData) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        reportService.create(user, timeSpanData.getTimeStart(), timeSpanData.getTimeEnd());
        return ResponseEntity.ok().body("OK");

    }

    @PostMapping("/createReport")
    public ResponseEntity<?> createReport(Authentication authentication, Integer month, Integer year) {
        var dateInfo = new TimeSpanData();

        var startDate = LocalDateTime.of(year, month, 1, 0, 0);

        var endDate = LocalDateTime.of(year, month, 1, 23, 59)
                .plusDays(startDate.toLocalDate().lengthOfMonth() - 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");

        dateInfo.setStart(startDate.format(formatter));
        dateInfo.setEnd(endDate.format(formatter));
        return createReport(authentication, dateInfo);

    }

    @PostMapping("/createReport")
    public ResponseEntity<?> createReport(Authentication authentication) {
        return createReport(authentication, LocalDateTime.now().getMonthValue(), LocalDateTime.now().getYear());

    }


    @PostMapping("/get")
    public ResponseEntity<?> getReport(Authentication authentication,
                                       @RequestParam Integer month,
                                       @RequestParam Integer year) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        try {
            var report = reportService.findReport(user, month, year).toWrapper();
            return ResponseEntity.ok().body(new Gson().toJson(report));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
        }
    }

    ;


}
