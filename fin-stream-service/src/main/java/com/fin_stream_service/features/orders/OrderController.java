package com.fin_stream_service.features.orders;

import com.fin_stream_service.common.dto.OrderRequestDTO;
import com.fin_stream_service.common.dto.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/execute")
    public Mono<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO request) {
        return orderService.executeOrder(request);
    }
}