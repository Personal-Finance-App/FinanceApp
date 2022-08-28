package financeapp.bankConnection.sberbank;

import financeapp.bankConnection.Interface.BankConnection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class SberbankConnection extends BankConnection {
    private String pinCode;
    private String mGUID;
}
