package com.fin_stream_service.features.stocks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("stocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    private Long id;

    @Column("stock_name")
    private String stockName;

    private String symbol;

    @Column("base_price")
    private BigDecimal basePrice;

}
