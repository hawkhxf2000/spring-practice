package com.example.springjpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JPABasicTest {

    @Autowired
    private CustomerJPAService service;

    @Autowired
    private CustomerJPARepository repository;

    @Test
    public void queryAll() {
        List<Customer> customerList = service.queryAll();
        System.out.println(customerList);
    }

    @Test
    public void queryById(){
        Optional<Customer> customer = service.queryById(1L);
        System.out.println(customer);
    }

    @Test
    public void addCustomer(){
        for (int i = 1; i < 11; i++) {
            Customer customer = new Customer();
            customer.setUsername("cust"+i);
            customer.setPassword("password");
            customer.setEmail("cust"+i+"@gmail.com");
            repository.save(customer);
        }
    }
}


