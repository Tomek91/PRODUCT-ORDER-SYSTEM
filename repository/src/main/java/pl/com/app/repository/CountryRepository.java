package pl.com.app.repository;


import pl.com.app.repository.generic.GenericRepository;
import pl.com.app.repository.model.Country;

import java.util.Optional;

public interface CountryRepository extends GenericRepository<Country> {
    Optional<Country> findByName(String name);

}
