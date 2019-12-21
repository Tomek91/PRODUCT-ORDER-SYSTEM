package pl.com.app.repository;


import pl.com.app.repository.generic.GenericRepository;
import pl.com.app.repository.model.Customer;

import java.util.List;

public interface CustomerRepository extends GenericRepository<Customer> {
    List<Customer> findByNameSurname(String name, String surname);
}
