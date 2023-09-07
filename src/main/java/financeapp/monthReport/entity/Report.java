package financeapp.monthReport.entity;


import financeapp.monthReport.wrappers.ReportWrapper;
import financeapp.users.CustomUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReportCategoryPart> parts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "analysis_id")
    private Analysis analysis;

    private LocalDateTime updated;
    @Column(length = 600)
    private String comment;

    public Report(CustomUser user, Integer year, Integer month, List<ReportCategoryPart> parts, LocalDateTime sync) {
        this.linkedUser = user;
        this.year = year;
        this.month = month;
        this.parts = parts;
        this.updated = sync;
        this.comment = "";
    }

    public ReportWrapper toWrapper() {
        return new ReportWrapper(year, month, updated, parts, analysis);
    }
}
