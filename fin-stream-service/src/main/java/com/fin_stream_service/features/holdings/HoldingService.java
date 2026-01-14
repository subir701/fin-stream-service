package com.fin_stream_service.features.holdings;

import com.fin_stream_service.common.dto.HoldingResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface HoldingService {
    Flux<HoldingResponseDTO> getUserHoldings(Long userId);
    // Internal logic to update holdings after an order
    Mono<Holding> updateHoldingAfterTrade(Long userId, Long stockId, Long qty, BigDecimal price);
}
