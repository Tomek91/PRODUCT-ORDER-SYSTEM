package pl.com.app.repository;


import pl.com.app.repository.generic.GenericRepository;
import pl.com.app.repository.model.Stock;

import java.util.Optional;

public interface StockRepository extends GenericRepository<Stock> {
    Optional<Stock> findByProductIdAndShopId(Long productId, Long shopId);
}
