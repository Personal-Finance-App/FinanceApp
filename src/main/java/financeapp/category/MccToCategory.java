package financeapp.category;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MccToCategory {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "link_category_id")
    private Category linkCategory;

    public Category getLinkCategory() {
        return linkCategory;
    }

    public void setLinkCategory(Category linkCategory) {
        this.linkCategory = linkCategory;
    }
}
