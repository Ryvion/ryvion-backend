package com.ryvion.backend.repository;

import com.ryvion.backend.model.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StrategyRepository extends JpaRepository<Strategy,Long> {

}
