package com.picpaysimple.picpaysimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.picpaysimple.picpaysimple.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
