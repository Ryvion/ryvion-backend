package com.ryvion.backend.model;

import com.ryvion.backend.contracts.YieldEscrow;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.DefaultGasProvider;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ContractScheduler {

    public static void main(String[] args) {
        Web3j web3j = Web3j.build(new HttpService("https://your-ethereum-node"));
        Credentials credentials = Credentials.create("private");

        YieldEscrow contract = YieldEscrow.load(
                "0xc21040e6AD80578cAE6BdaA03e968Fbb60A4E284",
                web3j,
                credentials,
                new DefaultGasProvider()
        );

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                //var receipt = contract.getStrategy().send();
                System.out.println("getStrategy");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        long initialDelay = 0;
        long period = 30L * 24 * 60 * 60;
        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }
}
