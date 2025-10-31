package com.ryvion.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptimizeResponse {
    private Long strategyId;
    private String recommendation;
    private String status;
}
