package financeapp.monthReport.entity;


import financeapp.monthReport.wrappers.ReportWrapper;
import financeapp.users.CustomUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "linked_user_id")
    private CustomUser linkedUser;
    private Integer year;
    private Integer month;

    @OneToMany
    private List<ReportPart> parts;

    private LocalDateTime updated;

    public Report(CustomUser user, Integer year, Integer month, List<ReportPart> parts, LocalDateTime sync) {
        this.linkedUser = user;
        this.year = year;
        this.month = month;
        this.parts = parts;
        this.updated = sync;
    }

    public ReportWrapper toWrapper() {
        return new ReportWrapper(year, month, updated, parts);
    }
}
