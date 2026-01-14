package com.fin_stream_service.features.holdings;

import com.fin_stream_service.common.dto.HoldingResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final HoldingService holdingService;

    @GetMapping("/{userId}")
    public Flux<HoldingResponseDTO> getMyPortfolio(@PathVariable Long userId) {
        return holdingService.getUserHoldings(userId);
    }
}