package com.fin_stream_service.features.stocks;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockService {

    Flux<Stock> getAllStocks();
    Mono<Stock> getStockBySymbol(String symbol);
    Flux<Stock> searchStocks(String query);
}
