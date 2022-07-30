package financeapp.accounts.models;

import financeapp.transaction.models.AbstractTransaction;
import financeapp.users.CustomUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public abstract class Account {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private LocalDateTime lastSync;
    private double balance;

    private String idInSystem;
    private String friendlyName;
    private String provider;


    public Account(String idInSystem, String name, CustomUser user, String provider) {
        id = UUID.randomUUID();
        friendlyName = name;
        this.idInSystem = idInSystem;
        balance = 0;
        this.user = user;
        lastSync = null;
        this.provider = provider;
    }

//    @OneToOne
//    @JoinColumn(name = "bank_connection_id")
//    private BankConnection bankConnection;

    @OneToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<AbstractTransaction> transactionList;

    public boolean AddTransaction(AbstractTransaction transaction) {
        return transactionList.add(transaction);
    }


    public abstract String getName();

    public String getImageProviderUrl() {
        return switch (this.provider) {
            case "Тинькоф" -> "bank-logo/tinkoff.png";
            default -> "";
        };
    }
}
