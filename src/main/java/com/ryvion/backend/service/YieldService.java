package com.ryvion.backend.service;

import com.ryvion.backend.contracts.YieldEscrow;
import com.ryvion.backend.model.Strategy;
import com.ryvion.backend.model.StrategyStatus;
import com.ryvion.backend.repository.StrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.List;

@Service
@EnableScheduling
@EnableAsync
public class BlockchainService {

    private final Web3j web3j;
    private final Credentials credentials;
    private final YieldEscrow contract;
    private final String contractAddress;
    private final StrategyRepository strategyRepository;

    @Autowired
    public BlockchainService(
            @Value("${ARC_RPC_URL}") String rpcUrl,
            @Value("${BACKEND_PRIVATE_KEY}") String privateKey,
            @Value("${ESCROW_CONTRACT_ADDRESS}") String contractAddress,
            StrategyRepository strategyRepository
    ) {
        this.web3j = Web3j.build(new HttpService(rpcUrl));
        this.credentials = Credentials.create(privateKey);
        this.contractAddress = contractAddress;
        this.strategyRepository = strategyRepository;

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

    @Async
    public void executeStrategy(Strategy strategy) {
        try {
            TransactionReceipt receipt = this.contract.createStrategy(
                    strategy.getUser().getWalletAddress(),
                    strategy.getDepositAmount(),
                    strategy.getRecommendation()
            ).send();

            strategy.setStatus(StrategyStatus.CONFIRMED);
            strategy.setTxHash(receipt.getTransactionHash());
            strategyRepository.save(strategy);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            strategy.setStatus(StrategyStatus.FAILED);
            strategyRepository.save(strategy);
        }
    }

    @Scheduled(fixedRate = 240000)
    public void distributeYieldToAll() {
        List<Strategy> activeStrategies = strategyRepository.findByStatus(StrategyStatus.CONFIRMED);
        if (activeStrategies.isEmpty()) {
            System.out.println("No active strategy found");
            return;
        }

        BigInteger yieldAmount = new BigInteger("1000000");
        BigInteger feePercentage = new BigInteger("1");

        for (Strategy strategy : activeStrategies) {
            try{
                this.contract.executeYield
            } catch (Exception e) {

            }
        }
    }
}