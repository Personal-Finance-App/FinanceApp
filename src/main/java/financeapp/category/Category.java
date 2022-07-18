package financeapp.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    private String categoryName;

    private String mccCode;

    public Category(String categoryName, String mccCode) {
        id = UUID.randomUUID();
        this.categoryName = categoryName;
        this.mccCode = mccCode;
    }

}
