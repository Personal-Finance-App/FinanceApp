package financeapp.Accounts.models;

import financeapp.BankConnection.Interface.BankConnection;
import financeapp.Users.CustomUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Account {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private LocalDate lastSync;
    private double balance;
    @OneToOne
    @JoinColumn(name = "bank_connection_id")
    private BankConnection bankConnection;

    @OneToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;


}
