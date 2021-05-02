package com.kaminski.store.servicecustomer.repository;

import com.kaminski.store.servicecustomer.entity.Customer;
import com.kaminski.store.servicecustomer.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByNumberID(String numberID);

    List<Customer> findByLastName(String lastName);

    List<Customer> findByRegion(Region region);

}
