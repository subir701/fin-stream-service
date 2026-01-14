package com.fin_stream_service.features.users;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserService {
    Mono<User> getUserProfile(Long userId);
    Mono<User> getUserByUsername(String username);
    Mono<User> updateBalance(Long userId, BigDecimal amount);
}
