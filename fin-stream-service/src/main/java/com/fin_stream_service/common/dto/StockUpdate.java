package com.fin_stream_service.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockUpdate {

    private String symbol;
    private BigDecimal ltp;
    private BigDecimal change;
    private BigDecimal changePercent;
}
