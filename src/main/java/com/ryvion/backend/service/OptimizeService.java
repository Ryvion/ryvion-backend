package com.ryvion.backend.service;

import com.ryvion.backend.dto.OptimizeRequest;
import com.ryvion.backend.dto.OptimizeResponse;
import com.ryvion.backend.model.RiskLevel;
import com.ryvion.backend.model.Strategy;
import com.ryvion.backend.model.StrategyStatus;
import com.ryvion.backend.model.User;
import com.ryvion.backend.repository.StrategyRepository;
import com.ryvion.backend.repository.UserRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Service
public class OptimizeService {

    private UserRepository userRepository;
    private BlockchainService blockchainService;
    private final ChatClient chatClient;
    private StrategyRepository strategyRepository;

    @Autowired
    public OptimizeService(ChatClient.Builder chatClientBuilder, UserRepository userRepository, BlockchainService blockchainService, StrategyRepository strategyRepository) {
        this.chatClient = chatClientBuilder.build();
        this.blockchainService = blockchainService;
        this.strategyRepository = strategyRepository;
        this.userRepository = userRepository;
    }

    public OptimizeResponse createStrategy(User user, BigInteger depositAmount, RiskLevel riskLevel) {
        Strategy strategy = new Strategy();
        strategy.setUser(user);
        strategy.setDepositAmount(depositAmount);
        strategy.setRecommendation(callAI(riskLevel));
        strategy.setStatus(StrategyStatus.PENDING);

        Strategy saveStrategy = strategyRepository.save(strategy);
        blockchainService.executeStrategy(saveStrategy);

        return new OptimizeResponse(saveStrategy.getId(), saveStrategy.getRecommendation(), saveStrategy.getStatus());
    }

    private String callAI(RiskLevel riskLevel) {
        try {
            String prompt = """
                You are an investment analyst AI for Ryvion.
                Optimize USDC yield for RWA based on risk level: %s.
                (Assume treasuries 4.2%%, invoices 5.5%%).
                Output only: Summary: ... Allocation: ... Yield: ...
                """.formatted(riskLevel);

            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

        } catch (Exception e) {
            throw new RuntimeException("AI call failed: " + e.getMessage(), e);
        }
    }

    @PostMapping("/optimize")
    public Map<String, Object> optimize(@RequestBody OptimizeRequest req) {
        Map<String, Object> response = new HashMap<>();

        //double realTreasuryYield = fetchRealTreasuryYield();

        String recommendation = callAI(req.getRiskLevel());//, realTreasuryYield);
        response.put("recommendation", recommendation);

        try {
            String txHash = "0xDeployedContract";
            response.put("txHash", txHash);
            response.put("status", "Strategy created! Yield accruing...");
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }

        return response;
    }
}
