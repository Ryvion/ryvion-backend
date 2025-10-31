package com.ryvion.backend.controller;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.ContentBlock;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import com.anthropic.models.messages.TextBlock;
import com.ryvion.backend.dto.OptimizeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OptimizeController {

    private final Web3j web3j;
    private final String contractAddress;
    private final RestTemplate restTemplate = new RestTemplate();
    private final AnthropicClient aiClient;
    private final Credentials credentials;

    public OptimizeController(
            @Value("${ARC_RPC_URL}") String rpcUrl,
            @Value("${ESCROW_CONTRACT_ADDRESS}") String contractAddress,
            @Value("${BACKEND_PRIVATE_KEY}") String privateKey,
            @Value("${ANTHROPIC_API_KEY}") String aiKey
    ) {
        this.web3j = Web3j.build(new HttpService(rpcUrl));
        this.contractAddress = contractAddress;
        this.credentials = Credentials.create(privateKey);
        this.aiClient = new AnthropicOkHttpClient.Builder().apiKey(aiKey).build();

        System.out.println("Backend Wallet Address: " + this.credentials.getAddress());
    }

    @PostMapping("/optimize")
    public Map<String, Object> optimize(@RequestBody OptimizeRequest req) {
        Map<String, Object> response = new HashMap<>();

        //double realTreasuryYield = fetchRealTreasuryYield();

        String recommendation = callAnthropicAI(req.getRiskLevel());//, realTreasuryYield);
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

    private String callAnthropicAI(String riskLevel) {
        try {
            String prompt = """
                You are an investment analyst AI for Ryvion.
                Optimize USDC yield for RWA (tokenized treasuries/invoices) based on risk level: %s.
                Suggest allocation (e.g., 70% treasuries, 30% invoices) with short summary and expected monthly yield (assume treasuries 0.35%, invoices 1%).
                Output only: Summary: ... Allocation: ... Yield: ...
                """.formatted(riskLevel);

            MessageCreateParams params = MessageCreateParams.builder()
                    .model(Model.CLAUDE_4_SONNET_20250514)
                    .addUserMessage(prompt)
                    .maxTokens(200L)
                    .build();

            Message message = aiClient.messages().create(params);
            List<ContentBlock> contentList = message.content();

            return contentList.stream()
                    .filter(TextBlock.class::isInstance)
                    .map(TextBlock.class::cast)
                    .findFirst()
                    .map(TextBlock::text)
                    .orElseThrow(() -> new RuntimeException("Response did not contain text"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
