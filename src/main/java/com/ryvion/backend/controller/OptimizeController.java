package com.ryvion.backend.controller;

import com.ryvion.backend.dto.OptimizeRequest;
import com.ryvion.backend.dto.OptimizeResponse;
import com.ryvion.backend.service.OptimizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;

@RestController
@RequestMapping("/api/v1")
public class OptimizeController {

    private final OptimizeService optimizeService;

    @Autowired
    public OptimizeController(OptimizeService optimizeService) {
        this.optimizeService = optimizeService;
    }

    @PostMapping("/optimize")
    public ResponseEntity<OptimizeResponse> optimize(@RequestBody OptimizeRequest optimizeRequest){
        OptimizeResponse response = optimizeService.getAIRecommendation(optimizeRequest);
        return ResponseEntity.ok(response);
    }

}
