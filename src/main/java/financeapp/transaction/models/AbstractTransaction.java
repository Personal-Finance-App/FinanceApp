package financeapp.transaction.models;

import financeapp.accounts.models.Account;
import financeapp.category.entity.Category;
import financeapp.monthReport.entity.Label;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private double amount;
    private String description;
    private LocalDateTime dateTime;
    @ManyToOne(cascade = {CascadeType.DETACH
            , CascadeType.MERGE
            , CascadeType.PERSIST
            , CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;
    private String merchant;

    @ManyToMany
    @JoinTable(name="lable_transaction",
    joinColumns = @JoinColumn(name = "transaction_id"),
    inverseJoinColumns = @JoinColumn(name = "lable_id"))
    private List<Label> labelList;

    @ManyToOne(cascade = {CascadeType.DETACH
            , CascadeType.MERGE
            , CascadeType.PERSIST
            , CascadeType.REFRESH})
    @JoinColumn(name = "account_id")
    private Account account;

    public AbstractTransaction(double amount, String description, LocalDateTime dateTime, String merchant) {
        this.amount = amount;
        this.description = description;
        this.dateTime = dateTime;
        this.merchant = merchant;
        this.labelList = new ArrayList<>();
    }

    public void addLabel(Label label) {
        labelList.add(label);
    }

    public abstract String friendName();
}
