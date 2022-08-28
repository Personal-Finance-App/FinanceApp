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

/**
 * Часть отчета, которая хранит в себе пару категорий - транзакции
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReportCategoryPart {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Double sum;
    @OneToMany
    private List<AbstractTransaction> transactionList;

    public ReportCategoryPart(Category category) {
        this.category = category;
        transactionList = new ArrayList<>();
        sum = Double.valueOf(0);
    }


    public void AddTransaction(AbstractTransaction transaction) {
        sum += transaction.getAmount();
        transactionList.add(transaction);
    }

    public ReportPartWrapper toWrapper() {
        return new ReportPartWrapper(category, transactionList);
    }
}
