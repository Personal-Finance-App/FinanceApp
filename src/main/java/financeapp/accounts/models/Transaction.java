package financeapp.accounts.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private double amount;
    private String placeName;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private LocalDate date;

    public Transaction(double amount, String placeName, LocalDate date){
        this.amount = amount;
        this.placeName = placeName;
        this.date = date;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
