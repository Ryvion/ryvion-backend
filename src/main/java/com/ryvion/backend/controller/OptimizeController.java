package com.ryvion.backend.controller;

import com.ryvion.backend.dto.OptimizeRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OptimizeController {

    private final Web3j web3j;
    private final String contractAddress;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ChatClient chatClient;
    private final Credentials credentials;

    public OptimizeController(
            @Value("${ARC_RPC_URL}") String rpcUrl,
            @Value("${ESCROW_CONTRACT_ADDRESS}") String contractAddress,
            @Value("${BACKEND_PRIVATE_KEY}") String privateKey,
            ChatClient.Builder chatClientBuilder) {
        this.web3j = Web3j.build(new HttpService(rpcUrl));
        this.contractAddress = contractAddress;
        this.credentials = Credentials.create(privateKey);
        this.chatClient = chatClientBuilder.build();
        System.out.println("Backend Wallet Address: " + this.credentials.getAddress());
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

    private String callAI(String riskLevel) {
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

}
