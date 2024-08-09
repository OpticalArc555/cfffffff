package com.spring.jwt.repository;

import com.spring.jwt.entity.FinalBid;
import com.spring.jwt.entity.PlacedBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalBidRepository extends JpaRepository<FinalBid, Integer> {
    boolean existsByBidCarId(Integer bidCarId);

    List<FinalBid> findByBuyerDealerId(Integer buyerDealerId);

}