package com.fin_stream_service.features.holdings;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface HoldingRepository extends ReactiveCrudRepository<Holding,Long> {

    Flux<Holding> findByUserIdAndStockId(Long userId, Long stockId);
}
