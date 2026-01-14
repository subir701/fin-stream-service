package com.fin_stream_service.features.watchlist;

import com.fin_stream_service.common.dto.StockUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/stream")
@RequiredArgsConstructor
public class WatchlistController {

    private final ReactiveRedisTemplate<String, StockUpdate> redisTemplate;

    // This endpoint stays open! Browser receives data every second.
    @GetMapping(value = "/prices/{symbol}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StockUpdate> streamStockPrice(@PathVariable String symbol) {
        return redisTemplate.listenToChannel("TICKER:" + symbol)
                .map(ReactiveSubscription.Message::getMessage);
    }
}