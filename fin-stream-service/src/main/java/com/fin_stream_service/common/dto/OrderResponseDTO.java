package com.fin_stream_service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long orderId;
    private String symbol;
    private String orderType;       // BUY / SELL
    private Integer quantity;
    private BigDecimal executionPrice;
    private BigDecimal totalAmount; // qty * executionPrice
    private String status;          // SUCCESS / FAILED / REJECTED
    private String message;         // e.g., "Insufficient funds"
    private LocalDateTime timestamp;
}