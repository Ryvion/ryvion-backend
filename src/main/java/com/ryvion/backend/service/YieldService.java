package com.ryvion.backend.service;

import com.ryvion.backend.contracts.YieldEscrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.contracts.token.ERC20Interface;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.contracts.*;

import java.math.BigInteger;

@Service
@EnableScheduling
public class YieldService {

    private final YieldEscrow contract;
    private final Web3j web3j;
    private final Credentials credentials;
    private final String contractAddress;
    private final String usdcTokenAddress = "0x3600000000000000000000000000000000000000";

    @Autowired
    public YieldService(
            @Value("${ARC_RPC_URL}") String rpcUrl,
            @Value("${BACKEND_PRIVATE_KEY}") String privateKey,
            @Value("${ESCROW_CONTRACT_ADDRESS}") String contractAddress
    ) {
        this.web3j = Web3j.build(new HttpService(rpcUrl));
        this.credentials = Credentials.create(privateKey);
        this.contractAddress = contractAddress;

        this.contract = YieldEscrow.load(
                contractAddress,
                web3j,
                credentials,
                new DefaultGasProvider()
        );

        System.out.println("Yield Service (Agent) started. Wallet: " + credentials.getAddress());
        System.out.println("Watching contract: " + contractAddress);

        new Thread(this::approveContract).start();
    }

    private void approveContract() {
        try {
            System.out.println("Checking allowance...");


        }
    }

    @Scheduled(fixedRate = 300000)
    public void distributeYieldToAll() throws Exception {
        try {
            BigInteger nextId = contract.nextId().send();

            BigInteger yieldAmount = new BigInteger("500000");
            BigInteger minExpectedYield = BigInteger.ZERO;

            for (BigInteger i = BigInteger.ONE; i.compareTo(nextId) < 0; i = i.add(BigInteger.ONE)) {
                Tuple7<String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, BigInteger> details =
                        contract.getStrategyDetails(i).send();

                String userAddress = details.component1();
                Boolean isActive = details.component4();

                if (isActive && !userAddress.equals("0x0000000000000000000000000000000000000000")) {
                    System.out.println("Distributing yield to strategy: " + i + " for user: " + userAddress);

                    contract.distributeYield(
                            i,
                            yieldAmount,
                            minExpectedYield
                    ).sendAsync();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
