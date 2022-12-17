package financeapp.monthPlan.models.planTransactionModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import financeapp.monthPlan.models.MonthPlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public abstract class PlanTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String Description;
    private Double Amount;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "month_plan")
    @JsonIgnore
    private MonthPlan monthPlan;

}
