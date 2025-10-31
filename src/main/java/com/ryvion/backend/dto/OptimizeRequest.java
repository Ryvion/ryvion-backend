package com.ryvion.backend.dto;

import com.ryvion.backend.model.RiskLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptimizeRequest {
    private String walletAddress;
    private RiskLevel riskLevel;
}
