package pl.com.app.repository;


import pl.com.app.repository.generic.GenericRepository;
import pl.com.app.repository.model.Payment;
import pl.com.app.repository.model.enums.EPayment;

import java.util.Optional;

public interface PaymentRepository extends GenericRepository<Payment> {
    Optional<Payment> findByName(EPayment ePayment);
}
