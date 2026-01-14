package com.fin_stream_service.features.watchlist;

import com.fin_stream_service.common.dto.WatchlistResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WatchlistService {

    Mono<Watchlist> addToWatchlist(Long userId, Long stockId);
    Mono<Void> removeFromWatchlist(Long userId, Long stockId);
    // Crucial: Returns a DTO containing Stock Name + Live Price from Redis
    Flux<WatchlistResponseDTO> getFullWatchlist(Long userId);
}
