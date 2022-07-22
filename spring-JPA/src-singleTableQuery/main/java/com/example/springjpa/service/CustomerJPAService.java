package com.example.springjpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerJPAService {

    @Autowired
    private CustomerJPARepository repository;

    public List<Customer> queryAll(){
        List<Customer> customerList = (List<Customer>) repository.findAll();
        return customerList;
    }

    public Optional<Customer> queryById(long id){
        Optional<Customer> customer = repository.findById(id);
        return customer;
    }
}
