package com.fin_stream_service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoldingResponseDTO {
    private String symbol;
    private String stockName;
    private Integer quantity;
    private BigDecimal avgBuyPrice;    // From Postgres
    private BigDecimal currentPrice;   // From Redis

    // Calculated Fields
    private BigDecimal investedValue;  // quantity * avgBuyPrice
    private BigDecimal currentValue;  // quantity * currentPrice
    private BigDecimal totalGain;      // currentValue - investedValue
    private BigDecimal gainPercentage;
}