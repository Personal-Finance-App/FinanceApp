package financeapp.monthPlan.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import financeapp.monthPlan.models.planTransactionModels.*;
import financeapp.users.CustomUser;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MonthPlan {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer month;
    private Integer year;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "linked_user_id")
    @JsonIgnore
    private CustomUser linkedUser;

    @OneToMany(cascade = CascadeType.ALL)
    private List<IncomePlanTransaction> expectedIncome;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SavePlanTransaction> toSave;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OtherPlanTransaction> otherPlans;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RequiredPlanTransaction> toRequiredPayment;

    public CustomUser getLinkedUser() {
        return linkedUser;
    }

    public void setLinkedUser(CustomUser user) {
        this.linkedUser = user;
    }

//    public Double getExpectedIncomeSum() {
//        return this.expectedIncome.stream().mapToDouble(PlanTransaction::getAmount).sum();
//    }
//
//    public Double getToSaveSum() {
//        return this.toSave.stream().mapToDouble(PlanTransaction::getAmount).sum();
//    }
//
//    public Double getToRequiredSum() {
//        return this.toRequiredPayment.stream().mapToDouble(PlanTransaction::getAmount).sum();
//    }
//
//    public Double getOtherSum() {
//        return this.otherPlans.stream().mapToDouble(PlanTransaction::getAmount).sum();
//    }

}
