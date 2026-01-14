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
public class WatchlistResponseDTO {
    private Long stockId;
    private String symbol;
    private String stockName;
    private BigDecimal currentPrice; // From Redis
    private BigDecimal priceChange;  // Current - Base
    private BigDecimal percentChange;
}