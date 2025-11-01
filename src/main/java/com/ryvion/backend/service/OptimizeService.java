package com.ryvion.backend.service;

import com.ryvion.backend.dto.OptimizeRequest;
import com.ryvion.backend.dto.OptimizeResponse;
import com.ryvion.backend.model.RiskLevel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptimizeService {

    private final ChatClient chatClient;
    private final ChatModel chatModel;

    @Autowired
    public OptimizeService(ChatClient.Builder chatClientBuilder, ChatModel chatModel) {
        this.chatClient = chatClientBuilder.build();
        this.chatModel = chatModel;
    }

    public OptimizeResponse getAIRecommendation(OptimizeRequest optimizeRequest) {
        String recommendation = callAI(optimizeRequest.getRiskLevel());

        OptimizeResponse response = new OptimizeResponse();
        response.setRecommendation(recommendation);

        return response;
    }

    private String callAI(RiskLevel riskLevel) {
        try {
            String prompt = """
                You are "Ryvion", an expert AI investment agent.
                Your job is to optimize a user's USDC yield by allocating it 
                between two available assets:
                
                1.  **Tokenized US Treasuries:** Very safe. Stable 5%% APY.
                2.  **Tokenized AI Compute (GPU Farms):** Higher risk, higher reward. 
                    Estimated 18%% APY.
                    
                Based on the user's risk level (%s), create an allocation.
                
                Output ONLY the recommendation string in one line.
                
                Example for LOW: "70%% US Treasuries / 30%% AI Compute. Est. APY: 8.9%%"
                Example for HIGH: "10%% US Treasuries / 90%% AI Compute. Est. APY: 16.7%%"
                """.formatted(riskLevel.toString());

            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

        } catch (Exception e) {
            System.err.println("AI call failed: " + e.getMessage());
            return "Default Strategy: 100% US Treasuries. Est. APY: 5.0%";
        }
    }

}
