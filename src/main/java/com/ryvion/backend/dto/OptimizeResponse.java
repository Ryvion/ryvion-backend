package com.ryvion.backend.dto;

import com.ryvion.backend.model.StrategyStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptimizeResponse {
    private Long strategyId;
    private String recommendation;
    private StrategyStatus status;
    public OptimizeResponse(Long strategyId, String recommendation, StrategyStatus status) {
        this.strategyId = strategyId;
        this.recommendation = recommendation;
        this.status = status;
    }
}
