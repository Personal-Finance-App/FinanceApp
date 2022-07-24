package financeapp.monthReport.entity;


import financeapp.category.entity.Category;
import financeapp.monthReport.wrappers.ReportPartWrapper;
import financeapp.transaction.models.AbstractTransaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReportPart {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany
    private List<AbstractTransaction> transactionList;

    public ReportPart(Category category) {
        this.category = category;
        transactionList = new ArrayList<>();
    }


    public void AddTransaction(AbstractTransaction transaction) {
        transactionList.add(transaction);
    }

    public ReportPartWrapper toWrapper() {
        return new ReportPartWrapper(category, transactionList);
    }
}
