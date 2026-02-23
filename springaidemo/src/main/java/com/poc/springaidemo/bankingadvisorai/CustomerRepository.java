package com.poc.springaidemo.bankingadvisorai;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByCustomerId(Integer customerId);
}

