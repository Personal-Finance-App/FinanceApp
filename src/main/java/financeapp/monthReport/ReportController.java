package financeapp.monthReport;

import com.google.gson.Gson;
import financeapp.monthReport.services.ReportService;
import financeapp.users.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController()
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

    private final UserRepo userRepo;
    private final ReportService reportService;

    @PostMapping(value = "/createReport/startend")
    public ResponseEntity<?> createReport(Authentication authentication, @RequestBody TimeSpanData timeSpanData) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        reportService.create(user, timeSpanData.getTimeStart(), timeSpanData.getTimeEnd());
        return ResponseEntity.ok().body("OK");

    }

    @PostMapping(value = "/createReport/monthYear")
    public ResponseEntity<?> createReport(Authentication authentication, @RequestBody MonthYear data) {
        var dateInfo = new TimeSpanData(data.getMonth(), data.getYear());
        return createReport(authentication, dateInfo);

    }

    @PostMapping("/createReport/auto")
    public ResponseEntity<?> createReport(Authentication authentication) {
        return createReport(authentication, new MonthYear(LocalDateTime.now().getMonthValue(), LocalDateTime.now().getYear()));

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

}

@Data
@AllArgsConstructor
class MonthYear {
    private Integer month;
    private Integer year;
}
