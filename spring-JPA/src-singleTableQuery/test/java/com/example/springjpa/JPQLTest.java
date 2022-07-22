package com.example.springjpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JPQLTest {

    @Autowired
    private CustomerJPQLRepository repository;

    @Test
    public void findByName(){
        Customer customer = repository.findCustomerByCustName("johndoe");
        System.out.println(customer);
    }

    @Test
    public void deleteCustomerBycustId(){
        System.out.println(repository.deleteCustomerByCustID(14L));
    }

    @Test
    public void updateCustomerPasswordByCustId(){
        System.out.println(repository.updateCustomerPasswordByCustID("123123", 13L));
    }
}
