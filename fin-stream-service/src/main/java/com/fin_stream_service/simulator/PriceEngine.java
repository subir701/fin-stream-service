package com.fin_stream_service.simulator;

import com.fin_stream_service.common.dto.StockUpdate;
import reactor.core.publisher.Mono;

public interface PriceEngine {

    //Start the price generation process.
    void startPriceSimulation();

    //Broadcasts a single price update
    Mono<Long> broadcastPrice(StockUpdate update);
}
