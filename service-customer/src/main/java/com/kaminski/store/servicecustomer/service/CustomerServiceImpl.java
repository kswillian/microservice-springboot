package com.kaminski.store.servicecustomer.service;

import com.kaminski.store.servicecustomer.entity.Customer;
import com.kaminski.store.servicecustomer.entity.Region;
import com.kaminski.store.servicecustomer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> listAll() {

        return customerRepository.findAll();

    }

    @Override
    public Customer getCustomer(Long id) {

        Optional<Customer> customer = customerRepository.findById(id);

        if(customer.isEmpty()){
            return null;
        }

        return customer.get();

    }

    @Override
    public Customer create(Customer customer) {

        Customer customerDB = customerRepository.findByNumberID(customer.getNumberID());

        if(customerDB != null){
            return customerDB;
        }

        customer.setState("CREATED");
        customerDB = customerRepository.save(customer);
        return customerDB;

    }

    @Override
    public Customer update(Customer customer) {

        Customer customerDB = getCustomer(customer.getId());

        if(customerDB == null){
            return null;
        }

        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setPhotoUrl(customer.getPhotoUrl());

        return customerRepository.save(customerDB);

    }

    @Override
    public Customer delete(Long id) {

        Customer customerDB = getCustomer(id);

        if(customerDB == null){
            return null;
        }

        customerDB.setState("DELETED");
        return customerRepository.save(customerDB);
    }

    @Override
    public List<Customer> findByRegion(Region region) {

        return customerRepository.findByRegion(region);

    }

}
