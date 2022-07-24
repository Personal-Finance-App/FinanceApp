package financeapp.monthReport;

import com.google.gson.Gson;
import financeapp.monthReport.services.ReportService;
import financeapp.users.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


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

    ;

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
