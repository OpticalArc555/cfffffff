package com.spring.jwt.Wallet.Repo;

import com.spring.jwt.Wallet.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByAccount_AccountId(Integer accountId);
}



