package pl.openthejar.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Encja produktu
 */
@Entity
@NoArgsConstructor
@Setter @Getter
public class Product extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Setter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private ProductType type;

    @Column(nullable = false)
    private Integer quantity;

    public Product(ProductType type, Integer quantity) {
        this.type = type;
        this.quantity = quantity;
    }
}
