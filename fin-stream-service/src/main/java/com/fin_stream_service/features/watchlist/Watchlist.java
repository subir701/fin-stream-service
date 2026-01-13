package com.fin_stream_service.features.watchlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("watchlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Watchlist {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    @Column("stock_id")
    private Long stockId;
}
