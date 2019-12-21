package pl.com.app.repository;


import pl.com.app.repository.generic.GenericRepository;
import pl.com.app.repository.model.CustomerOrder;

import java.util.List;

public interface CustomerOrderRepository extends GenericRepository<CustomerOrder> {
    List<CustomerOrder> findAllWithFetchGuarantee();
}
