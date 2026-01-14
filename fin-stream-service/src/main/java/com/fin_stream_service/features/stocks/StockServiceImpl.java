package com.fin_stream_service.features.stocks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockServiceImpl implements StockService{
    private final StockRepository stockRepository;

    @Override
    public Flux<Stock> getAllStocks() {
        log.info("Fetching all available stocks");
        return stockRepository.findAll();
    }

    @Override
    public Mono<Stock> getStockBySymbol(String symbol) {
        log.info("Fetching stock details for symbol: {}", symbol);
        return stockRepository.findBySymbol(symbol)
                .switchIfEmpty(Mono.error(new RuntimeException("Stock not found with symbol: " + symbol)));
    }

    @Override
    public Flux<Stock> searchStocks(String query) {
        log.info("Searching for stocks matching: {}", query);
        // Using a standard repository pattern for searching
        // You'll need to add 'findAllByStockNameContainingIgnoreCase' to your StockRepository
        return stockRepository.findAllByStockNameContainingIgnoreCase(query);
    }
}
