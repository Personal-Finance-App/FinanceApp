package financeapp.monthReport.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * На транзакции можно будет навесить "Метки"
 * Которые впоследствии будут играть свою роль при анализе
 * Причем метки устанавливает не только система, но и пользователь
 */
@Entity
@Getter
@NoArgsConstructor
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    public Label(String name) {
        this.name = name;
    }
}
