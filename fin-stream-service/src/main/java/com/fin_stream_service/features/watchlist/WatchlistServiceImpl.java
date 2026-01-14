package com.fin_stream_service.features.watchlist;

import com.fin_stream_service.common.dto.StockUpdate;
import com.fin_stream_service.common.dto.WatchlistResponseDTO;
import com.fin_stream_service.features.stocks.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
@RequiredArgsConstructor
public class WatchlistServiceImpl implements WatchlistService{

    private final WatchlistRepository watchlistRepository;
    private final StockRepository stockRepository;
    private final ReactiveRedisTemplate<String, StockUpdate> redisTemplate;

    @Override
    public Mono<Watchlist> addToWatchlist(Long userId, Long stockId) {
        return watchlistRepository.save(new Watchlist(null, userId,stockId))
                .doOnSuccess(w -> log.info("Stock {} added to user {} watchlist", stockId, userId));
    }

    @Override
    public Mono<Void> removeFromWatchlist(Long userId, Long stockId) {
        // We assume your repository has a delete method for this
        return watchlistRepository.deleteByUserIdAndStockId(userId, stockId);
    }

    @Override
    public Flux<WatchlistResponseDTO> getFullWatchlist(Long userId) {
        return watchlistRepository.findByUserId(userId)
                .flatMap(item -> stockRepository.findById(item.getStockId())
                        .flatMap(stock -> {
                            // 1. Get the latest price from Redis (Static cache lookup)
                            return redisTemplate.opsForValue().get("TICKER:" + stock.getSymbol())
                                    .map(update -> mapToDTO(stock, update.getLtp()))
                                    // 2. Fallback to base price if Redis is empty
                                    .defaultIfEmpty(mapToDTO(stock, stock.getBasePrice()));
                        })
                );
    }

    private WatchlistResponseDTO mapToDTO(com.fin_stream_service.features.stocks.Stock stock, BigDecimal currentPrice) {
        BigDecimal change = currentPrice.subtract(stock.getBasePrice());
        BigDecimal percent = change.divide(stock.getBasePrice(), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));

        return WatchlistResponseDTO.builder()
                .stockId(stock.getId())
                .symbol(stock.getSymbol())
                .stockName(stock.getStockName())
                .currentPrice(currentPrice)
                .priceChange(change)
                .percentChange(percent)
                .build();
    }
}
