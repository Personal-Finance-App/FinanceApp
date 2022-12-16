package financeapp.monthPlan.models.planTransactionModels;

import financeapp.monthPlan.models.MonthPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
public abstract class PlanTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String Description;
    private Double Amount;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "month_plan")
    private MonthPlan monthPlan;

}
