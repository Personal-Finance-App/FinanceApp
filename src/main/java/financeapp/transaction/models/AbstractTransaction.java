package financeapp.transaction.models;

import financeapp.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String merchant;

    public AbstractTransaction(double amount, String description, LocalDateTime dateTime, String merchant) {
        this.amount = amount;
        this.description = description;
        this.dateTime = dateTime;
        this.merchant = merchant;
    }
}