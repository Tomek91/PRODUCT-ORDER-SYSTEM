package pl.com.app.repository;


import pl.com.app.repository.generic.GenericRepository;
import pl.com.app.repository.model.Producer;

import java.util.List;

public interface ProducerRepository extends GenericRepository<Producer> {
    List<Producer> findAllWithFetchProducts();
}
