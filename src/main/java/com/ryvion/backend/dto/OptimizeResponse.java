package com.ryvion.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class OptimizeResponse {
    private String recommendation;

    public OptimizeResponse(String recommendation) {
        this.recommendation = recommendation;
    }
}
