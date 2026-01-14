package com.fin_stream_service.features.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public Mono<User> getUserProfile(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Mono<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Mono<User> updateBalance(Long userId, BigDecimal amount) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found: " + userId)))
                .flatMap(user -> {
                    // Perform the math (use .add() for BigDecimal, not .plus())
                    BigDecimal newBalance = user.getBalance().add(amount);
                    user.setBalance(newBalance);

                    // Return the Mono from the repository save operation
                    return userRepository.save(user);
                })
                // Log the update for debugging
                .doOnSuccess(u -> log.info("Updated balance for user {}: new balance {}", userId, u.getBalance()));

    }
}
