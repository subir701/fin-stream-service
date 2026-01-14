package com.fin_stream_service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    private Long userId;
    private Long stockId;
    private BigDecimal totalAmount;
    private BigDecimal price;
    private Long quantity;
}
