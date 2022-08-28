package financeapp.monthReport;

import com.google.gson.Gson;
import financeapp.monthReport.services.ReportService;
import financeapp.users.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @PostMapping("/save-comment/{year}/{month}")
    @ResponseBody
    public ResponseEntity<?> saveComments(Authentication authentication,
                                          @RequestBody CommentData data,
                                          @PathVariable Integer month,
                                          @PathVariable Integer year) throws Exception {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var report = reportService.findReport(user, month, year);

        if (reportService.setComment(report, data.getComment()))
            return ResponseEntity.ok().body("saved");
        return ResponseEntity.internalServerError().body("Error");
    }

}

@Data
@AllArgsConstructor
class MonthYear {
    private Integer month;
    private Integer year;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CommentData {
    private String comment;
}

