package com.fin_stream_service.features.stocks;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface StockRepository extends ReactiveCrudRepository<Stock, Long> {

    //// Custom query: Find a stock by their exact symbol
    Mono<Stock> findBySymbol(String symbol);
}
