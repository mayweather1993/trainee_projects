package com.mayweather.repositories;

import com.mayweather.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long>{
}
