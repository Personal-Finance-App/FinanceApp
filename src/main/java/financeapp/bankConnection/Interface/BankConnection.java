package financeapp.bankConnection.Interface;

import financeapp.users.CustomUser;
import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

/**
 * Абстрактный класс, от которого можно будет наследоваться другим классам, которые отвечают за соединение с банком
 * На каждого пользователя создается отедельный класс, для соединения, которые хранит всю необходимую информацию,
 * чтобы получить список транзакций
 */
@Entity
@Getter
public abstract class BankConnection {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;

    public BankConnection() {
        id = UUID.randomUUID();
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }


}
