package com.ryvion.backend.controller;

import com.ryvion.backend.dto.OptimizeRequest;
import com.ryvion.backend.model.Strategy;
import com.ryvion.backend.model.User;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OptimizeController {

    private final Web3j web3j;
    private final String contractAddress;
    private final RestTemplate restTemplate = new RestTemplate();
    private final Credentials credentials;

    public OptimizeController(
            @Value("${ARC_RPC_URL}") String rpcUrl,
            @Value("${ESCROW_CONTRACT_ADDRESS}") String contractAddress,
            @Value("${BACKEND_PRIVATE_KEY}") String privateKey) {
        this.web3j = Web3j.build(new HttpService(rpcUrl));
        this.contractAddress = contractAddress;
        this.credentials = Credentials.create(privateKey);
        System.out.println("Backend Wallet Address: " + this.credentials.getAddress());
    }

}
