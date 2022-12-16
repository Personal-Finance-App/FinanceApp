package financeapp.monthPlan.models;


import financeapp.monthPlan.models.planTransactionModels.*;
import financeapp.users.CustomUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MonthPlan {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer month;
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;

    @OneToMany(cascade = CascadeType.ALL)
    private List<IncomePlanTransaction> expectedIncome;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SavePlanTransaction> toSave;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OtherPlanTransaction> otherPlans;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RequiredPlanTransaction> toRequiredPayment;

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public Double getExpectedIncomeSum() {
        return this.expectedIncome.stream().mapToDouble(PlanTransaction::getAmount).sum();
    }

    public Double getToSaveSum() {
        return this.toSave.stream().mapToDouble(PlanTransaction::getAmount).sum();
    }

    public Double getToRequiredSum() {
        return this.toRequiredPayment.stream().mapToDouble(PlanTransaction::getAmount).sum();
    }

    public Double getOtherSum() {
        return this.otherPlans.stream().mapToDouble(PlanTransaction::getAmount).sum();
    }

}
