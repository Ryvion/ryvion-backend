package com.ryvion.backend;

import com.ryvion.backend.service.BlockchainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RyvionBackendApplication {

	public static void main(String[] args) throws Exception {
        var context = SpringApplication.run(RyvionBackendApplication.class, args);
        BlockchainService bs = context.getBean(BlockchainService.class);

        System.out.println("💰 Contract balance: " + bs.getContractTokenBalance());
	}
}
