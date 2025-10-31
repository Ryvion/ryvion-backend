package com.ryvion.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.Instant;

@Entity
@Table(name = "strategies")
@Getter
@Setter
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigInteger depositAmount;

    @Column(length = 500)
    private String recommendation;

    @Column(nullable = false) // PENDING CONFIMED CANCELED
    private String status;

    private String txHash;

    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}
