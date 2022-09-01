package financeapp.category.entity;


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
    private String code;

    @ManyToOne
    @JoinColumn(name = "link_category_id")
    private Category linkCategory;

    public MccToCategory(String code) {
        this.code = code;
    }

    public Category getLinkCategory() {
        return linkCategory;
    }

    public void setLinkCategory(Category linkCategory) {
        this.linkCategory = linkCategory;
    }
}
