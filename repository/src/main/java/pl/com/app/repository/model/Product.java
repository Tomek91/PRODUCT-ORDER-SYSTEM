package pl.com.app.repository.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.repository.model.enums.EGuarantee;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = TableNames.PRODUCTS)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    private Set<CustomerOrder> customerOrders = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ElementCollection
    @CollectionTable(
            name = "guarantee_components",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "eGuarantees")
    @Enumerated(EnumType.STRING)
    private Set<EGuarantee> eGuarantees;

    @OneToMany( mappedBy = "product")
    private Set<Stock> stock = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(producer, product.producer) &&
                Objects.equals(category, product.category) &&
                Objects.equals(eGuarantees, product.eGuarantees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, producer, category, eGuarantees);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", producer=" + producer +
                ", category=" + category +
                ", eGuarantees=" + eGuarantees +
                '}';
    }
}
