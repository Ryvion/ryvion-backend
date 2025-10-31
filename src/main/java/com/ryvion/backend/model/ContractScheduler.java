package com.ryvion.backend.model;

import com.ryvion.backend.contracts.YieldEscrow;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

public class ContractScheduler {

    private static final long CHECK_INTERVAL_HOURS = 24;
    private static final long STRATEGY_LIFETIME_SECONDS = 30L * 24 * 60 * 60;

    private static final Map<Long, Long> strategyStartTimestamps = new HashMap<>(); //todo

    public static void main(String[] args) {

        Web3j web3j = Web3j.build(new HttpService("https://your-ethereum-node"));
        Credentials credentials = Credentials.create("PRIVATEB");

        YieldEscrow contract = YieldEscrow.load(
                "0xc21040e6AD80578cAE6BdaA03e968Fbb60A4E284",
                web3j,
                credentials,
                new DefaultGasProvider()
        );

        strategyStartTimestamps.put(0L, Instant.now().getEpochSecond() - STRATEGY_LIFETIME_SECONDS - 1000);
        strategyStartTimestamps.put(1L, Instant.now().getEpochSecond() - 10 * 24 * 60 * 60);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                System.out.println("checking strategies time: " + Instant.now());

                for (Map.Entry<Long, Long> entry : strategyStartTimestamps.entrySet()) {
                    Long id = entry.getKey();
                    Long startTime = entry.getValue();

                    long elapsed = Instant.now().getEpochSecond() - startTime;

                    if (elapsed >= STRATEGY_LIFETIME_SECONDS) {
                        System.out.println("executing strategy ID: " + id);

                        BigInteger yieldAmount = BigInteger.valueOf(100 * (id + 1)); // todo

                        TransactionReceipt receipt = contract.executeStrategy(
                                BigInteger.valueOf(id),
                                yieldAmount
                        ).send();

                        System.out.println("strategy executed. hash: " + receipt.getTransactionHash());

                        strategyStartTimestamps.remove(id);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, CHECK_INTERVAL_HOURS, TimeUnit.HOURS);
    }
}
