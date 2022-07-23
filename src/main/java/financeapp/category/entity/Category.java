package financeapp.category.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String categoryName;

    public void setId(Long id) {
        this.id = id;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

}
