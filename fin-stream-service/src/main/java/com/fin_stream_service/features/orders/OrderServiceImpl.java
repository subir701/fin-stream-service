package com.fin_stream_service.features.orders;

import com.fin_stream_service.common.dto.OrderRequestDTO;
import com.fin_stream_service.common.dto.OrderResponseDTO;
import com.fin_stream_service.features.holdings.HoldingService;
import com.fin_stream_service.features.users.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final HoldingService holdingService;

    @Override
    @Transactional
    @CircuitBreaker(name = "orderService", fallbackMethod = "orderFallback")
    public Mono<OrderResponseDTO> executeOrder(OrderRequestDTO request) {
        // 1. Deduct Balance (UserService handles insufficiency check)
        return userService.updateBalance(request.getUserId(), request.getTotalAmount().negate())
                .flatMap(user -> holdingService.updateHoldingAfterTrade(
                        request.getUserId(), request.getStockId(), request.getQuantity(), request.getPrice()))
                .flatMap(holding -> {
                    Order order = Order.builder()
                            .userId(request.getUserId()).stockId(request.getStockId())
                            .quantity(request.getQuantity()).executionPrice(request.getPrice())
                            .status("SUCCESS").build();
                    return orderRepository.save(order);
                })
                .map(order -> mapToResponse(order, "Order Executed Successfully"))
                .onErrorResume(e -> Mono.just(OrderResponseDTO.builder()
                        .status("FAILED").message(e.getMessage()).timestamp(LocalDateTime.now()).build()));
    }

    @Override
    public Flux<Order> getOrderHistory(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    // Fallback method for Circuit Breaker
    public Mono<OrderResponseDTO> orderFallback(OrderRequestDTO request, Throwable t) {
        log.error("Circuit Breaker triggered for OrderService: {}", t.getMessage());
        return Mono.just(OrderResponseDTO.builder()
                .status("SYSTEM_BUSY")
                .message("Trade execution is temporarily unavailable. Please try later.")
                .timestamp(LocalDateTime.now())
                .build());
    }

    private OrderResponseDTO mapToResponse(Order order, String msg) {
        return OrderResponseDTO.builder()
                .orderId(order.getId()).status(order.getStatus())
                .message(msg).timestamp(LocalDateTime.now()).build();
    }
}