package com.fin_stream_service.features.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table("users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private Long id;

    @Column("username")
    private String username;

    @Column("balance")
    private BigDecimal balance;

    @Version
    private Long version;
}
