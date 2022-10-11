package financeapp.monthReport.entity;

import financeapp.transaction.models.PayTransaction;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Analysis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private Double income = 0D;
    private Double requirePayments = 0D;
    private Double saved = 0D;
    private Double other = 0D;
    @OneToOne
    @JoinColumn(name = "biggest_payment_id")
    private PayTransaction biggestPayment;
    private Double averagePerDay;

}
