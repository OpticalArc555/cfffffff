package com.spring.jwt.repository;

import com.spring.jwt.entity.BidCars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidCarsRepo extends JpaRepository<BidCars, Integer> {
}
