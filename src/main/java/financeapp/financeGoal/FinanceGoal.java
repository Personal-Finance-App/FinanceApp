package financeapp.financeGoal;

import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.users.CustomUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FinanceGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "linked_account_id")
    private SavingAccount linkedAccount;

    private String friendlyName;
    private LocalDate achieveDate;
    private double goalAmount;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;

    public FinanceGoal(SavingAccount linkedAccount, String friendlyName, LocalDate achieveDate, double goalAmount, CustomUser user) {
        this.linkedAccount = linkedAccount;
        this.friendlyName = friendlyName;
        this.achieveDate = achieveDate;
        this.goalAmount = goalAmount;
        this.user = user;
    }

    public void setImage(String image) {
        this.imageUrl = image;
    }

    public double MonthlyFee() {
        var saved = this.linkedAccount.getBalance();
        var rest = this.goalAmount - saved;
        if (rest <= 0)
            return 0;
        var period = Period.between(LocalDate.now(), achieveDate);
        var years = period.getYears();
        var months = years * 12 + period.getMonths();
        return Math.ceil(rest / months);
    }
}
