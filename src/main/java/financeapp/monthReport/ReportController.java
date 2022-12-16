package financeapp.monthReport;

import com.google.gson.Gson;
import financeapp.monthReport.entity.Report;
import financeapp.monthReport.services.ReportService;
import financeapp.monthReport.wrappers.ReportWrapper;
import financeapp.users.UserRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RestController()
@RequestMapping("/report")
@AllArgsConstructor
@SecurityRequirement(name = "javainuseapi")
@Tag(name = "Report", description = "Connected with report things")
public class ReportController {

    private final UserRepo userRepo;
    private final ReportService reportService;

    @PostMapping(value = "/createReport/startend")
    @ResponseBody
    public ResponseEntity<?> createReport(Authentication authentication, @RequestBody TimeSpanData timeSpanData) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        reportService.create(user, timeSpanData.getTimeStart(), timeSpanData.getTimeEnd());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("status", "ok"));

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
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(Collections.singletonMap("status", "saved"));
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("status", "error"));
    }

    @PostMapping(value = "/category-history")
    @ResponseBody
    public ResponseEntity<?> getHistory(Authentication authentication,
                                        @RequestBody CategoryHistory data) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        return ResponseEntity.ok().body(reportService.getCategoriesHistory(user, data.getCategoryId(), data.getLength()));
    }

    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<ReportWrapper>> getPreviousData(Authentication authentication) {
        var user = userRepo.findCustomUserByEmail(authentication.getName());
        var reports = reportService.findReportsByUserPeriod(user, 6);
        var reportsWrapper = reports.stream().map(Report::toWrapper).collect(Collectors.toList());
        return ResponseEntity.ok().body(reportsWrapper);
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

@Data
class CategoryHistory {
    private Long categoryId;
    private Integer length;
}