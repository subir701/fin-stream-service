package com.fin_stream_service.features.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("orders")
public class Order {

    @Id
    private Long id;
    private Long userId;
    private Long stockId;
    private Long quantity;
    private BigDecimal executionPrice;
    private String status; // SUCCESS, FAILED
    private String orderType; // BUY, SELL
    private LocalDateTime createdAt;
}
