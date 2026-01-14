package com.fin_stream_service.features.holdings;

import com.fin_stream_service.common.dto.HoldingResponseDTO;
import com.fin_stream_service.common.dto.StockUpdate;
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
public class HoldingServiceImpl implements HoldingService{
    private final HoldingRepository holdingRepository;
    private final StockRepository stockRepository;
    private final ReactiveRedisTemplate<String, StockUpdate> redisTemplate;

    @Override
    public Flux<HoldingResponseDTO> getUserHoldings(Long userId) {
        return holdingRepository.findByUserId(userId)
                .flatMap(holding -> stockRepository.findById(holding.getStockId())
                        .flatMap(stock -> redisTemplate.opsForValue().get("TICKER:" + stock.getSymbol())
                                .map(update -> mapToDTO(holding, stock.getStockName(), stock.getSymbol(), update.getLtp()))
                                .defaultIfEmpty(mapToDTO(holding, stock.getStockName(), stock.getSymbol(), holding.getAvgBuyPrice()))
                        )
                );
    }

    @Override
    public Mono<Holding> updateHoldingAfterTrade(Long userId, Long stockId, Long qty, BigDecimal price) {
        return holdingRepository.findByUserIdAndStockId(userId, stockId)
                .flatMap(existing -> {
                    // Calculation logic remains the same
                    BigDecimal totalOldCost = existing.getAvgBuyPrice().multiply(BigDecimal.valueOf(existing.getQuantity()));
                    BigDecimal totalNewCost = price.multiply(BigDecimal.valueOf(qty));
                    Long totalQty = existing.getQuantity() + qty;

                    BigDecimal newAvgPrice = totalOldCost.add(totalNewCost)
                            .divide(BigDecimal.valueOf(totalQty), 2, RoundingMode.HALF_UP);

                    existing.setQuantity(totalQty);
                    existing.setAvgBuyPrice(newAvgPrice);
                    return holdingRepository.save(existing);
                })
                // Use defer to prevent the new Holding from being created unless needed
                .switchIfEmpty(Mono.defer(() ->
                        holdingRepository.save(new Holding(null, userId, stockId, qty, price))
                ));
    }

    private HoldingResponseDTO mapToDTO(Holding h, String name, String symbol, BigDecimal ltp) {
        BigDecimal invested = h.getAvgBuyPrice().multiply(BigDecimal.valueOf(h.getQuantity()));
        BigDecimal current = ltp.multiply(BigDecimal.valueOf(h.getQuantity()));
        return HoldingResponseDTO.builder()
                .symbol(symbol).stockName(name).quantity(h.getQuantity().intValue())
                .avgBuyPrice(h.getAvgBuyPrice()).currentPrice(ltp)
                .investedValue(invested).currentValue(current)
                .totalGain(current.subtract(invested))
                .build();
    }
}
