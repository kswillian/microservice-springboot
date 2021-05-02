package com.kaminski.store.servicecustomer.service;

import com.kaminski.store.servicecustomer.entity.Customer;
import com.kaminski.store.servicecustomer.entity.Region;

import java.util.List;

public interface CustomerService {

    List<Customer> listAll();

    Customer getCustomer(Long id);

    Customer create(Customer customer);

    Customer update(Customer customer);

    Customer delete(Long id);

    List<Customer> findByRegion(Region region);

}
