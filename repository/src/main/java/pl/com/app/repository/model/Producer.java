package pl.com.app.repository.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = TableNames.PRODUCERS)
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false)
    private String name;

    @OneToMany(mappedBy = "producer")
    private Set<Product> products = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToOne()
    @JoinColumn(name = "trade_id", nullable = false)
    private Trade trade;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(id, producer.id) &&
                Objects.equals(name, producer.name) &&
                Objects.equals(country, producer.country) &&
                Objects.equals(trade, producer.trade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country, trade);
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country +
                ", trade=" + trade +
                '}';
    }
}
