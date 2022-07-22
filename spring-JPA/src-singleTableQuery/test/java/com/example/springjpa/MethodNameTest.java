package com.example.springjpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MethodNameTest {

    @Autowired
    private MethodNameRepository repository;

    @Test
    public void findByUsernameTest(){
        List<Customer> customer = repository.findCustomerByUsername("johndoe");
        System.out.println(customer);
    }

    @Test
    public void findByUsernameLikeTest(){
        List<Customer> customerList = repository.findCustomersByUsernameIsLikeIgnoreCase("%cust%");
        customerList.forEach(System.out::println);
    }
}
