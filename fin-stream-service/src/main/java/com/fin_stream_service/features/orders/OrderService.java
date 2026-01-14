package com.fin_stream_service.features.orders;

import com.fin_stream_service.common.dto.OrderRequestDTO;
import com.fin_stream_service.common.dto.OrderResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    // Orchestrates the whole Buy/Sell flow
    Mono<OrderResponseDTO> executeOrder(OrderRequestDTO request);
    Flux<Order> getOrderHistory(Long userId);
}
