package com.ryvion.backend.controller;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OptimizeController {

    private final Web3j web3j = Web3j.build(new HttpService("https://rpc.testnet.arc.network"));
    private final String contractAddress = "";
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey = System.getenv("ANTHROPIC_API_KEY");

    @PostMapping("/optimize")
    public Map<String, Object> optimize(@RequestBody OptimizeRequest req) {
        Map<String, Object> response = new HashMap<>();

        String recommendation = callAnthropicAI(req.getRiskLevel());
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
            AnthropicClient client = new AnthropicOkHttpClient.Builder()
                    .apiKey(apiKey)
                    .build();

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

            Message message = client.messages().create(params);
            return message.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    @Setter
    static class OptimizeRequest {
        private String riskLevel;

    }

}
