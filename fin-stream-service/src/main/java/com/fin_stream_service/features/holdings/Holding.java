package com.fin_stream_service.features.holdings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("holding")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Holding {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("stock_id")
    private Long stockId;

    private Long quantity;

    @Column("avg_buy_price")
    private BigDecimal avgBuyPrice;

    @Version
    private Long version;

    public Holding(Long id, Long userId, Long stockId, Long quantity, BigDecimal price) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.avgBuyPrice = price;
    }
}
