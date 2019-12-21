package pl.com.app.repository.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.repository.model.enums.EPayment;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = TableNames.PAYMENTS)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false, unique = true)
    private EPayment ePayment;

    @OneToMany(mappedBy = "payment")
    private Set<CustomerOrder> customerOrders = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
                ePayment == payment.ePayment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ePayment);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", ePayment=" + ePayment +
                '}';
    }
}
