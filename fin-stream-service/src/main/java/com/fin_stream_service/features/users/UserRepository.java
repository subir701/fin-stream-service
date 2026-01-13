package com.fin_stream_service.features.users;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,Long> {

    // Custom query: Find a user by their exact username
    // Returns a Mono because a username should be unique (0 or 1 result)
    Mono<User> findByUsername(String username);

    // You can also write manual SQL if needed using @Query
     @Query("SELECT * FROM users WHERE balance > :minBalance")
     Flux<User> findWealthyUsers(BigDecimal minBalance);
}
