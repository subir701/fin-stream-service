package com.fin_stream_service.features.watchlist;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface WatchlistRepository extends ReactiveCrudRepository<Watchlist, Long> {

    //Query: Getting list of watchlist stock by userId
    Flux<Watchlist> findByUserId(Long userId);
}
