package com.example.springjpa.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;


public interface CustomerJPQLRepository extends PagingAndSortingRepository<Customer,Long> {



    //read
    //the number followed by question mark represents the position of parameter,1 means first parameter
    @Query("FROM Customer WHERE username = ?1")
    Customer findCustomerByCustName(String username);

    //delete
    @Transactional
    @Modifying
    @Query("delete from Customer where custID = ?1")
    int deleteCustomerByCustID(long custID);

    //update
    //":" is used to mark the parameter name in the method. ":password" means the value is from parameter "password"
    @Transactional
    @Modifying
    @Query("update Customer c set c.password=:password where c.custID = :id")
    int updateCustomerPasswordByCustID(String password, Long id);
}
