package com.fin_stream_service.simulator;

import com.fin_stream_service.common.dto.StockUpdate;
import com.fin_stream_service.features.stocks.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceSimulator implements PriceEngine{

    private final StockRepository stockRepository;
    private final ReactiveRedisTemplate<String, StockUpdate> redisTemplate;
    private final Random random = new Random();

    @Override
    @Scheduled(fixedRate = 1000)
    public void startPriceSimulation() {
        stockRepository.findAll()
                .flatMap(stock -> {
                    BigDecimal newPrice = calculateNewPrice(stock.getBasePrice());
                    StockUpdate update = new StockUpdate(
                            stock.getSymbol(),
                            newPrice,
                            newPrice.subtract(stock.getBasePrice()),
                            newPrice.divide(stock.getBasePrice(), 4, RoundingMode.HALF_UP).subtract(BigDecimal.ONE).multiply(new BigDecimal(100))
                    );
                    return broadcastPrice(update);
                })
                .subscribe(); // In a simulator, we subscribe to trigger the flux
    }

    @Override
    public Mono<Long> broadcastPrice(StockUpdate update) {
        // Return the Mono directly to the caller
        return redisTemplate.convertAndSend("TICKER:" + update.getSymbol(), update)
                .doOnSuccess(count -> log.debug("Price broadcasted for {}: {}", update.getSymbol(), update.getLtp()));
    }

    private BigDecimal calculateNewPrice(BigDecimal currentPrice){
        //Simulates a random walk: +/- 1% fluctuation
        double changePercent = (random.nextDouble() * 0.02) - 0.01;
        return currentPrice.multiply(BigDecimal.valueOf(1 + changePercent)).setScale(2, RoundingMode.HALF_UP);
    }
}
