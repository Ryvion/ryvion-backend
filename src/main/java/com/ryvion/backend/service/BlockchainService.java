package com.ryvion.backend.service;

import com.ryvion.backend.contracts.YieldEscrow;
import com.ryvion.backend.model.Strategy; // Импортируем вашу модель
import com.ryvion.backend.model.User; // Предполагаем, что у вас есть модель User
import com.ryvion.backend.repository.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.time.Instant; // Импортируем Instant

@Service
@EnableScheduling
@EnableAsync
public class BlockchainService {

    private final Web3j web3j;
    private final Credentials credentials;
    private final YieldEscrow contract;

    @Autowired
    public BlockchainService(
            @Value("${ARC_RPC_URL}") String rpcUrl,
            @Value("${BACKEND_PRIVATE_KEY}") String privateKey,
            @Value("${ESCROW_CONTRACT_ADDRESS}") String contractAddress,
            StrategyRepository strategyRepository
    ) {
        this.web3j = Web3j.build(new HttpService(rpcUrl));
        this.credentials = Credentials.create(privateKey);

        this.contract = YieldEscrow.load(
                contractAddress,
                web3j,
                credentials,
                new org.web3j.tx.gas.DefaultGasProvider()
        );

        System.out.println("Blockchain service started. Wallet Address: " + credentials.getAddress());
        System.out.println("Contract Address : " + contractAddress);
    }

    public BigInteger getContractTokenBalance() throws Exception {
        return contract.getContractTokenBalance().send();
    }
}